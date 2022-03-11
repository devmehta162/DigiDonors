package com.dm.digidonors.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.digidonors.MainActivity;
import com.dm.digidonors.R;
import com.dm.digidonors.Utils.OnboardingItem;
import com.dm.digidonors.Utils.onBoardingAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HowWeWork extends AppCompatActivity {


    private onBoardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    //    private Toolbar toolbar;
    private MaterialButton buttonOnboardingAction;
    private TextView toolbarHeadingText;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_we_work);
        toolbarHeadingText = findViewById(R.id.toolbarHeadingText);
        backButton=findViewById(R.id.backButton);
//
//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("How we Work");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        toolbarHeadingText.setText("How we Work");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      Toast.makeText(HowWeWork.this, "back button pressed", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(HowWeWork.this, "back button pressed", Toast.LENGTH_SHORT).show();
//                Intent intent =new Intent(HowWeWork.this,MainActivity.class);
//                startActivity(intent);
//                onBackPressed();
//                finish();
//            }
//        });
        setupOnBiadringItems();
        final ViewPager2 onboardingViewwPager = findViewById(R.id.onBoardingViewPager);

        onboardingViewwPager.setAdapter(onboardingAdapter);
        setupOnboardingIndicators();
        setCurrentOnboardingIndicators(0);
        onboardingViewwPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewwPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {

                    onboardingViewwPager.setCurrentItem(onboardingViewwPager.getCurrentItem() + 1);

                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                }
            }
        });
    }

    private void setupOnBiadringItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem Schedule = new OnboardingItem();
        Schedule.setTitle("Schedule a Pickup");
        Schedule.setDescription("Enter the pickup location, and schedule a pickup.\n" +
                "You can also choose to go for the drop off option in case you want to drop the donations yourself.");
        Schedule.setImage(R.drawable.schedule);

        OnboardingItem DoorStep = new OnboardingItem();
        DoorStep.setTitle("Donate at your Doorstep");
        DoorStep.setDescription("We will come to your doorstep to pick up the donations in the chosen slot and deliver them to the NGO where they can be given a new life.");
        DoorStep.setImage(R.drawable.doorstep);

        OnboardingItem Rewards = new OnboardingItem();
        Rewards.setTitle("Get Rewards");
        Rewards.setDescription("Our brand partners provide our donors gifts* as a “gesture of thanks” for making a difference.\n" +
                "Be ready to get surprised!");
        Rewards.setImage(R.drawable.rewards);


        onboardingItems.add(Schedule);
        onboardingItems.add(DoorStep);
        onboardingItems.add(Rewards);

        onboardingAdapter = new onBoardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);

        }
    }

    private void setCurrentOnboardingIndicators(int index) {

        int childCount = layoutOnboardingIndicators.getChildCount();

        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("Start Donating");
        } else {
            buttonOnboardingAction.setText("Next");
        }
    }
}
