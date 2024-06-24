package com.dm.digidonors.Home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dm.digidonors.R;
import com.dm.digidonors.models.RecentlyAddedNgoModel;

public class NgoDetailsActivity extends AppCompatActivity {

    private TextView ngoName,ngoAddress,ngoDetails,ngoEmail,ngoPhone,ngoWebsite;

    private TextView toolbarHeadingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_details);
        ngoName=findViewById(R.id.ngoName);
        ngoAddress=findViewById(R.id.ngoAddress);
        ngoDetails=findViewById(R.id.ngoDetails);
        ngoEmail=findViewById(R.id.ngoEmail);
        ngoPhone=findViewById(R.id.ngoPhone);
        ngoWebsite=findViewById(R.id.ngoWebsite);


        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);



        RecentlyAddedNgoModel recentlyAddedNgoModel = getIntent().getParcelableExtra("details");

        ngoName.setText(recentlyAddedNgoModel.getNgoName());
        ngoAddress.setText(recentlyAddedNgoModel.getNgoAddress());
        ngoDetails.setText(recentlyAddedNgoModel.getNgoDetails());
        ngoEmail.setText(recentlyAddedNgoModel.getEmail());
        ngoPhone.setText(recentlyAddedNgoModel.getPhonenumber());
        ngoWebsite.setText(recentlyAddedNgoModel.getWebsitelink());

//        Toast.makeText(this, ""+recentlyAddedNgoModel.getNgoName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}