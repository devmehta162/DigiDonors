package com.dm.digidonors.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dm.digidonors.R;

import java.util.List;

public class onBoardingAdapter extends RecyclerView.Adapter<onBoardingAdapter.onboardingViewHolder>{

    private List<OnboardingItem> onboardingItems;

    public onBoardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public onboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new onboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_onboarding,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull onboardingViewHolder holder, int position) {

        holder.setOnBoardingData(onboardingItems.get(position));
        Glide.with(holder.itemView.getContext()).load(onboardingItems.get(position).getImage()).into(holder.imageonBoarding);
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class onboardingViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageonBoarding;

        public onboardingViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle=itemView.findViewById(R.id.textTitle);
            textDescription=itemView.findViewById(R.id.textDescription);
            imageonBoarding=itemView.findViewById(R.id.imageonBoarding);
        }
        void  setOnBoardingData(OnboardingItem onboardingItem){
            textTitle.setText(onboardingItem.getTitle());
            textDescription.setText(onboardingItem.getDescription());
            imageonBoarding.setImageResource(onboardingItem.getImage());
        }
    }
}

