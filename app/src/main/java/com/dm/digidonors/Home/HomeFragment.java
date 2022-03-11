package com.dm.digidonors.Home;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.UniversalImageLoader;
import com.dm.digidonors.models.UserAddressModel;
import com.dm.digidonors.models.UserProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Type;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RESULT_OK = -1;
    private Button logOut;
    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Toast backToast;
    private long backPressedTime;

    private AppUpdateManager mAppUpdateManager;


    private CardView cloth;
    private CardView books;
    private CardView blood;
    private CardView others;

    private String clothes = "clothes";

    private TextView userName, userEmail;

    private ViewFlipper viewFlipper;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private TextView image1, image2, image3, image4;
    FirebaseFirestore dbnew = FirebaseFirestore.getInstance();
    private UserProfile userProfile = new UserProfile();
    private static final String TAG = "HomeFragment";
    private static final int RC_APP_UPDATE=100;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();


        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);


        imageView1 = view.findViewById(R.id.imageview1);
        imageView2 = view.findViewById(R.id.imageview2);
        imageView3 = view.findViewById(R.id.imageview3);
        imageView4 = view.findViewById(R.id.imageview4);

        image1 = view.findViewById(R.id.img1);
        image2 = view.findViewById(R.id.img2);
        image3 = view.findViewById(R.id.img3);
        image4 = view.findViewById(R.id.img4);

        cloth = view.findViewById(R.id.cloth_card);
        books = view.findViewById(R.id.books_card);
        blood = view.findViewById(R.id.blood_card);
        others = view.findViewById(R.id.others_card);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        if (!preferences.getString("mUserProfileDetails", "").equals("")) {
            String mUserProfile = preferences.getString("mUserProfileDetails", "");
            Type type = new TypeToken<UserProfile>() {
            }.getType();

            userProfile = new Gson().fromJson(mUserProfile, type);

            Log.d(TAG, "onCreateView: userProfile.getUserName()" + userProfile.getUserName() + userProfile.getUserEmail());
//


            updateNavHeaderView(userProfile.getUserName(), userProfile.getUserEmail(), userProfile.getUserImageurl());

        }

        mAppUpdateManager= AppUpdateManagerFactory.create(getActivity());

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){

                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE,
                                getActivity(),RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mAppUpdateManager.registerListener(installStateUpdatedListener);

        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "cloth");
                startActivity(intent);
            }
        });
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "books");
                startActivity(intent);
            }
        });
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "blood");
                startActivity(intent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "others");

                startActivity(intent);
            }
        });

        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        toolbar = view.findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.nav_home);


//        logOut =view.findViewById(R.id.logOut);


//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                Intent intent =new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        return view;

    }

    private void updateNavHeaderView(String name, String email, String profileImageUri) {


        View header = navigationView.getHeaderView(0);
        TextView userName = header.findViewById(R.id.user_name);
        TextView login = header.findViewById(R.id.loginBtn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        initImageLoader();
        CircleImageView circleImageView = header.findViewById(R.id.userProfileImage);

        UniversalImageLoader.setImage(profileImageUri, circleImageView, null, "");
//        circleImageView.setImageURI(Uri.parse(profileImageUri));


        Log.d(TAG, "updateNavHeaderView: " + Uri.parse(profileImageUri));

        userName.setText(name);


        if (userName == null) {
            login.setVisibility(View.VISIBLE);

        }
//        userEmail.setText(email);


    }


    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (backPressedTime + 2000 > System.currentTimeMillis()) {
            getActivity().finishAffinity();
            backToast.cancel();

            return;
        } else {
            backToast = Toast.makeText(getActivity(), "Press back again to exit", Toast.LENGTH_LONG);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:

                break;
//            case R.id.nav_gallery:
//                Intent intent1 = new Intent(getContext(), DashBoardActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.nav_Login:
//                Intent intent2 = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent2);
//
//                break;
//            case R.id.nav_Logout:
//                mAuth.signOut();
//                Menu menu = navigationView.getMenu();
//                menu.findItem(R.id.nav_Login).setVisible(true);
//                Intent intent3 = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent3);
//                break;

            case R.id.nav_work:
//                Intent intent3 = new Intent(getContext(), HowWeWork.class);
//                startActivity(intent3);
                break;
            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Download the free donating app " + "https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName();
                String shareSubject = "Download the free donating app ";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                startActivity(Intent.createChooser(shareIntent, "Share Via"));
                break;
            case R.id.nav_rate:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(i);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Menu menu = navigationView.getMenu();
//            menu.findItem(R.id.nav_Logout).setVisible(false);
//            menu.findItem(R.id.nav_Profile).setVisible(false);
//            Intent intent =new Intent(MainActivity.this,Login_Activity.class);
//           startActivity(intent);

        } else if (currentUser != null) {
            Menu menu = navigationView.getMenu();
//            menu.findItem(R.id.nav_Login).setVisible(false);

            // userName.setText(getIntent().getStringExtra("name"));
            // GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
//            if (signInAccount!=null){
            //       userName.setText(signInAccount.getGivenName());
            //        userEmail.setText(signInAccount.getEmail());
            //  }
        }
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());

        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private InstallStateUpdatedListener installStateUpdatedListener =new InstallStateUpdatedListener()
    {
        @Override
        public void onStateUpdate(InstallState state)
        {
            if(state.installStatus() == InstallStatus.DOWNLOADED)
            {
                showCompletedUpdate();
            }
        }
    };
    private void showCompletedUpdate()
    {
        Snackbar snackbar = Snackbar.make(getView().findViewById(android.R.id.content),"New app is ready!",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();

    }
    @Override
    public void onStop()
    {
       // if(mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_APP_UPDATE && resultCode != RESULT_OK){
//                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability()== UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){

                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE,
                                getActivity(),RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}