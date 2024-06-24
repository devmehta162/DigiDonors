package com.dm.digidonors.Home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.dm.digidonors.R;
import com.dm.digidonors.Utils.SelectSlotDialog;
import com.dm.digidonors.Utils.UserAddressAdapter;
import com.dm.digidonors.models.DetailsOfDonation;
import com.dm.digidonors.models.UserAddressModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SelectSlotForDonationActivity extends AppCompatActivity implements SelectSlotDialog.DialogToConfirmListner {


    private FirebaseFirestore firebaseFirestore;
    private UserAddressAdapter userAddressAdapter;

    private Toolbar toolbar;

    private ArrayList<UserAddressModel> userAddressModelArrayList2;
    //  firebase
    private FirebaseAuth mAuth;
    String uid;
    private FirebaseAuth.AuthStateListener mAuthListner;

    public static String serviceType="";
    public TextView name;
    public TextView mobileNo;
    public TextView address;
    public TextView pincode;
    public TextView city;
    public TextView state;
    private Button changeOrAddAddressBtn;
    private TextView selectDateTimeForDonation;
    String tomorrowAsString, dayAfterTomorrow2;
    public static String date, time;
    private TextView QuantityTxt, BloodGroupTxt;
    private EditText noOfClothes, noOfBooks, whichBloodGroup, description;
    private Button nextBtn;
    private TextView toolbarHeadingText;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("DetailsOfDonation");

    public int id = 0;

    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_NO = "No";
    private DetailsOfDonation detailsOfDonation = new DetailsOfDonation();
    private ImageView backButton;

    private static final String TAG = "SelectSlotForDonationAc";
    private UserAddressModel userAddressModelselected = new UserAddressModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: onCreate lifecycle");

        setContentView(R.layout.activity_select_slot_for_donation);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SelectSlotForDonationActivity.this);

        name = findViewById(R.id.name);
        mobileNo = findViewById(R.id.mobileNo);
        pincode = findViewById(R.id.pincode);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        changeOrAddAddressBtn = findViewById(R.id.changeOrAddAddressBtn);
        selectDateTimeForDonation = findViewById(R.id.select_date_time_for_donation);
        QuantityTxt = findViewById(R.id.quatityTxt);
        BloodGroupTxt = findViewById(R.id.bloodTxt);
        noOfClothes = findViewById(R.id.noOfClothes);
        noOfBooks = findViewById(R.id.noOfBooks);
        whichBloodGroup = findViewById(R.id.whichBloodGroup);
        nextBtn = findViewById(R.id.nextBtn);
        description = findViewById(R.id.description);
        backButton = findViewById(R.id.backButton);
        toolbarHeadingText = findViewById(R.id.toolbarHeadingText);


        toolbarHeadingText.setText("Select Time and Date");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        String category = getIntent().getStringExtra("category");

        if (category != null) {
            switch (category) {
                case "cloth":
                    QuantityTxt.setVisibility(View.VISIBLE);
                    noOfClothes.setVisibility(View.VISIBLE);
                    break;
                case "books":
                    noOfClothes.setVisibility(View.VISIBLE);

                    noOfClothes.setHint("Number of books you want to donate");
                    QuantityTxt.setVisibility(View.VISIBLE);
//            noOfBooks.setVisibility(View.VISIBLE);
                    break;
                case "blood":

                    BloodGroupTxt.setVisibility(View.VISIBLE);
                    whichBloodGroup.setVisibility(View.VISIBLE);
                    break;
                case "stationary":
                case "food":
                    noOfClothes.setVisibility(View.VISIBLE);
                    QuantityTxt.setVisibility(View.VISIBLE);
                    noOfClothes.setHint("Number of items you want to donate");
                    break;
                case "shoes":
                    noOfClothes.setVisibility(View.VISIBLE);
                    QuantityTxt.setVisibility(View.VISIBLE);
                    noOfClothes.setHint("Number of shoes you want to donate");
                    break;
            }
        }


        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dayAfterTomorrow = calendar.getTime();


        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");

        String todayAsString = dateFormat.format(today);
        tomorrowAsString = dateFormat.format(tomorrow);
        dayAfterTomorrow2 = dateFormat.format(dayAfterTomorrow);

        date = tomorrowAsString;
        time = "10 AM - 1 PM";

        selectDateTimeForDonation.setText(date + " " + time);


        selectDateTimeForDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

//
//        Log.d(TAG, "onCreate: dayAfterTomorrow2 " + dayAfterTomorrow2
//                + "\ntomorrowAsString " + tomorrowAsString);



        changeOrAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectSlotForDonationActivity.this, UserAddressActivity.class));
            }
        });


        userAddressModelArrayList2 = new ArrayList<>();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();

        } else {
//            Toast.makeText(this, "User Not logged In", Toast.LENGTH_SHORT).show();
        }


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nextBtn.getApplicationWindowToken(), 0);
                if (!noOfClothes.getText().toString().trim().isEmpty()) {
                    detailsOfDonation.setName(name.getText().toString().trim());
                    detailsOfDonation.setPhone(mobileNo.getText().toString().trim());
                    detailsOfDonation.setAddress(address.getText().toString().trim());
                    detailsOfDonation.setCategory(category);
                    detailsOfDonation.setDateforpickup(date);
                    detailsOfDonation.setTimeforpickup(time);
                    detailsOfDonation.setCity(city.getText().toString().trim());
                    detailsOfDonation.setState(state.getText().toString().trim().substring(1));
                    detailsOfDonation.setPincode(pincode.getText().toString().trim().substring(1));
                    detailsOfDonation.setNo(noOfClothes.getText().toString());
                    detailsOfDonation.setDescription(description.getText().toString().trim());
                    detailsOfDonation.setTime(currentDateandTime);



                    btnSHowDialog(detailsOfDonation);

//
//                    collectionReference.document(myId).set(detailsOfDonation).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            startActivity(new Intent(SelectSlotForDonationActivity.this, FinalActivity.class));
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(SelectSlotForDonationActivity.this, "Internet not connected", Toast.LENGTH_SHORT).show();
//                        }
//                    });

                } else {
                    Toast.makeText(SelectSlotForDonationActivity.this, "Enter the Quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (!preferences.getString("mUserSelectedAddress", "").equals("")) {
//            Log.d(TAG, "onCreate: preferences");
            String mUserAddress = preferences.getString("mUserSelectedAddress", "");
            Type type = new TypeToken<UserAddressModel>() {
            }.getType();

            userAddressModelselected = new Gson().fromJson(mUserAddress, type);
            setInformationOfUserSelectedAddress();
            if (mUserAddress.equals("[]")) {
                Intent intent = new Intent(SelectSlotForDonationActivity.this, AddNewAddress.class);
                startActivity(intent);
                finish();
            }

        } else {
            if (!preferences.getString("mUserAddress", "").equals("")) {
//                Log.d(TAG, "onCreate: preferences");
                String mUserAddress = preferences.getString("mUserAddress", "");
                Type type = new TypeToken<ArrayList<UserAddressModel>>() {
                }.getType();
                userAddressModelArrayList2 = new Gson().fromJson(mUserAddress, type);
                if (mUserAddress.equals("[]")) {
                    Intent intent = new Intent(SelectSlotForDonationActivity.this, AddNewAddress.class);
                    startActivity(intent);
                    finish();
                }
                setInformationOfUserAddress();

            } else {
//                Log.d(TAG, "onCreate: firebase");
                firebaseFirestore.collection("Users").document(uid).collection("Address").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : list) {
                                    UserAddressModel userAddressModel = d.toObject(UserAddressModel.class);
                                    userAddressModelArrayList2.add(userAddressModel);

                                }


                                String mUserAddress = new Gson().toJson(userAddressModelArrayList2);

                                preferences.edit().putString("mUserAddress", mUserAddress).apply();
                                setInformationOfUserAddress();
                                if (mUserAddress.equals("[]")) {
                                    Intent intent = new Intent(SelectSlotForDonationActivity.this, AddNewAddress.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }
        }
    }

    private void setInformationOfUserSelectedAddress() {
        if (userAddressModelselected != null) {
            name.setText(userAddressModelselected.getName());
            mobileNo.setText(userAddressModelselected.getPhone());
            pincode.setText(userAddressModelselected.getPincode());
            address.setText(userAddressModelselected.getAddress());
            city.setText(userAddressModelselected.getCity());
            state.setText("," + userAddressModelselected.getState());


        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openDialog() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(selectDateTimeForDonation.getApplicationWindowToken(), 0);
        SelectSlotDialog selectSlotDialog = new SelectSlotDialog();
        Bundle bundle = new Bundle();
        bundle.putString("TomorrowDate", tomorrowAsString);
        bundle.putString("NextDate", dayAfterTomorrow2);
        selectSlotDialog.setArguments(bundle);
        selectSlotDialog.setCancelable(false);
        selectSlotDialog.show(getSupportFragmentManager(), "select time slot dialog");
    }


    private void setInformationOfUserAddress() {
        if (userAddressModelArrayList2 != null && userAddressModelArrayList2.size() != 0) {
            name.setText(userAddressModelArrayList2.get(0).getName());
            mobileNo.setText(userAddressModelArrayList2.get(0).getPhone());
            pincode.setText("," + userAddressModelArrayList2.get(0).getPincode());
            address.setText(userAddressModelArrayList2.get(0).getAddress());
            city.setText(userAddressModelArrayList2.get(0).getCity().trim());
//            state.setText(MessageFormat.format(",", userAddressModelArrayList2.get(0).getState()));
            state.setText("," + userAddressModelArrayList2.get(0).getState());


        }
    }

    public String gen() {
        Random r = new Random(System.currentTimeMillis());
        return String.valueOf((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private void changeColorBack(Button textView) {
//        textView.setBackgroundResource(R.drawable.curveborders);
//        textView.setBackgroundColor(textView.getContext().getResources().getColor(R.color.purple_200));

        textView.setBackground(getResources().getDrawable(R.drawable.curveborders));


    }

    private void changeColor(Button textView) {
//        Drawable tempDrawable = getResources().getDrawable(R.drawable.curveborders);
//        LayerDrawable bubble = (LayerDrawable) tempDrawable; //(cast to root element in xml)
//        GradientDrawable solidColor = (GradientDrawable) bubble.findDrawableByLayerId(R.color.green);
//        solidColor.setColor(getResources().getColor(R.color.green));
//        textView.setBackground(tempDrawable);
//        textView.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void sendtext(String datefromInterface, String timefromInterface) {
        date = datefromInterface;
        time = timefromInterface;

        selectDateTimeForDonation.setText(date + " " + time);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onCreate: onResume lifecycle");

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SelectSlotForDonationActivity.this);

        if (!preferences.getString("mUserSelectedAddress", "").equals("")) {
            Log.d(TAG, "onCreate: preferences");
            String mUserAddress = preferences.getString("mUserSelectedAddress", "");
            Type type = new TypeToken<UserAddressModel>() {
            }.getType();

            userAddressModelselected = new Gson().fromJson(mUserAddress, type);
            setInformationOfUserSelectedAddress();

            if (mUserAddress.equals("[]")) {
                Intent intent = new Intent(SelectSlotForDonationActivity.this, DonateActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }

    public void btnSHowDialog(DetailsOfDonation detailsOfDonation) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(SelectSlotForDonationActivity.this);

        View mView = getLayoutInflater().inflate(R.layout.service_type_dialog_box_layout, null);


        alert.setView(mView);



        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        LinearLayout llSelfDrop = (LinearLayout) mView.findViewById(R.id.llSelfDrop);
        LinearLayout llPickUpHome = (LinearLayout) mView.findViewById(R.id.llPickUpHome);
        Button btnFinish = (Button) mView.findViewById(R.id.btnFinish);
        TextView txtSelfDrop = (TextView) mView.findViewById(R.id.txtSelfDrop);
        TextView txtpickUpHome = (TextView) mView.findViewById(R.id.txtpickUpHome);
        ImageView backDialogBox = (ImageView)mView.findViewById(R.id.backDialogBox);



        llSelfDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llSelfDrop.setBackgroundTintList(ContextCompat.getColorStateList(SelectSlotForDonationActivity.this, R.color.appButtonColor));
                llPickUpHome.setBackgroundTintList(ContextCompat.getColorStateList(SelectSlotForDonationActivity.this, R.color.whiteSmoke));

                serviceType="SelfDrop";

            }
        });

        llPickUpHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPickUpHome.setBackgroundTintList(ContextCompat.getColorStateList(SelectSlotForDonationActivity.this, R.color.appButtonColor));
                llSelfDrop.setBackgroundTintList(ContextCompat.getColorStateList(SelectSlotForDonationActivity.this, R.color.whiteSmoke));

                serviceType="PickUp From Home";
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (serviceType.equals("")){
                    Toast.makeText(SelectSlotForDonationActivity.this, "Select one option", Toast.LENGTH_SHORT).show();
                }else {
                    String number = gen();
                    String otp = getRandomNumberString();
                    detailsOfDonation.setUserid(uid);
                    detailsOfDonation.setFcmtoken(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "defaultStringIfNothingFound"));
                    detailsOfDonation.setDonationid(number);
                    detailsOfDonation.setStatus("Confirmed");
                    detailsOfDonation.setOtp(otp);
                    String myId = db.collection("DetailsOfDonation").document().getId();
                    detailsOfDonation.setUserdonateid(myId);

                    detailsOfDonation.setServiceType(serviceType);
                    collectionReference.document(myId).set(detailsOfDonation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(SelectSlotForDonationActivity.this, FinalActivity.class));


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SelectSlotForDonationActivity.this, "Internet not connected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        backDialogBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });


        alertDialog.show();

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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onCreate: onStart lifecycle");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onCreate: onStop lifecycle");

    }
}