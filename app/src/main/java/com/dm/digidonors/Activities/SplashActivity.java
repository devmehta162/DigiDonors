package com.dm.digidonors.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final String TAG = "SplashActivity";
    private boolean isLoggedIn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

//        setupFirebaseAuth();

        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(1000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        timer.start();
    }


    //Destroy Welcome_screen.java after it goes to next activity
    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        finish();


    }

    private void checkCurrentFirebaseUser(FirebaseUser user) {

//        if (user == null) {
//            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//            finish();
//        }else {
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            finish();
//        }

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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListner);
//
//
//        checkCurrentFirebaseUser(mAuth.getCurrentUser());
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mAuthListner != null) {
//            mAuth.removeAuthStateListener(mAuthListner);
//        }
//    }
}