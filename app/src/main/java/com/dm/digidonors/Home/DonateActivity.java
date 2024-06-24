package com.dm.digidonors.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.UniversalImageLoader;
import com.dm.digidonors.models.UserProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonateActivity extends AppCompatActivity {

    private Button logOut;
    private FirebaseAuth mAuth;

    private Toolbar toolbar;
    private Toast backToast;
    private long backPressedTime;

    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListner;


    private CardView cloth;
    private CardView books;
    private CardView blood;
    private CardView food;
    private CardView stationary;
    private CardView shoes;

    private String clothes = "clothes";

    private TextView userName, userEmail;
    private TextView toolbarHeadingText;

    private ViewFlipper viewFlipper;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private TextView image1, image2, image3, image4;
    FirebaseFirestore dbnew = FirebaseFirestore.getInstance();
    private UserProfile userProfile = new UserProfile();
    private static final String TAG = "DonateActivity";
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Log.d(TAG, "onCreate: create lifecycle");

        mAuth = FirebaseAuth.getInstance();

        toolbarHeadingText = findViewById(R.id.toolbarHeadingText);

        toolbarHeadingText.setText("Select Category");

        imageView1 = findViewById(R.id.imageview1);
        imageView2 = findViewById(R.id.imageview2);
        imageView3 = findViewById(R.id.imageview3);
        imageView4 = findViewById(R.id.imageview4);

        image1 = findViewById(R.id.img1);
        image2 = findViewById(R.id.img2);
        image3 = findViewById(R.id.img3);
        image4 = findViewById(R.id.img4);

        cloth = findViewById(R.id.cloth_card);
        books = findViewById(R.id.books_card);
        blood = findViewById(R.id.blood_card);
        food = findViewById(R.id.food_card);
        stationary = findViewById(R.id.stationary_card);
        shoes = findViewById(R.id.shoes_card);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DonateActivity.this);

        setupFirebaseAuth();

        if (!preferences.getString("mUserProfileDetails", "").equals("")) {
            String mUserProfile = preferences.getString("mUserProfileDetails", "");
            Type type = new TypeToken<UserProfile>() {
            }.getType();

            userProfile = new Gson().fromJson(mUserProfile, type);

            Log.d(TAG, "onCreateView: userProfile.getUserName()" + userProfile.getUserName() + userProfile.getUserEmail());
//


        }
        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DonateActivity.this, SelectSlotForDonationActivity.class);
                intent.putExtra("category", "cloth");
                startActivity(intent);

                checkCurrentFirebaseUser(user);


            }
        });
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, SelectSlotForDonationActivity.class);
                intent.putExtra("category", "books");
                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, SelectSlotForDonationActivity.class);
                intent.putExtra("category", "blood");
                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, SelectSlotForDonationActivity.class);
                intent.putExtra("category", "food");

                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });
        stationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, SelectSlotForDonationActivity.class);
                intent.putExtra("category", "stationary");

                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, SelectSlotForDonationActivity.class);
                intent.putExtra("category", "shoes");

                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });


    }


//    public void onBackPressed() {
//
//        startActivity(new Intent(DonateActivity.this, MainActivity.class));
//        finish();
//
//    }

    private void checkCurrentFirebaseUser(FirebaseUser user) {

        if (user == null) {
            startActivity(new Intent(DonateActivity.this, LoginActivity.class));
            finish();
        }

    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                //check if user is logged in

                if (user != null) {
                    Log.d(TAG, "onAuthSateChanged:signed_in" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthSateChanged:signed_out");

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onCreate: onStart lifecycle");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListner);


//        checkCurrentFirebaseUser(mAuth.getCurrentUser());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onCreate: onStop lifecycle");

        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onCreate: onResume lifecycle");

    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onCreate: onRestart lifecycle");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onCreate: onPause lifecycle");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onCreate: onDestroy lifecycle");

    }

}