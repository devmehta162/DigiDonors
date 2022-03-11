package com.dm.digidonors.Home;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.digidonors.Activities.HowWeWork;
import com.dm.digidonors.MyDonation.MyDonationsActivity;
import com.dm.digidonors.Activities.ProfileActivity;
import com.dm.digidonors.Activities.WebViewActivity;
import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.Helpers;
import com.dm.digidonors.Utils.OnGoingCampaignAdapter;
import com.dm.digidonors.Utils.RecentlyAddedNgoAdapter;
import com.dm.digidonors.Utils.UniversalImageLoader;
import com.dm.digidonors.models.OnGoingCampaignModel;
import com.dm.digidonors.models.RecentlyAddedNgoModel;
import com.dm.digidonors.models.UserProfile;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainPageFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private static final String STRING_CONSTANT = "DigiDonors";
    private YouTubePlayerView youTubePlayerView;
    //    private PlayerView simpleExoPlayerView;
//    private SimpleExoPlayer simpleExoPlayer;
    public static int gTime, posit, positioninmain, selectionposs;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecentlyAddedNgoAdapter recentlyAddedNgoAdapter;
    private ArrayList<RecentlyAddedNgoModel> recentlyAddedNgoModelArrayList;
    private RecentlyAddedNgoModel recentlyAddedNgoModel = new RecentlyAddedNgoModel();
    private ImageView img_select_category;
    private TextView txtSelectCategory;
    private ImageView circular_background_1;
    private ImageView circular_background2;
    private View view1, view2;
    private TextView NoOfItemsDonated, noOfDonationDone, NoOfPeopleGotHelped;
    public static int flag = 1;

    private CardView cloth;
    private CardView books;
    private CardView blood;
    private CardView food;
    private CardView stationary;
    private CardView shoes;

    private OnGoingCampaignAdapter onGoingCampaignAdapter;
    private ArrayList<OnGoingCampaignModel> onGoingCampaignModelArrayList;
    private OnGoingCampaignModel onGoingCampaignModel = new OnGoingCampaignModel();
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;
    String uid;
    private FirebaseAuth.AuthStateListener mAuthListner;

    private ProgressBar progressBar2;

    private YouTubePlayerTracker tracker;

    private static final String TAG = "MainPageFragment";
    public static String videoid="";


    //    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private Button donateBtn;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toast backToast;
    private long backPressedTime;
    private FirebaseAuth mAuth;
    private UserProfile userProfile = new UserProfile();
    private RecyclerView rvRecentlyAddedNgo, rvOngoingCampaign;
    private ImageView btnOpenNavigarBar;
    private LinearLayout linearLayout_donate;


    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        mAuth = FirebaseAuth.getInstance();

//        videoPlayButton = view.findViewById(R.id.videoPlayButton);
//        videoThubmnailImage = view.findViewById(R.id.videoThubmnailImage);
//        simpleExoPlayerView = view.findViewById(R.id.exoPlayer);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        donateBtn = view.findViewById(R.id.btnDonate);
        btnOpenNavigarBar = view.findViewById(R.id.btnOpenNavigarBar);
//        aspectRatioFrameLayout = view.findViewById(R.id.aspectRatioFrameLayout);
        rvRecentlyAddedNgo = view.findViewById(R.id.rvRecentlyAddedNgo);
        rvOngoingCampaign = view.findViewById(R.id.rvOngoingCampaign);
        linearLayout_donate = view.findViewById(R.id.linearLayout_donate);
        progressBar2 = view.findViewById(R.id.progressBar2);
        circular_background2 = view.findViewById(R.id.circular_background2);
        img_select_category = view.findViewById(R.id.img_select_category);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        txtSelectCategory = view.findViewById(R.id.txtSelectCategory);
        circular_background_1 = view.findViewById(R.id.circular_background_1);

        cloth = view.findViewById(R.id.cloth_card);
        books = view.findViewById(R.id.books_card);
        blood = view.findViewById(R.id.blood_card);
        food = view.findViewById(R.id.food_card);
        stationary = view.findViewById(R.id.stationary_card);
        shoes = view.findViewById(R.id.shoes_card);

        user = mAuth.getCurrentUser();
//        aspectRatioFrameLayout.setAspectRatio(4f / 3f);
        recentlyAddedNgoModelArrayList = new ArrayList<>();
        onGoingCampaignModelArrayList = new ArrayList<>();

        NoOfPeopleGotHelped = view.findViewById(R.id.NoOfPeopleGotHelped);

        NoOfItemsDonated = view.findViewById(R.id.NoOfItemsDonated);

        noOfDonationDone = view.findViewById(R.id.noOfDonationDone);
        tracker = new YouTubePlayerTracker();


        setupFirebaseAuth();
        firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("CountDonated").document("TotalDonations").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String donations = (String) documentSnapshot.get("donations");
                String items = (String) documentSnapshot.get("items");
                String people = (String) documentSnapshot.get("people");
                if (videoid!=null) {
                    videoid = (String) documentSnapshot.get("videoid");

                }
                if (donations != null && items != null && people != null) {
                    NoOfItemsDonated.setText(items);
                    NoOfPeopleGotHelped.setText(people);
                    noOfDonationDone.setText(donations);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);


        // here we are adding observer to our youtubeplayerview.
        getLifecycle().addObserver(youTubePlayerView);

        // below method will provides us the youtube player
        // ui controller such as to play and pause a video
        // to forward a video
        // and many more features.
        youTubePlayerView.getPlayerUiController();

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.addListener(tracker);


                // 8gzEX9SIl4c
//                String videoId = "8gzEX9SIl4c";
                if (videoid!=null){
                    if (!videoid.equals("")) {

                        youTubePlayer.cueVideo(videoid, 0);
                    }
                }else {
                    String videoId = "8gzEX9SIl4c";
                    youTubePlayer.cueVideo(videoId, 0);
                }
                tracker.getState();
                tracker.getCurrentSecond();
                tracker.getVideoDuration();
                tracker.getVideoId();


            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);


                if (state.toString().equals("ENDED")) {


                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar2, "progress", 0, 100);
                    progressAnimator.setDuration(1000);
                    progressAnimator.setInterpolator(new LinearInterpolator());
                    progressAnimator.start();
//                    simpleExoPlayer.setPlayWhenReady(false);


                    flag = 0;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flag = 0;
                            Log.d(TAG, "run: simpleExoPlayerView GONE");
//                            simpleExoPlayerView.setVisibility(View.GONE);
//                            videoThubmnailImage.setVisibility(View.GONE);
//                            aspectRatioFrameLayout.setVisibility(View.GONE);
//                            videoPlayButton.setVisibility(View.GONE);
                            youTubePlayerView.setVisibility(View.GONE);
//                            youTubePlayerView.release();
                            linearLayout_donate.setVisibility(View.VISIBLE);
                            img_select_category.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3EEFF")));
                            circular_background2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#034EBF")));

                            view2.setVisibility(View.VISIBLE);
                            view1.setVisibility(View.GONE);
                            txtSelectCategory.setTextColor(ColorStateList.valueOf(Color.parseColor("#034EBF")));
                        }
                    }, 1000);
                }

            }
        });


        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "cloth");
                startActivity(intent);

                checkCurrentFirebaseUser(user);


            }
        });
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "books");
                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "blood");
                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "food");

                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });
        stationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "stationary");

                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectSlotForDonationActivity.class);
                intent.putExtra("category", "shoes");

                startActivity(intent);
                checkCurrentFirebaseUser(user);
            }
        });
        circular_background2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {


                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar2, "progress", 0, 100);
                    progressAnimator.setDuration(1000);
                    progressAnimator.setInterpolator(new LinearInterpolator());
                    progressAnimator.start();
//                    simpleExoPlayer.setPlayWhenReady(false);
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = "8gzEX9SIl4c";
                            youTubePlayer.cueVideo(videoId, 0);
                            youTubePlayer.pause();

                        }

                        @Override
                        public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {
                            super.onStateChange(youTubePlayer, state);
                            youTubePlayer.pause();
                        }
                    });

                    flag = 0;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flag = 0;
                            Log.d(TAG, "run: simpleExoPlayerView GONE");
//                            simpleExoPlayerView.setVisibility(View.GONE);
//                            videoThubmnailImage.setVisibility(View.GONE);
//                            aspectRatioFrameLayout.setVisibility(View.GONE);
//                            videoPlayButton.setVisibility(View.GONE);
                            youTubePlayerView.setVisibility(View.GONE);

                            linearLayout_donate.setVisibility(View.VISIBLE);
                            img_select_category.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3EEFF")));
                            circular_background2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#034EBF")));

                            view2.setVisibility(View.VISIBLE);
                            view1.setVisibility(View.GONE);
                            txtSelectCategory.setTextColor(ColorStateList.valueOf(Color.parseColor("#034EBF")));
                        }
                    }, 1000);
                }
            }
        });
        circular_background_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (flag == 0) {
                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar2, "progress", 100, 0);
                    progressAnimator.setDuration(1000);
                    progressAnimator.setInterpolator(new LinearInterpolator());
                    progressAnimator.start();
                    flag = 1;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            simpleExoPlayerView.setVisibility(View.VISIBLE);
//                            videoThubmnailImage.setVisibility(View.VISIBLE);
//                            aspectRatioFrameLayout.setVisibility(View.VISIBLE);
//                            videoPlayButton.setVisibility(View.VISIBLE);

                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    String videoId = "8gzEX9SIl4c";
                                    youTubePlayer.cueVideo(videoId, 0);


                                }
                            });
                            youTubePlayerView.setVisibility(View.VISIBLE);
                            linearLayout_donate.setVisibility(View.GONE);
                            img_select_category.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                            circular_background2.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                            view2.setVisibility(View.GONE);
                            view1.setVisibility(View.VISIBLE);
                            txtSelectCategory.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));

                        }
                    }, 1000);


                }


            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false
        );
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false
        );
        rvRecentlyAddedNgo.setLayoutManager(linearLayoutManager);
        rvRecentlyAddedNgo.setItemAnimator(new DefaultItemAnimator());

        rvOngoingCampaign.setLayoutManager(linearLayoutManager2);
        rvOngoingCampaign.setItemAnimator(new DefaultItemAnimator());

        rvRecentlyAddedNgo.setHasFixedSize(true);
        rvOngoingCampaign.setHasFixedSize(true);

        recentlyAddedNgoAdapter = new RecentlyAddedNgoAdapter(getActivity(), recentlyAddedNgoModelArrayList, new RecentlyAddedNgoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(RecentlyAddedNgoModel recentlyAddedNgoModel) {

                Intent intent =new Intent(getActivity(),NgoDetailsActivity.class);
                intent.putExtra("details",recentlyAddedNgoModel);
                startActivity(intent);
//                Toast.makeText(getContext(), ""+recentlyAddedNgoModel.getNgoName(), Toast.LENGTH_SHORT).show();
            }
        });
        rvRecentlyAddedNgo.setAdapter(recentlyAddedNgoAdapter);

        onGoingCampaignAdapter = new OnGoingCampaignAdapter(getActivity(), onGoingCampaignModelArrayList);
        rvOngoingCampaign.setAdapter(onGoingCampaignAdapter);



        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();

        } else {
//            Toast.makeText(getActivity(), "User Not logged In", Toast.LENGTH_SHORT).show();
        }
        firebaseFirestore.collection("Ngo").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot d : list) {
                            RecentlyAddedNgoModel recentlyAddedNgoModel = d.toObject(RecentlyAddedNgoModel.class);
                            recentlyAddedNgoModelArrayList.add(recentlyAddedNgoModel);
                        }
                        String mUserAddress = new Gson().toJson(recentlyAddedNgoModelArrayList);

                        preferences.edit().putString("mRecentlyAddedNgo", mUserAddress).apply();
                        recentlyAddedNgoAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



        firebaseFirestore.collection("onGoingCampaigns").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot d : list) {
                            OnGoingCampaignModel onGoingCampaignModel = d.toObject(OnGoingCampaignModel.class);
                            onGoingCampaignModelArrayList.add(onGoingCampaignModel);
                        }
                        String mUserAddress = new Gson().toJson(onGoingCampaignModelArrayList);

                        preferences.edit().putString("mOnGoingCampaigns", mUserAddress).apply();
                        onGoingCampaignAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        Log.d(TAG, "onCreateView: " + preferences.getString("mUserProfileDetails", ""));
        if (!preferences.getString("mUserProfileDetails", "").equals("")) {
            String mUserProfile = preferences.getString("mUserProfileDetails", "");
            Type type = new TypeToken<UserProfile>() {
            }.getType();

            userProfile = new Gson().fromJson(mUserProfile, type);

            Log.d(TAG, "onCreateView: userProfile.getUserName()" + userProfile.getUserName() + userProfile.getUserEmail());
//


            updateNavHeaderView(userProfile.getUserName(), userProfile.getUserEmail(), userProfile.getUserImageurl());

        } else {
            updateNavHeaderView("", "", "");

        }

        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), DonateActivity.class));
            }
        });
//        simpleExoPlayerView.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 15);
//            }
//        });

//        simpleExoPlayerView.setClipToOutline(true);
//        videoPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoPlayButton.setVisibility(View.GONE);
//                videoThubmnailImage.setVisibility(View.GONE);
//                aspectRatioFrameLayout.setVisibility(View.VISIBLE);
//
//                simpleExoPlayerView.setVisibility(View.VISIBLE);
//                getUrl("https://firebasestorage.googleapis.com/v0/b/digidonors-162.appspot.com/o/mainPageVideo%2FUntitled.mp4?alt=media&token=8d4c80b5-7498-4500-8380-92d34fdaae80");
//
//            }
//        });

        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        toolbar = view.findViewById(R.id.toolbar);

        btnOpenNavigarBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.nav_home);

        return view;

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        if (youTubePlayerView == null) {
//        } else {
//            youTubePlayerView.cu(videoId, seekTime);
//        }
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
////        updateNavHeaderView(userProfile.getUserName(), userProfile.getUserEmail(), userProfile.getUserImageurl());
////        getUrl("https://firebasestorage.googleapis.com/v0/b/digidonors-162.appspot.com/o/mainPageVideo%2F10mb.mp4?alt=media&token=99029825-4155-45f6-b78d-4d61da3dcc63");
//
////        startPlayer(simpleExoPlayer);
//    }

//
//    private void getUrl(String url) {
//        Log.d(TAG, "getUrl: " + url);
//        Uri uri = Uri.parse(url);
//        try {
//            // Create a progressive media source pointing to a stream uri.
//            mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(uri);
//            // Create a player instance.
////            simpleExoPlayer.setAudioAttributes(audioAttributes, /* handleAudioFocus= */ true);
////
////
////            simpleExoPlayerView.setPlayer(simpleExoPlayer);
////            simpleExoPlayer.prepare(mediaSource);
//
//
//            simpleExoPlayer.addListener(new ExoPlayer.EventListener() {
//                @Override
//                public void onTimelineChanged(Timeline timeline, int reason) {
//
//                }
//
//
//                @Override
//                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//                }
//
//                @Override
//                public void onLoadingChanged(boolean isLoading) {
//
//                }
//
//                @Override
//                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//                    switch (playbackState) {
//                        case ExoPlayer.STATE_READY:
//                            Log.d(TAG, "onPlayerStateChanged: STATE_READY");
//                            simpleExoPlayerView.setVisibility(View.VISIBLE);
//                            break;
//                        case ExoPlayer.STATE_ENDED:
//
//                            Log.d(TAG, "onPlayerStateChanged: STATE_ENDED");
//
//                            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar2, "progress", 0, 100);
//                            progressAnimator.setDuration(1000);
//                            progressAnimator.setInterpolator(new LinearInterpolator());
//                            progressAnimator.start();
//
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    flag = 0;
//                                    Log.d(TAG, "run: simpleExoPlayerView GONE");
//                                    simpleExoPlayerView.setVisibility(View.GONE);
//                                    videoThubmnailImage.setVisibility(View.GONE);
//                                    aspectRatioFrameLayout.setVisibility(View.GONE);
//
//                                    videoPlayButton.setVisibility(View.GONE);
//                                    linearLayout_donate.setVisibility(View.VISIBLE);
//                                    img_select_category.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3EEFF")));
//                                    circular_background2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#034EBF")));
//
//                                    view2.setVisibility(View.VISIBLE);
//                                    view1.setVisibility(View.GONE);
//                                    txtSelectCategory.setTextColor(ColorStateList.valueOf(Color.parseColor("#034EBF")));
//                                }
//                            }, 1000);
//
//
//                    }
//
//
//                }
//
//                @Override
//                public void onPlayerError(ExoPlaybackException error) {
//
//                }
//
//
//                @Override
//                public void onPositionDiscontinuity(int reason) {
//
//                }
//
//                @Override
//                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//                }
//            });
//            simpleExoPlayer.setPlayWhenReady(true);
////            simpleExoPlayer.seekTo(getIntent().getIntExtra("gTime", 0));
//            simpleExoPlayer.seekTo(gTime);
//            simpleExoPlayerView.setFastForwardIncrementMs(10000);
//            simpleExoPlayerView.setRewindIncrementMs(10000);
//        } catch (Exception e) {
//        }
//    }


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

        mAuth.addAuthStateListener(mAuthListner);


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
//        int timee = (int) simpleExoPlayer.getCurrentPosition();
//        savedInstanceState.putInt(STRING_CONSTANT, timee);
        //declare values before saving the state
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onStop() {
        super.onStop();
//        pausePlayer(simpleExoPlayer);
//
//        if (!Helpers.isEmpty(simpleExoPlayer)) {
//            simpleExoPlayer.stop();
//        }
//        if (mAuthListner != null) {
//            mAuth.removeAuthStateListener(mAuthListner);
//        }
    }

    @Override
    public void onPause() {
//        if (!Helpers.isEmpty(simpleExoPlayer)) {
//            gTime = ((int) simpleExoPlayer.getCurrentPosition()) - 10;
//        }
        super.onPause();
    }

    private void updateNavHeaderView(String name, String email, String profileImageUri) {


        View header = navigationView.getHeaderView(0);
        TextView userName = header.findViewById(R.id.user_name);
        Button login = header.findViewById(R.id.loginBtn);
        TextView userProfileView = header.findViewById(R.id.view_edit_user_profile_txt);

        ImageView btnUserProfile = header.findViewById(R.id.btnUserProfile);


        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });


        userProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });


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

        Log.d(TAG, "updateNavHeaderView: " + user);

        if (user == null) {
            login.setVisibility(View.VISIBLE);
            userName.setVisibility(View.GONE);
            circleImageView.setVisibility(View.GONE);
            userProfileView.setVisibility(View.GONE);
            btnUserProfile.setVisibility(View.GONE);
        } else {
            login.setVisibility(View.GONE);
            userName.setVisibility(View.VISIBLE);
            circleImageView.setVisibility(View.VISIBLE);
            userProfileView.setVisibility(View.VISIBLE);
            btnUserProfile.setVisibility(View.VISIBLE);
        }
        //  checkCurrentFirebaseUser(user);

//        if (userName == null) {
//            login.setVisibility(View.VISIBLE);
//
//        }
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
//                Intent intent1 = new Intent(getContext(), DonateActivity.class);
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

            case R.id.nav_my_donation:
                Intent intentMyDonations = new Intent(getContext(), MyDonationsActivity.class);
                startActivity(intentMyDonations);

                break;
            case R.id.nav_work:
                Intent intent4 = new Intent(getContext(), HowWeWork.class);
                startActivity(intent4);
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
            case R.id.privacy_policy:

                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "https://sites.google.com/view/digidonors-privacypolicy/home");
                startActivity(intent);

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());

        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void checkCurrentFirebaseUser(FirebaseUser user) {


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
    public void onDestroy() {

        super.onDestroy();
        youTubePlayerView.release();

    }


}
