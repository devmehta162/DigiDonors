package com.dm.digidonors.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;

public class FinalActivity extends AppCompatActivity {
    private Button main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        main=findViewById(R.id.main_menu);

        AlertDialog.Builder builder = new AlertDialog.Builder(FinalActivity.this);
        builder.setMessage("A person from our team will contact you within 24 hours to confirm date and time for the pick up.");
        builder.setCancelable(false);

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FinalActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FinalActivity.this, MainActivity.class);
        startActivity(intent);
    }
}