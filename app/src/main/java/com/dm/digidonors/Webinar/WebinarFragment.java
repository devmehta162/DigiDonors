package com.dm.digidonors.Webinar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.SectionPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WebinarFragment extends Fragment {


    ViewPager viewPager;
    TabLayout tableLayout;

    private static final String TAG = "WebinarFragment";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    public WebinarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webinar, container, false);

        mAuth=FirebaseAuth.getInstance();
        setupFirebaseAuth();
        viewPager =(ViewPager) view.findViewById(R.id.viewPager);
        tableLayout =(TabLayout) view.findViewById(R.id.tabLayout);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getChildFragmentManager());

        sectionPageAdapter.addFragment(new UpcomingWebinars(),"Upcoming Webinars");
        sectionPageAdapter.addFragment(new MyWebinars(),"My Webinars");

        viewPager.setAdapter(sectionPageAdapter);
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
        mAuth.addAuthStateListener(mAuthListner);


       checkCurrentFirebaseUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
}