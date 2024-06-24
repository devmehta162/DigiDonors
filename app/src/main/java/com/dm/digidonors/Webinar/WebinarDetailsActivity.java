package com.dm.digidonors.Webinar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.digidonors.Home.FinalActivity;
import com.dm.digidonors.Home.SelectSlotForDonationActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.models.DetailsOfDonation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class WebinarDetailsActivity extends AppCompatActivity implements DialogToConfirm.DialogToConfirmListner {

    private ImageView imageView;
    private TextView webinarDate, webinarTitle, webinarDetails, webinarTime;
    String title, description, date, time, duration, webinarId;
    private Button registerWebinar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;
    private FirebaseAuth mAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webinar_details);
        imageView = findViewById(R.id.webinarImage);
        webinarDate = findViewById(R.id.webinateDate);
        webinarTitle = findViewById(R.id.webinar_title);
        webinarDetails = findViewById(R.id.webinar_description);
        webinarTime = findViewById(R.id.webinateTimings);
        registerWebinar = findViewById(R.id.registerWebinar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        if (user != null) {
            uid = user.getUid();
//            Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        }


        registerWebinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSHowDialog();

            }
        });

        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");
        description = getIntent().getStringExtra("description");
        date = getIntent().getStringExtra("date");
        webinarId = getIntent().getStringExtra("webinarId");


        setData();

    }

    public void btnSHowDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(WebinarDetailsActivity.this);

        View mView = getLayoutInflater().inflate(R.layout.register_user_webinar_dialog_box_layout, null);


        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button registerFinal = (Button) mView.findViewById(R.id.registerFinal);
        ImageView backDialogBox = (ImageView) mView.findViewById(R.id.backDialogBox);
        EditText name = (EditText) mView.findViewById(R.id.enterName);
        EditText email = (EditText) mView.findViewById(R.id.enterEmail);
        EditText phoneNumber = (EditText) mView.findViewById(R.id.enterPhoneNumber);


        registerFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                sendtext(name.getText().toString().trim(), email.getText().toString().trim(), phoneNumber.getText().toString().trim());

                if (!name.getText().toString().trim().isEmpty() && !email.getText().toString().trim().isEmpty() && !phoneNumber.getText().toString().trim().isEmpty()&& phoneNumber.getText().toString().trim().length()!=10) {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", name.getText().toString().trim());
                    map.put("emailid", email.getText().toString().trim());
                    map.put("phoneNumber", phoneNumber.getText().toString().trim());
                    map.put("webinarId", webinarId);

                    collectionReference = db.collection("WebinardetailsOfUser").document(uid).collection("webinar");
                    collectionReference.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(WebinarDetailsActivity.this, "Registered", Toast.LENGTH_SHORT).show();

                            alertDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(WebinarDetailsActivity.this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                          Toast.makeText(WebinarDetailsActivity.this, "Please Enter the details", Toast.LENGTH_SHORT).show();
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


    private void openDialog() {

        DialogToConfirm dialogToConfirm = new DialogToConfirm();
        dialogToConfirm.show(getSupportFragmentManager(), "confirmation ");
    }

    private void setData() {
        webinarTitle.setText(title);
        webinarDetails.setText(description);
        webinarDate.setText(date);
        webinarTime.setText(time);
    }

    @Override
    public void sendtext(String name, String email, String phone) {

        if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {

            HashMap<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("emailid", email);
            map.put("phoneNumber", phone);
            map.put("webinarId", webinarId);

            collectionReference = db.collection("WebinardetailsOfUser").document(uid).collection("webinar");
            collectionReference.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(WebinarDetailsActivity.this, "addedSuccesfully", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            //      Toast.makeText(this, "efefe", Toast.LENGTH_SHORT).show();
        }
    }
}