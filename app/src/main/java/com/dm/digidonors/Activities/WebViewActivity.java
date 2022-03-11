package com.dm.digidonors.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.digidonors.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private ImageView backButton;
    private TextView toolbarHeadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView=findViewById(R.id.webView);
        backButton=findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        url=getIntent().getStringExtra("url");


        webView.loadUrl(url);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}