package com.dm.digidonors.MyDonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dm.digidonors.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MyDonationAdapter extends FirestoreRecyclerAdapter<MyDonationModel, MyDonationAdapter.MyDonationHolder> {

    private Context context;

    public MyDonationAdapter(@NonNull FirestoreRecyclerOptions<MyDonationModel> options, Context context) {
        super(options);
        this.context = context;
    }

//    public MyDonationAdapter(@NonNull FirestoreRecyclerOptions<MyDonationModel> options) {
//        super(options);
//
//    }

    @Override
    protected void onBindViewHolder(@NonNull MyDonationAdapter.MyDonationHolder holder, int position, @NonNull MyDonationModel model) {

        holder.listaddress.setText(model.getAddress());
        holder.listNo.setText(model.getNo());
        holder.listDateForPickup.setText(model.getDateforpickup()+"("+model.getTimeforpickup()+")");
//        holder.listparticipate.setText(model.getParticipate());
        holder.listDescription.setText(model.getDescription());
        holder.listCategory.setText(model.getCategory());
        holder.listPostedDate.setText(model.getTime().substring(0,10));
        holder.listStatus.setText(model.getStatus());
        holder.listOtp.setText(model.getOtp());
//        Toast.makeText(context, model.getDescription()+"  "+model.getStatus(), Toast.LENGTH_SHORT).show();

        switch (model.getCategory()) {
            case "cloth":
                holder.listCategoryImage.setImageResource(R.drawable.clothes);
                break;
            case "books":
                holder.listCategoryImage.setImageResource(R.drawable.books1);

                break;
            case "stationary":
                holder.listCategoryImage.setImageResource(R.drawable.stationary);

                break;
            case "shoes":
                holder.listCategoryImage.setImageResource(R.drawable.shoes2);

                break;
            case "blood":
                holder.listCategoryImage.setImageResource(R.drawable.blood);

                break;
            case "food":
                holder.listCategoryImage.setImageResource(R.drawable.food2);

                break;
        }
    }

    @NonNull
    @Override
    public MyDonationAdapter.MyDonationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_donation_list_row,
                parent, false);
        return new MyDonationHolder(v);     }

    public class MyDonationHolder extends RecyclerView.ViewHolder {
        private TextView listaddress;
        private TextView listNo;
        private TextView listDateForPickup;
//        private TextView listTimeForPickup;
        private TextView listparticipate;
        private TextView listCategory;
        private TextView listDescription;
        private TextView listPostedDate;
        private ImageView listCategoryImage;
        private TextView listStatus;
        private TextView listOtp;

        public MyDonationHolder(@NonNull View itemView) {
            super(itemView);

            listaddress=itemView.findViewById(R.id.textView3);
            listNo=itemView.findViewById(R.id.textView6);
            listDateForPickup=itemView.findViewById(R.id.textView4);
            listOtp=itemView.findViewById(R.id.textViewOtp);
//            listparticipate=itemView.findViewById(R.id.textView7);
            listCategory=itemView.findViewById(R.id.textViewCategory);
            listDescription=itemView.findViewById(R.id.textViewDescription);
            listPostedDate=itemView.findViewById(R.id.postedDate);
            listCategoryImage=itemView.findViewById(R.id.categoryImage);
            listStatus=itemView.findViewById(R.id.status);
        }
    }
}
