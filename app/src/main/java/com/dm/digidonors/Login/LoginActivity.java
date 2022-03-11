package com.dm.digidonors.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.digidonors.Activities.WebViewActivity;
import com.dm.digidonors.Home.SelectSlotForDonationActivity;
import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.models.UserProfile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;

    //vars
    private String TAG = "LOGINACTIVITY";
    private int RC_SIGN_IN = 1;

    //widgets
    private EditText inputMobile;
    private Button btnGetOtp;
    private Button GoogleLoginBtn;
    private ProgressBar progressBar;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DocumentReference collectionReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserProfile userProfile;
    private TextView termsOfUseBtn;
    private CheckBox checkBoxTermsofUse;
    private ImageView backButton;
    private TextView toolbarHeadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleLoginBtn = findViewById(R.id.btn_googleSignIn);
        mAuth = FirebaseAuth.getInstance();

        inputMobile = findViewById(R.id.inputMobile);
        btnGetOtp = findViewById(R.id.btnGetOtp);
        progressBar=findViewById(R.id.progressBar);
        checkBoxTermsofUse=findViewById(R.id.checkBoxTermsOfUse);
        termsOfUseBtn=findViewById(R.id.termsOfUseBtn);

        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);

        toolbarHeadingText.setText("DigiDonors");
        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        termsOfUseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("url","https://sites.google.com/view/digidonors-termsofuse/home");
                startActivity(intent);
            }
        });


        //setupFirebaseAuth();
        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputMobile.getText().toString().trim().length() < 10 || inputMobile.getText().toString().trim().length() > 10) {
                    Toast.makeText(LoginActivity.this, "Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();

                } else {


                    if (checkBoxTermsofUse.isChecked()){
                        progressBar.setVisibility(View.VISIBLE);
                        btnGetOtp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91"+inputMobile.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                LoginActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        btnGetOtp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        btnGetOtp.setVisibility(View.VISIBLE);
                                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        btnGetOtp.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifyOtp.class);

                                        intent.putExtra("mobile", inputMobile.getText().toString().trim());
                                        intent.putExtra("verifiationId",verificationId);
                                        startActivity(intent);
                                    }
                                }

                        );
                    }else {
                        Toast.makeText(LoginActivity.this, "Please read and agree with our Terms of Use before proceeding.", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxTermsofUse.isChecked()){
                    signin();
                }else {
                    Toast.makeText(LoginActivity.this, "Please read and agree with our Terms of Use before proceeding.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSignInresult(task);
        }
    }

    private void handleSignInresult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Sign in successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);

        } catch (ApiException e) {
            Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUi(user);
                    saveUserDetails(user);



                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    Log.d(TAG, "onComplete: "+user.getDisplayName() + "\n "+ user.getEmail()
                            + "\n "
                            +user.getDisplayName()
                            + "\n "+user.getPhotoUrl()
                    +user.getPhoneNumber());




                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    updateUi(null);
                }
            }
        });

    }

    private void saveUserDetails(FirebaseUser user) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        String uid =user.getUid();
        HashMap<String,Object> map =new HashMap<>();
        map.put("UserId",uid);
        map.put("FCMTokem",PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "defaultStringIfNothingFound"));


        collectionReference = db.collection("Users").document(uid);
        collectionReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        Log.d(TAG, "saveUserDetails: user.getDisplayName() "+user.getDisplayName());
        userProfile =new UserProfile(user.getDisplayName(),user.getEmail(),user.getPhotoUrl().toString(),user.getPhoneNumber(),"",user.getUid());

        String mUserProfile = new Gson().toJson(userProfile);

        preferences.edit().putString("mUserProfileDetails", mUserProfile).apply();
    }

    private void updateUi(FirebaseUser fUser) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {


        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();

    }
    //
//    private void setupFirebaseAuth() {
//        mAuth = FirebaseAuth.getInstance();
//        mAuthListner = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                //check if user is logged in
//
//                if (user != null) {
//                    Log.d(TAG, "onAuthSateChanged:signed_in" + user.getUid());
//                } else {
//                    Log.d(TAG, "onAuthSateChanged:signed_out");
//
//                }
//            }
//        };
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListner);
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mAuthListner != null) {
//            mAuth.removeAuthStateListener(mAuthListner);
//        }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);
//    }
}
