package com.dm.digidonors.Utils;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
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
import com.dm.digidonors.models.RecentlyAddedNgoModel;
import com.dm.digidonors.models.UserAddressModel;

import java.util.ArrayList;

public class RecentlyAddedNgoAdapter extends RecyclerView.Adapter<RecentlyAddedNgoAdapter.RecentlyAddedNgoHolder>{

    private Context mContext;
    private static final String TAG = "RecentlyAddedNgoAdapter";
    private ArrayList<RecentlyAddedNgoModel> recentlyAddedNgoModelArrayList;
    public static String SelectedUsername;
    private ItemClickListener itemClickListener;

    public RecentlyAddedNgoAdapter() {
    }

    public RecentlyAddedNgoAdapter(Context mContext, ArrayList<RecentlyAddedNgoModel> recentlyAddedNgoModelArrayList,ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.recentlyAddedNgoModelArrayList = recentlyAddedNgoModelArrayList;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public RecentlyAddedNgoAdapter.RecentlyAddedNgoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recently_added_ngo_list_item,
                parent, false);
        return new RecentlyAddedNgoAdapter.RecentlyAddedNgoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyAddedNgoAdapter.RecentlyAddedNgoHolder holder, int position) {

//        holder.ngoImage.setImageURI(Uri.parse(recentlyAddedNgoModelArrayList.get(position).getNgoImageUrl()));
        holder.ngoName.setText(recentlyAddedNgoModelArrayList.get(position).getNgoName());

        holder.ngoAddress.setText(recentlyAddedNgoModelArrayList.get(position).getNgoAddress());

        Glide.with(mContext).load(recentlyAddedNgoModelArrayList.get(position).getNgoImageUrl()).into(holder.ngoImage);

        holder.itemView.setOnClickListener(view ->{
            itemClickListener.onItemClick(recentlyAddedNgoModelArrayList.get(position));
        });

        Log.d(TAG, "onBindViewHolder: "+recentlyAddedNgoModelArrayList.get(position).getNgoImageUrl());

    }

    @Override
    public int getItemCount() {
        return recentlyAddedNgoModelArrayList.size();
    }

    public interface ItemClickListener{
        void onItemClick(RecentlyAddedNgoModel recentlyAddedNgoModel);
    }

    public class RecentlyAddedNgoHolder extends RecyclerView.ViewHolder {
        private ImageView ngoImage;
        private TextView ngoName,ngoAddress;

        public RecentlyAddedNgoHolder(@NonNull View itemView) {
            super(itemView);

            ngoImage=itemView.findViewById(R.id.ngoImage);
            ngoName=itemView.findViewById(R.id.ngoName);
            ngoAddress=itemView.findViewById(R.id.ngoAddress);


        }
    }
}
