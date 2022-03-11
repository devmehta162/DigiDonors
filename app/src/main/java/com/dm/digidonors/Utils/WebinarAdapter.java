package com.dm.digidonors.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dm.digidonors.R;
import com.dm.digidonors.Webinar.WebinarDetailsActivity;
import com.dm.digidonors.models.Webinar;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class WebinarAdapter extends FirestoreRecyclerAdapter<Webinar, WebinarAdapter.MyWebinarHolder> {

    private Context mContext;

    public WebinarAdapter(@NonNull FirestoreRecyclerOptions<Webinar> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyWebinarHolder holder, int position, @NonNull final Webinar model) {

        if (model.getTitle() != null && model.getDate() != null) {

            holder.webinarTitle.setText(model.getTitle());
            holder.webinarDate.setText(model.getDate());


            String descrtion = model.getDescription();
            if (descrtion.length() > 70) {
                String des = descrtion.substring(0, 65) + "...See More";
                holder.webinarDescription.setText(des);
            } else {
                holder.webinarDescription.setText(model.getDescription());
            }

            holder.webinarTimings.setText(model.getStarttime());
            holder.webinarDuration.setText(model.getEndtime());


            holder.joinNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebinarDetailsActivity.class);
                    intent.putExtra("title",model.getTitle());
                    intent.putExtra("description",model.getDescription());
                    intent.putExtra("time",model.getStarttime());
                    intent.putExtra("duration",model.getEndtime());
                    intent.putExtra("benefits",model.getBenefits());
                    intent.putExtra("date",model.getDate());
                    intent.putExtra("fee",model.getFee());
                    intent.putExtra("link",model.getLink());
                    intent.putExtra("userid",model.getUserId());
                    intent.putExtra("speaker",model.getTitle());
                    intent.putExtra("webinarId",model.getWebinarId());

                    mContext.startActivity(intent);
                }
            });

        }

    }

    @NonNull
    @Override
    public MyWebinarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_webinar_list_item,
                parent, false);
        return new MyWebinarHolder(v);
    }


    class MyWebinarHolder extends RecyclerView.ViewHolder {

        private TextView webinarTitle;
        private TextView webinarDate;
        private TextView webinarTimings;
        private TextView webinarDescription;
        private TextView webinarDuration;
        private Button joinNow;


        public MyWebinarHolder(View itemView) {
            super(itemView);
            webinarTitle = itemView.findViewById(R.id.webinar_title);
            webinarDate = itemView.findViewById(R.id.webinateDate);
            webinarTimings = itemView.findViewById(R.id.webinateTimings);
            webinarDescription = itemView.findViewById(R.id.webinar_description);
            webinarDuration = itemView.findViewById(R.id.webinarDuration);
            joinNow = itemView.findViewById(R.id.joinNow);

        }
    }
}
