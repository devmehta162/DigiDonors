package com.dm.digidonors.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dm.digidonors.R;
import com.dm.digidonors.models.OnGoingCampaignModel;
import com.dm.digidonors.models.RecentlyAddedNgoModel;

import java.util.ArrayList;

public class OnGoingCampaignAdapter extends RecyclerView.Adapter<OnGoingCampaignAdapter.OnGoingCampaignHolder>{

    private Context mContext;
    private static final String TAG = "OnGoingCampaignAdapter";
    private ArrayList<OnGoingCampaignModel> onGoingCampaignModelArrayList;

    public OnGoingCampaignAdapter() {
    }

    public OnGoingCampaignAdapter(Context mContext, ArrayList<OnGoingCampaignModel> onGoingCampaignModelArrayList) {
        this.mContext = mContext;
        this.onGoingCampaignModelArrayList = onGoingCampaignModelArrayList;
    }

    @NonNull
    @Override
    public OnGoingCampaignAdapter.OnGoingCampaignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_on_going_campaign_list_item,
                parent, false);
        return new OnGoingCampaignAdapter.OnGoingCampaignHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnGoingCampaignHolder holder, int position) {
        holder.campaignHeading.setText(onGoingCampaignModelArrayList.get(position).getCampaignHeading());

        holder.campaignDetails.setText(onGoingCampaignModelArrayList.get(position).getCampaignDetails());

        Glide.with(mContext).load(onGoingCampaignModelArrayList.get(position).getCampaignImageUrl()).into(holder.campaignImage);

    }

    @Override
    public int getItemCount() {
        return onGoingCampaignModelArrayList.size();
    }

    public class OnGoingCampaignHolder extends RecyclerView.ViewHolder {

        private ImageView campaignImage;
        private TextView campaignHeading,campaignDetails;
        public OnGoingCampaignHolder(@NonNull View itemView) {
            super(itemView);
            campaignImage=itemView.findViewById(R.id.campaignImageUrl);
            campaignHeading=itemView.findViewById(R.id.campaignHeading);
            campaignDetails=itemView.findViewById(R.id.campaignDetails);
        }
    }
}
