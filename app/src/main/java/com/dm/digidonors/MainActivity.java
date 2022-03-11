package com.dm.digidonors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.dm.digidonors.Home.HomeFragment;
import com.dm.digidonors.Home.MainPageFragment;
import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.SocialMedia.SocialMediaFragment;
import com.dm.digidonors.Utils.UniversalImageLoader;
import com.dm.digidonors.Webinar.WebinarFragment;
import com.dm.digidonors.models.Photo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    //vars
    private static final String TAG = "MainActivity";
    public static final int HOME_FRAGMENT = 1;
    private Context mContext = MainActivity.this;
    public static final int ACTIVITY_NUM = 0;
//    private BottomSheetBehavior bottomSheetBehavior;
    View bottomSheetView;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    //widgets
    private ViewPager viewPager;
    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
//    private BottomNavigationView bottomBar;
    private int currentFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onComplete: "+ PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "defaultStringIfNothingFound"));

//        bottomBar = findViewById(R.id.bottomNav);
//        bottomNavigationView=findViewById(R.id.bottomNav);
//        logOut=findViewById(R.id.log_out);
//        mAuth = FirebaseAuth.getInstance();
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                Intent intent =new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

//        bottomBar.setOnNavigationItemSelectedListener(this.navListener);

        mAuth = FirebaseAuth.getInstance();

        frameLayout = (FrameLayout) findViewById(R.id.container);
        relativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);

        loadFragment(new MainPageFragment());


//        Toast.makeText(mContext, "cdks", Toast.LENGTH_SHORT).show();


       setupFirebaseAuth();
//        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public boolean onItemSelect(int i) {
//                Fragment fragment = null;
//                switch (i) {
//                    case 0:
//                        currentFragmentId = R.id.botton_nav_home;
//                        fragment = new HomeFragment();
//                        loadFragment(fragment);
//                        break;
//
//                    case 1:
//
//                        currentFragmentId = R.id.botton_nav_social_media;
//                        fragment = new SocialMediaFragment();
//                        loadFragment(fragment);
//
//                        break;
//                    case 2:
//
//                        currentFragmentId = R.id.bottom_nav_webinars;
//                        fragment = new WebinarFragment();
//                        loadFragment(fragment);
//
//                        break;
//
//                }
//                return loadFragment(fragment);
//            }
//        });
//        bottomBar.setOnItemReselectedListener(new OnItemReselectedListener() {
//            @Override
//            public void onItemReselect(int i) {
//            }
//        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitAllowingStateLoss(); //Using this instead of commit: It is known as state loss.
            // You happen to commit a FragmentTransaction from AsyncTask.
            //That is prohibited by the framework itself.
            //https://stackoverflow.com/questions/22713002/java-lang-illegalstateexception-can-not-perform-this-action-after-onsaveinstanc
            //https://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html
            return true;
        }
        return false;
    }
//    public void onCommentThreadSelected(Photo photo) {
//        Log.d(TAG, "onCommentThreadSelected: selected a coemment thread");
//
//        ViewCommentsFragment fragment = new ViewCommentsFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(getString(R.string.photo), photo);
//        fragment.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, fragment);
//        transaction.addToBackStack(getString(R.string.view_comments_fragment));
//        transaction.commit();
//
//    }
//public BottomNavigationView.OnNavigationItemSelectedListener navListener =
//        new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Fragment selectedFragment = null;
////                progressBar.setVisibility(View.INVISIBLE);
//                switch (menuItem.getItemId()){
//
//
//                    case R.id.botton_nav_home:
//
//                        currentFragmentId = R.id.botton_nav_home;
//
//
//                        selectedFragment = new MainPageFragment();
//                        loadFragment(selectedFragment);
//                        break;
//                    case R.id.botton_nav_social_media:
//
//                        currentFragmentId = R.id.botton_nav_social_media;
//                        selectedFragment = new SocialMediaFragment();
//                        loadFragment(selectedFragment);
//                        break;
//                    case R.id.bottom_nav_webinars:
//
//                        currentFragmentId = R.id.bottom_nav_webinars;
//                        selectedFragment = new WebinarFragment();
//                        loadFragment(selectedFragment);
//                        break;
//
//                }
//
//                if(selectedFragment!=null)   getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).setReorderingAllowed(true).addToBackStack(selectedFragment.getClass().getName()).commit();
//                return true;
//            }
//        };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (frameLayout.getVisibility() == View.VISIBLE) {
            showLayout();
        }
    }

    public void showLayout() {
        relativeLayout.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);

        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
    private void checkCurrentFirebaseUser(FirebaseUser user) {

//        if (user == null) {
//            startActivity(new Intent(mContext, LoginActivity.class));
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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);


        checkCurrentFirebaseUser(mAuth.getCurrentUser());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
}