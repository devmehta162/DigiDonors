package com.dm.digidonors.MyDonation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyDonationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyDonationAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private String uid;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private ProgressBar progressBar;
    private ImageView backButton;
    private TextView toolbarHeadingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);

        setupFirebaseAuth();
        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);

        toolbarHeadingText.setText("My Donation");
        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.profileProgressBar);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();

        } else {
//            Toast.makeText(this, "User Not logged In", Toast.LENGTH_SHORT).show();
        }

//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        Query query = firebaseFirestore.collection("DetailsOfDonation").whereEqualTo("userid",uid);





        FirestoreRecyclerOptions<MyDonationModel> options = new FirestoreRecyclerOptions.Builder<MyDonationModel>()
                .setQuery(query, MyDonationModel.class).build();


        adapter = new MyDonationAdapter(options,MyDonationsActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyDonationsActivity.this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);


    }


    private void checkCurrentFirebaseUser(FirebaseUser user) {

        if (user == null) {
            startActivity(new Intent(MyDonationsActivity.this, LoginActivity.class));
            finish();
        }

    }

    /*
    set up firebase auth object
     */
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if user is logged in
                checkCurrentFirebaseUser(user);
                if (user != null) {
                } else {

                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        adapter.startListening();

        mAuth.addAuthStateListener(mAuthListner);
        checkCurrentFirebaseUser(mAuth.getCurrentUser());

        // updateUI(currentUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
            adapter.stopListening();
        }

    }
}