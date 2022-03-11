package com.dm.digidonors.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.digidonors.FcmNotificationsSender;
import com.dm.digidonors.FirebaseMessagingService;
import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Webinar.WebinarDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class VerifyOtp extends AppCompatActivity {

    private static final String TAG = "VerifyOtp";
    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private TextView textMobile;
    private ProgressBar progressBar;
    private Button btnVerify;
    private String verificationId;
    private FirebaseAuth mAuth;
    String uid;
    private DocumentReference collectionReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView backButton;
    private TextView toolbarHeadingText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        mAuth = FirebaseAuth.getInstance();



        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        textMobile = findViewById(R.id.textMobile);
        progressBar = findViewById(R.id.progressBar);
        btnVerify = findViewById(R.id.btnVerifyOtp);

        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);

        toolbarHeadingText.setText("Verify Phone Number");
        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        verificationId = getIntent().getStringExtra("verifiationId");

        textMobile.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCode1.getText().toString().trim().isEmpty() ||
                        inputCode2.getText().toString().trim().isEmpty() ||
                        inputCode3.getText().toString().trim().isEmpty() ||
                        inputCode4.getText().toString().trim().isEmpty() ||
                        inputCode5.getText().toString().trim().isEmpty() ||
                        inputCode6.getText().toString().trim().isEmpty()) {

                    Toast.makeText(VerifyOtp.this, "Please Enter Valid Code", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String code = inputCode1.getText().toString() +
                            inputCode2.getText().toString() +
                            inputCode3.getText().toString()
                            + inputCode4.getText().toString()
                            + inputCode5.getText().toString() +
                            inputCode6.getText().toString();

                    if (verificationId != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        btnVerify.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                verificationId,
                                code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        btnVerify.setVisibility(View.VISIBLE);
                                        if (task.isSuccessful()) {
                                            saveUserDetails();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finishAffinity();
                                            FcmNotificationsSender fcmNotificationsSender  =new FcmNotificationsSender();
                                            fcmNotificationsSender.SendNotifications();
                                            FirebaseMessagingService firebaseMessagingService =new FirebaseMessagingService();
                                            Log.d(TAG, "onComplete: "+ PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "defaultStringIfNothingFound"));
                                        } else {
                                            Toast.makeText(VerifyOtp.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        setupOTPInputs();


    }

    private void saveUserDetails() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            uid = user.getUid();
//            Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        }else {
//            Toast.makeText(this, "User Not logged In", Toast.LENGTH_SHORT).show();
        }
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
    }

    private void setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}