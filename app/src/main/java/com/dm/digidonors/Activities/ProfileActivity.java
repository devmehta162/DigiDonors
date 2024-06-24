package com.dm.digidonors.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.UniversalImageLoader;
import com.dm.digidonors.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Type;

public class ProfileActivity extends AppCompatActivity {

    private ImageView userProfileImage;
    private EditText userName,userMobileNumber,userEmail,userAddress;
    private UserProfile userProfile = new UserProfile();

    private Button saveDetails,logOut;
    private FirebaseAuth mAuth;
    private static final String TAG = "ProfileActivity";
    private ImageView backButton;
    private TextView toolbarHeadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userAddress=findViewById(R.id.user_address);
        userMobileNumber=findViewById(R.id.user_mobile_number);
        userEmail=findViewById(R.id.user_email);
        userName=findViewById(R.id.user_name);
        userProfileImage=findViewById(R.id.userProfileImage);
        logOut=findViewById(R.id.logOut);
        saveDetails=findViewById(R.id.btnSaveProfileDetails);
        mAuth = FirebaseAuth.getInstance();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);

        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);

        toolbarHeadingText.setText("Profile");
        backButton=findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent3 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent3);

                clearAllTheSharedPreferencesValues();


            }
        });
        if (!preferences.getString("mUserProfileDetails", "").equals("")) {
            String mUserProfile = preferences.getString("mUserProfileDetails", "");
            Type type = new TypeToken<UserProfile>() {
            }.getType();

            userProfile = new Gson().fromJson(mUserProfile, type);

            Log.d(TAG, "onCreateView: userProfile.getUserName()" + userProfile.getUserName() + userProfile.getUserEmail());
//


            updateProfile(userProfile.getUserName(), userProfile.getUserEmail(), userProfile.getUserImageurl(),userProfile.getUserPhoneNumber());

        }


        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfile =new UserProfile(userName.getText().toString().trim(),userEmail.getText().toString().trim(),userProfile.getUserImageurl(),userMobileNumber.getText().toString().trim(),userAddress.getText().toString().trim(),userProfile.getUserUid());

                String mUserProfile = new Gson().toJson(userProfile);

                preferences.edit().putString("mUserProfileDetails", mUserProfile).apply();


                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });






    }

    private void updateProfile(String userNameGiven, String userEmailGiven, String userImageurlGiven, String userPhoneNumber) {
        userName.setText(userNameGiven);
        userEmail.setText(userEmailGiven);

        userMobileNumber.setText(userPhoneNumber);
        initImageLoader();

        UniversalImageLoader.setImage(userImageurlGiven, userProfileImage, null, "");

    }
    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(ProfileActivity.this);

        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void clearAllTheSharedPreferencesValues() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        preferences.edit().remove("mUserProfileDetails").commit();
        preferences.edit().remove("mRecentlyAddedNgo").commit();
        preferences.edit().remove("mOnGoingCampaigns").commit();
        preferences.edit().remove("mUserAddress").commit();
        preferences.edit().remove("mUserSelectedAddress").commit();

    }

}