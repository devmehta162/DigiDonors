package com.dm.digidonors.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.digidonors.R;
import com.dm.digidonors.Utils.UserAddressAdapter;
import com.dm.digidonors.Utils.WebinarAdapter;
import com.dm.digidonors.models.AddNewAddressModel;
import com.dm.digidonors.models.UserAddressModel;
import com.dm.digidonors.models.Webinar;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class UserAddressActivity extends AppCompatActivity {


    private Button addNewAddress,nextBtn;
    private RecyclerView recyclerView;

    private FirebaseFirestore firebaseFirestore;
    private UserAddressAdapter userAddressAdapter;
    private TextView toolbarHeadingText;

    private ImageView backButton;

    private Toolbar toolbar;
    private ArrayList<UserAddressModel> userAddressModelArrayList;
    //  firebase
    private FirebaseAuth mAuth;
    String uid;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final String TAG = "UserAddressActivity";
    private UserAddressModel userAddressModelselected =new UserAddressModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        mAuth = FirebaseAuth.getInstance();
        nextBtn=findViewById(R.id.nextBtn);
        userAddressModelArrayList = new ArrayList<>();
        addNewAddress = findViewById(R.id.newAddressBtn);
        recyclerView = findViewById(R.id.recyclerViewUserAddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserAddressActivity.this));
        recyclerView.setHasFixedSize(true);
        userAddressAdapter=new UserAddressAdapter(getApplicationContext(),userAddressModelArrayList);
        recyclerView.setAdapter(userAddressAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();

        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);

        backButton=findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        toolbarHeadingText.setText("Address");

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserAddressActivity.this);


        if (!preferences.getString("mUserSelectedAddress", "").equals("")){
            String mUserAddress = preferences.getString("mUserSelectedAddress", "");
            Type type = new TypeToken<UserAddressModel>() {
            }.getType();

            userAddressModelselected = new Gson().fromJson(mUserAddress, type);


            Log.d(TAG, "onCreate: ,userAddressModelselected.getName() "+userAddressModelselected.getName());
            userAddressAdapter =new UserAddressAdapter(UserAddressActivity.this,userAddressModelselected.getName());

        }



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!preferences.getString("mUserSelectedAddress", "").equals("")){

                    preferences.edit().remove("mUserSelectedAddress").commit();

                }
                Intent intent =new Intent(UserAddressActivity.this,SelectTimeAndDate.class);
                intent.putExtra("name",userAddressAdapter.getSelected().getName());
                intent.putExtra("address",userAddressAdapter.getSelected().getAddress());
                intent.putExtra("city",userAddressAdapter.getSelected().getCity());
                intent.putExtra("dateAdded",userAddressAdapter.getSelected().getDateAdded());
                intent.putExtra("mobileNo",userAddressAdapter.getSelected().getPhone());
                intent.putExtra("pinCode",userAddressAdapter.getSelected().getPincode());
                intent.putExtra("state",userAddressAdapter.getSelected().getState());
                intent.putExtra("category",getIntent().getStringExtra("category"));
//                startActivity(intent);



                UserAddressModel userAddressModel =new UserAddressModel(userAddressAdapter.getSelected().getName()
                        ,userAddressAdapter.getSelected().getPhone(),
                        userAddressAdapter.getSelected().getPincode(),
                        userAddressAdapter.getSelected().getAddress(),
                        userAddressAdapter.getSelected().getCity(),
                        userAddressAdapter.getSelected().getState(),
                        userAddressAdapter.getSelected().getDateAdded()
                        );

                String mUserAddress = new Gson().toJson(userAddressModel);

                preferences.edit().putString("mUserSelectedAddress", mUserAddress).apply();

                onBackPressed();

            }
        });
        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAddressActivity.this, AddNewAddress.class);
                startActivity(intent);
            }
        });



        if (!preferences.getString("mUserAddress", "").equals("")) {

            Log.d(TAG, "onCreate: preferences");
            String mUserAddress = preferences.getString("mUserAddress", "");
            Type type = new TypeToken<ArrayList<UserAddressModel>>() {
            }.getType();
            userAddressModelArrayList = new Gson().fromJson(mUserAddress, type);
            userAddressAdapter=new UserAddressAdapter(getApplicationContext(),userAddressModelArrayList);
            recyclerView.setAdapter(userAddressAdapter);
            userAddressAdapter.notifyDataSetChanged();
        }else {

            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                uid = user.getUid();

            } else {
//                Toast.makeText(this, "User Not logged In", Toast.LENGTH_SHORT).show();
            }
            firebaseFirestore.collection("Users").document(uid).collection("Address").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                UserAddressModel userAddressModel = d.toObject(UserAddressModel.class);
                                userAddressModelArrayList.add(userAddressModel);
                            }
                            String mUserAddress = new Gson().toJson(userAddressModelArrayList);

                            preferences.edit().putString("mUserAddress", mUserAddress).apply();
                            userAddressAdapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserAddressActivity.this);


        if (!preferences.getString("mUserAddress", "").equals("")) {

            Log.d(TAG, "onCreate: preferences");
            String mUserAddress = preferences.getString("mUserAddress", "");
            Type type = new TypeToken<ArrayList<UserAddressModel>>() {
            }.getType();
            userAddressModelArrayList.clear();
            userAddressModelArrayList = new Gson().fromJson(mUserAddress, type);
            if (userAddressModelArrayList!=null&&userAddressModelArrayList.size()!=0) {
            //    Toast.makeText(this, userAddressModelArrayList.get(0).getName(), Toast.LENGTH_SHORT).show();
            }
            userAddressAdapter=new UserAddressAdapter(getApplicationContext(),userAddressModelArrayList);
            recyclerView.setAdapter(userAddressAdapter);
            userAddressAdapter.notifyDataSetChanged();
        }else {

            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                uid = user.getUid();

            } else {
//                Toast.makeText(this, "User Not logged In", Toast.LENGTH_SHORT).show();
            }
            firebaseFirestore.collection("Users").document(uid).collection("Address").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            userAddressModelArrayList.clear();
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                UserAddressModel userAddressModel = d.toObject(UserAddressModel.class);
                                userAddressModelArrayList.add(userAddressModel);
                            }
                            String mUserAddress = new Gson().toJson(userAddressModelArrayList);

                            preferences.edit().putString("mUserAddress", mUserAddress).apply();
                            userAddressAdapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}