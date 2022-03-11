package com.dm.digidonors.SocialMedia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.dm.digidonors.Login.LoginActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.MainfeedListAdapter;
import com.dm.digidonors.models.Comment;
import com.dm.digidonors.models.Photo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SocialMediaFragment extends Fragment {

    public static final String TAG = "SocialMediaFragment";

    //vars
    private ArrayList<Photo> mPhotos;
    private ArrayList<Photo> mPaginatedPhotos;
    private ArrayList<String> mFollowing;
    private ListView mListView;
    private MainfeedListAdapter mAdapter;
    private int mResults;

   // firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_media, container, false);

        mAuth=FirebaseAuth.getInstance();
        setupFirebaseAuth();

        mListView = (ListView) view.findViewById(R.id.listView);
        mFollowing = new ArrayList<>();
        mPhotos = new ArrayList<>();
        getPhotos();
        // getFollowing();
        return view;
    }
    private void getPhotos() {
        Log.d(TAG, "getPhotos: getting photos");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        for(int i = 0; i < mFollowing.size(); i++){
//            final int count = i;
        Query query = reference
                .child("photos")
                .orderByChild(getString(R.string.field_user_id));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Photo photo = new Photo();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();


                    if (objectMap!=null&&getActivity()!=null) {
                        photo.setCaption(objectMap.get(getActivity().getString(R.string.field_caption)).toString());
                        photo.setTags(objectMap.get(getActivity().getString(R.string.field_tags)).toString());
                        photo.setPhoto_id(objectMap.get(getActivity().getString(R.string.field_photo_id)).toString());
                        photo.setUser_id(objectMap.get(getActivity().getString(R.string.field_user_id)).toString());
                        photo.setDate_created(objectMap.get(getActivity().getString(R.string.field_date_created)).toString());
                        photo.setImage_path(objectMap.get(getActivity().getString(R.string.field_image_path)).toString());
                    }
//                    ArrayList<Comment> comments = new ArrayList<Comment>();
//                    for (DataSnapshot dSnapshot : singleSnapshot
//                            .child(getString(R.string.field_comments)).getChildren()) {
//                        Comment comment = new Comment();
//                        comment.setUser_id(dSnapshot.getValue(Comment.class).getUser_id());
//                        comment.setComment(dSnapshot.getValue(Comment.class).getComment());
//                        comment.setDate_created(dSnapshot.getValue(Comment.class).getDate_created());
//                        comments.add(comment);
//                    }

                  //  photo.setComments(comments);
                    mPhotos.add(photo);
                }

                //display our photos
                displayPhotos();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // }
    }
    private void displayPhotos() {
        if(mPhotos != null){
            Collections.sort(mPhotos, new Comparator<Photo>() {
                @Override
                public int compare(Photo o1, Photo o2) {
                    if (o2!=null&&o2.getDate_created()!=null&&o1!=null&&o1.getDate_created()!=null) {

                        return o2.getDate_created().compareTo(o1.getDate_created());
                    }else {
                        return 0;
                    }
                }
            });

            if (getActivity()!=null) {
                mAdapter = new MainfeedListAdapter(getActivity(), R.layout.layout_mainfeed_listitem, mPhotos);
                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

        }
    }
    public void displayMorePhotos() {
        Log.d(TAG, "displayMorePhotos: displaying more photos");

        try {

            if (mPhotos.size() > mResults && mPhotos.size() > 0) {

                int iterations;
                if (mPhotos.size() > (mResults + 10)) {
                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
                    iterations = 10;
                } else {
                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
                    iterations = mPhotos.size() - mResults;
                }

                //add the new photos to the paginated results
                for (int i = mResults; i < mResults + iterations; i++) {
                    mPaginatedPhotos.add(mPhotos.get(i));
                }
                mResults = mResults + iterations;
                mAdapter.notifyDataSetChanged();
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage());
        }
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