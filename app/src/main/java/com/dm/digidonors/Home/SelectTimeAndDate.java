package com.dm.digidonors.Home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dm.digidonors.R;
import com.dm.digidonors.models.DetailsOfDonation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectTimeAndDate extends AppCompatActivity {

    private EditText timeFromView,dateView;
    private DatePickerDialog datePickerDialog;
    private Calendar myCalendar;
    private String date;
    private String timeFrom;
    private Toolbar toolbar;
    private Button finishBtn;
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
    private DetailsOfDonation detailsOfDonation=new DetailsOfDonation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time_and_date);

        init();

        final String name= getIntent().getStringExtra("name");
        final String address= getIntent().getStringExtra("address");
        final String city= getIntent().getStringExtra("city");
        String dateAdded= getIntent().getStringExtra("dateAdded");
        final String mobileNo= getIntent().getStringExtra("mobileNo");
        String pinCode= getIntent().getStringExtra("pinCode");
        String category= getIntent().getStringExtra("category");
        final String state= getIntent().getStringExtra("state");
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                startActivity(new Intent(SelectTimeAndDate.this,FinalActivity.class));
                String dateforpickup = dateView.getText().toString().trim();
                String timeforpickup = timeFromView.getText().toString().trim();

                detailsOfDonation.setName(name);
                detailsOfDonation.setPhone(mobileNo);
                detailsOfDonation.setAddress(address);

                detailsOfDonation.setCategory(category);
                detailsOfDonation.setDateforpickup(dateforpickup);
                detailsOfDonation.setTimeforpickup(timeforpickup);
                detailsOfDonation.setCity(city);
                detailsOfDonation.setState(state);
                detailsOfDonation.setPincode(pinCode);
                //   donates.setParticipate(participate);
                //   donates.setNo(No);


                collectionReference.add(detailsOfDonation);
            }
        });


        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }

        };

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datePickerDialog = new DatePickerDialog(SelectTimeAndDate.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));//.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
                datePickerDialog.show();
            }
        });

        timeFromView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SelectTimeAndDate.this , android.app.AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hr,min;
                        if(selectedHour<10)
                            hr="0"+ selectedHour;
                        else
                            hr=String.valueOf(selectedHour);

                        if(selectedMinute<10)
                            min="0"+ selectedMinute;
                        else
                            min=String.valueOf(selectedMinute);
                        timeFromView.setText(hr + ":" +min);
                        timeFrom = timeFromView.getText().toString();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });
    }

    private void init() {
        timeFromView=findViewById(R.id.input_time_from);
        dateView=findViewById(R.id.input_date);
        myCalendar = Calendar.getInstance();
        toolbar=findViewById(R.id.toolbar);
        finishBtn=findViewById(R.id.finishBtn);



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Date and Time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void updateLabelDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date = sdf.format(myCalendar.getTime());
        dateView.setText(date);
    }
}