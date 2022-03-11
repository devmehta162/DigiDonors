package com.dm.digidonors.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.digidonors.R;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String,List<String>> donationCollection;
    private List<String> donationCategories;

    TextView noOfGroupDonation;



    public MyExpandableListAdapter(Context context, List<String> donationCategories, Map<String, List<String>> donationCollection) {
        this.context = context;
        this.donationCategories = donationCategories;
        this.donationCollection = donationCollection;
    }

    @Override
    public int getGroupCount() {
        return donationCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return donationCollection.get(donationCategories.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return donationCategories.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return donationCollection.get(donationCategories.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String donationCategoryName =donationCategories.get(groupPosition);
        if (convertView==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.group_item_expandable_list_view,null);
        }

        TextView item =convertView.findViewById(R.id.donationCategoriesName);

        noOfGroupDonation =convertView.findViewById(R.id.NoOfGroupDonation);

        ImageView imgArrow =convertView.findViewById(R.id.imgDown);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(donationCategoryName);




        if (isExpanded) {
            imgArrow.setImageResource(R.drawable.ic_up_expandableview);
        } else {
            imgArrow.setImageResource(R.drawable.ic_down_expandableview);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String model = donationCollection.get(donationCategories.get(groupPosition)).get(childPosition);
        if (convertView==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.child_tem_expandable_list_view,null);
        }

        TextView item =convertView.findViewById(R.id.donationSubCategoriesName);


        TextView addChildItemToDonation =convertView.findViewById(R.id.addChildItemToDonation);


        item.setText(model);

//
//        addChildItemToDonation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });



        return convertView;    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
