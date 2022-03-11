package com.dm.digidonors.Webinar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.WebinarAdapter;
import com.dm.digidonors.models.Webinar;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class UpcomingWebinars extends Fragment {

    private static final String TAG = "UpcomingWebinars";
    public static final int ACTIVITY_NUM = 3;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private WebinarAdapter webinarAdapter;

  //  firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_webinars, container, false);
        mAuth=FirebaseAuth.getInstance();

       setupFirebaseAuth();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();


      //  FirebaseUser user = mAuth.getCurrentUser();

//        if (user!=null){
//            uid = user.getUid();
//            Toast.makeText(getActivity(), uid, Toast.LENGTH_SHORT).show();

//        }else {
//            Toast.makeText(getActivity(), "User Not logged In", Toast.LENGTH_SHORT).show();
//        }

        Query query = firebaseFirestore.collection("Webinars");

        FirestoreRecyclerOptions<Webinar> options = new FirestoreRecyclerOptions.Builder<Webinar>()
                .setQuery(query, Webinar.class)
                .build();
        webinarAdapter = new WebinarAdapter(options,getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(webinarAdapter);
        return view;
    }

    private void checkCurrentFirebaseUser(FirebaseUser user) {

        if (user == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

    }
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if user is logged in
                checkCurrentFirebaseUser(user);
                if (user != null) {
                    Log.d(TAG, "onAuthSateChanged:signed_in" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthSateChanged:signed_out");

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        webinarAdapter.startListening();
        mAuth.addAuthStateListener(mAuthListner);


        checkCurrentFirebaseUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
        webinarAdapter.stopListening();
    }
}