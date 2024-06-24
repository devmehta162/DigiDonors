package com.dm.digidonors.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dm.digidonors.R;
import com.dm.digidonors.Utils.MyExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoardActivity extends AppCompatActivity {

    private List<String> donationCategories;
    private List<String> donationSubCategories;

    private Map<String ,List<String>> donationCollection;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private TextView toolbarHeadingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        expandableListView=findViewById(R.id.elvDonation);
        toolbarHeadingText=findViewById(R.id.toolbarHeadingText);


        toolbarHeadingText.setText("Dashboard");
        createDonationCategories();

        createCollection();



        expandableListAdapter=new MyExpandableListAdapter(DashBoardActivity.this,donationCategories,donationCollection);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition=-1;
            @Override
            public void onGroupExpand(int i) {

                if (lastExpandedPosition!= -1 && i!=lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);

                }
                lastExpandedPosition=i;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String selected=expandableListAdapter.getChild(groupPosition,childPosition).toString();

            //    Toast.makeText(DashBoardActivity.this, selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });





    }

    private void createCollection() {

        String[] clothesModels ={"tshirt","shirt","jeans"};
        String[] booksModels={"ncert","jee"};
        String[] foodModels={"ncert","jee"};

        donationCollection=new HashMap<String ,List<String>>();


        for (String group:donationCategories){
            if (group.equals("Clothes")){
                loadChild(clothesModels);
            }else if (group.equals("Books"))
                loadChild(booksModels);
            else
                loadChild(foodModels);
            donationCollection.put(group,donationSubCategories);





        }


    }

    private void loadChild(String[] donationModels) {

        donationSubCategories = new ArrayList<>();

        for (String donation :donationModels){

            donationSubCategories.add(donation);
        }

    }

    private void createDonationCategories() {
        donationCategories=new ArrayList<>();

        donationCategories.add("Clothes");
        donationCategories.add("Books");
        donationCategories.add("Food");

    }
}