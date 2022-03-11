package com.dm.digidonors.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dm.digidonors.Home.AddNewAddress;
import com.dm.digidonors.R;
import com.dm.digidonors.models.AddNewAddressModel;
import com.dm.digidonors.models.UserAddressModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.MessageFormat;
import java.util.ArrayList;


public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.UserAddressHolder> {

    private Context mContext;
    private int checkedPosition = 0;
    private static final String TAG = "UserAddressAdapter";
    private ArrayList<UserAddressModel> userAddressModelArrayList;
    public static String SelectedUsername;

    public UserAddressAdapter(Context mContext, ArrayList<UserAddressModel> userAddressModelArrayList) {
        this.mContext = mContext;
        this.userAddressModelArrayList = userAddressModelArrayList;
    }
    public UserAddressAdapter(Context mContext, String SelectedUsername) {
        this.mContext = mContext;
        this.SelectedUsername = SelectedUsername;
        Log.d(TAG, "UserAddressAdapter: SelectedUsername"+SelectedUsername);
    }



    @NonNull
    @Override
    public UserAddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_address_list_item,
                parent, false);
                for (int i=0;i<userAddressModelArrayList.size();i++){


            if (SelectedUsername!=null&&userAddressModelArrayList.get(i).getName()!=null) {
                Log.d(TAG, "onBindViewHolder: first if ");
                if (SelectedUsername.equals(userAddressModelArrayList.get(i).getName())) {
                    Log.d(TAG, "onBindViewHolder: second if ");
                    checkedPosition = i;
                    Log.d(TAG, "onBindViewHolder: checkedPosition found "+checkedPosition);
                }
            }
        }

        return new UserAddressAdapter.UserAddressHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAddressHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.name.setText(userAddressModelArrayList.get(position).getName());

        Log.d(TAG, "onBindViewHolder: SelectedUsername "+SelectedUsername);
//        for (int i=0;i<userAddressModelArrayList.size();i++){
//
//
//            if (SelectedUsername!=null&&userAddressModelArrayList.get(i).getName()!=null) {
//                Log.d(TAG, "onBindViewHolder: first if ");
//                if (SelectedUsername.equals(userAddressModelArrayList.get(i).getName())) {
//                    Log.d(TAG, "onBindViewHolder: second if ");
//                    checkedPosition = i;
//                    Log.d(TAG, "onBindViewHolder: checkedPosition found "+checkedPosition);
//                }
//            }
//        }

        holder.mobileNo.setText(userAddressModelArrayList.get(position).getPhone());
        holder.pincode.setText(userAddressModelArrayList.get(position).getPincode());
        holder.address.setText(userAddressModelArrayList.get(position).getAddress());
        holder.city.setText(userAddressModelArrayList.get(position).getCity());
        holder.state.setText(MessageFormat.format(",",userAddressModelArrayList.get(position).getState()));


        Log.d(TAG, "onBindViewHolder: "+checkedPosition);
        if (checkedPosition == -1) {

            holder.check.setVisibility(View.INVISIBLE);

        } else {
            if (checkedPosition == position) {

                holder.check.setVisibility(View.VISIBLE);
            } else {

                holder.check.setVisibility(View.INVISIBLE);
            }
        }


        //holder.bind(categoryModelList.get(position));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkedPosition!=position) {
                    checkedPosition = position;
                    notifyDataSetChanged();
                    notifyItemChanged(checkedPosition);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userAddressModelArrayList.size();
    }

    public class UserAddressHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView mobileNo;
        public TextView address;
        public TextView pincode;
        public TextView city;
        public TextView state;
        private ImageView check;
        private CardView cardView;
        public UserAddressHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mobileNo = itemView.findViewById(R.id.mobileNo);
            pincode = itemView.findViewById(R.id.pincode);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            state= itemView.findViewById(R.id.state);
            check= itemView.findViewById(R.id.check);
            cardView =itemView.findViewById(R.id.NewAddresscardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check.setVisibility(View.INVISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }
    public UserAddressModel getSelected() {
        if (checkedPosition != -1) {
            return userAddressModelArrayList.get(checkedPosition);

        }
        return null;
    }
}
