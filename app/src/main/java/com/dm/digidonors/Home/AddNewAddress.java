package com.dm.digidonors.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dm.digidonors.Activities.HowWeWork;
import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.models.AddNewAddressModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddNewAddress extends AppCompatActivity {


    private TextView entercity;
    private TextView enterstate;
    private Button entersumit;
    private ImageView backButton;

    private EditText entername, enterphone, enteraddress, enterpincode;
    private TextView toolbarHeadingText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseFirestore db2 = FirebaseFirestore.getInstance();

    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    String uid;


    private String name;
    private String phone;
    private String pincode;
    private String address;
    private String city;
    private String state;

    public static final String KEY_NAME="name";
    public static final String KEY_PHONE="phone";
    public static final String KEY_PINCODE="pincode";
    public static final String KEY_ADDRESS="address";
    public static final String KEY_CITY="city";
    public static final String KEY_STATE="state";

    private CollectionReference collectionReference2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        entername = findViewById(R.id.enter_name);
        enterphone = findViewById(R.id.enter_phone);
        enteraddress = findViewById(R.id.enter_address);
        entersumit = findViewById(R.id.sumit_button);
        entercity = findViewById(R.id.enter_city);
        enterstate = findViewById(R.id.enter_state);
        enterpincode = findViewById(R.id.enter_pincode);
        mAuth = FirebaseAuth.getInstance();
        backButton=findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AddNewAddress.this);

        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);


        toolbarHeadingText.setText("Add New Address");

        entersumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entersumit.setEnabled(false);
                FirebaseUser user = mAuth.getCurrentUser();
                if (user!=null){
                    uid = user.getUid();

                }

                name=entername.getText().toString().trim();
                phone=enterphone.getText().toString().trim();
                address=enteraddress.getText().toString().trim();
                city=entercity.getText().toString().trim();
                state=enterstate.getText().toString().trim();
                pincode=enterpincode.getText().toString().trim();


                if (name!=null&&phone!=null&&address!=null&&pincode!=null && phone.length()==10) {
                    collectionReference2=db2.collection("Users").document(uid).collection("Address");
                    AddNewAddressModel addNewAddressModel =new AddNewAddressModel();

                    addNewAddressModel.setName(name);
                    addNewAddressModel.setPhone(phone);
                    addNewAddressModel.setAddress(address);
                    addNewAddressModel.setCity(city);
                    addNewAddressModel.setState(state);
                    addNewAddressModel.setPincode(pincode);


                    collectionReference2.add(addNewAddressModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {


                            if (!preferences.getString("mUserAddress", "").equals("")) {

                                preferences.edit().remove("mUserAddress").commit();

                            }
                            onBackPressed();
                            entersumit.setEnabled(true);

                            Toast.makeText(AddNewAddress.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            entersumit.setEnabled(true);
                        }
                    });

                }else {
                    Toast.makeText(AddNewAddress.this, "Enter Full Details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        enterpincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && enterpincode.getText().toString() != null) {
                    fetchdata();
                }

            }
        });
    }
    private void fetchdata() {
        String pincode = enterpincode.getText().toString().trim();
//        donates.setPincode(pincode);
        String url = "https://api.postalpincode.in/pincode/" + pincode;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response.toString());
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("PostOffice");

                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
//                                   JSONObject object1 = new JSONObject(response);
//                                    JSONArray jsonArray = object1.getJSONArray("PostOffice");
//
//
//                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                            entercity.setText(jsonObject1.getString("District"));
                            enterstate.setText(jsonObject1.getString("State"));


//                            donates.setCity(entercity);
//                            donates.setState(state);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });

        RequestQueue requestQueue = Volley.newRequestQueue(AddNewAddress.this);
        requestQueue.add(stringRequest);
        // requestQueue.add(stringRequest2);
    }

}