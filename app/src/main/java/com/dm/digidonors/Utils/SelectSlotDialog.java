package com.dm.digidonors.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.dm.digidonors.Home.SelectSlotForDonationActivity;
import com.dm.digidonors.R;

public class SelectSlotDialog extends AppCompatDialogFragment {


    private TextView tomorrowDate, nextDate;
    private ImageView closeDialog;
    private TextView firstDateFirstTime, firstDateSecondTime, firstDateThirdTime, secondDateFirstTime, secondDateSecondTime, secondDateThirdTime;
    public DialogToConfirmListner dialogToConfirmListner;
    private static final String TAG = "SelectSlotDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        SelectSlotForDonationActivity selectSlotForDonationActivity =new SelectSlotForDonationActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.layout_dialog_select_donation_time_slot, null);

        tomorrowDate = view.findViewById(R.id.currentDate);
        nextDate = view.findViewById(R.id.nextDate);
        closeDialog = view.findViewById(R.id.closeDialog);


        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Bundle bundle = getArguments();

        String tomorrowDateString = bundle.getString("TomorrowDate", "");
        String nextDateString = bundle.getString("NextDate", "");


        tomorrowDate.setText(tomorrowDateString);
        nextDate.setText(nextDateString);

        firstDateFirstTime = view.findViewById(R.id.firstDateFirstTime);
        firstDateSecondTime = view.findViewById(R.id.firstDateSecondTime);
        firstDateThirdTime = view.findViewById(R.id.firstDateThirdTime);
        secondDateFirstTime = view.findViewById(R.id.secondDateFirstTime);
        secondDateSecondTime = view.findViewById(R.id.secondDateSecondTime);
        secondDateThirdTime = view.findViewById(R.id.secondDateThirdTime);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstDateFirstTime.setTextColor(getActivity().getResources().getColor(R.color.black));
                firstDateSecondTime.setTextColor(getActivity().getResources().getColor(R.color.black));
                firstDateThirdTime.setTextColor(getActivity().getResources().getColor(R.color.black));
                secondDateFirstTime.setTextColor(getActivity().getResources().getColor(R.color.black));
                secondDateSecondTime.setTextColor(getActivity().getResources().getColor(R.color.black));
                secondDateThirdTime.setTextColor(getActivity().getResources().getColor(R.color.black));

                switch (v.getId()) {
                    case R.id.firstDateFirstTime:

                        firstDateFirstTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
                        dialogToConfirmListner.sendtext(tomorrowDateString, firstDateFirstTime.getText().toString().trim());
                        getDialog().dismiss();

                        break;
                    case R.id.firstDateSecondTime:
                        firstDateSecondTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
                        dialogToConfirmListner.sendtext(tomorrowDateString, firstDateSecondTime.getText().toString().trim());
                        getDialog().dismiss();


                        break;
                    case R.id.firstDateThirdTime:
                        firstDateThirdTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
                        dialogToConfirmListner.sendtext(tomorrowDateString, firstDateThirdTime.getText().toString().trim());

                        getDialog().dismiss();
                        break;
                    case R.id.secondDateFirstTime:
                        secondDateFirstTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
                        dialogToConfirmListner.sendtext(nextDateString, secondDateFirstTime.getText().toString().trim());

                        getDialog().dismiss();
                        break;
                    case R.id.secondDateSecondTime:
                        secondDateSecondTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
                        dialogToConfirmListner.sendtext(nextDateString, secondDateSecondTime.getText().toString().trim());

                        getDialog().dismiss();
                        break;
                    case R.id.secondDateThirdTime:
                        secondDateThirdTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
                        dialogToConfirmListner.sendtext(nextDateString, secondDateThirdTime.getText().toString().trim());

                        getDialog().dismiss();
                        break;
                }

            }
        };

        firstDateFirstTime.setOnClickListener(listener);
        firstDateSecondTime.setOnClickListener(listener);
        firstDateThirdTime.setOnClickListener(listener);
        secondDateFirstTime.setOnClickListener(listener);
        secondDateSecondTime.setOnClickListener(listener);
        secondDateThirdTime.setOnClickListener(listener);


        Log.d(TAG, "onCreateDialog: " + SelectSlotForDonationActivity.date + SelectSlotForDonationActivity.time);

        if (tomorrowDateString.equals(SelectSlotForDonationActivity.date)) {

            if (SelectSlotForDonationActivity.time.equals(firstDateFirstTime.getText().toString().trim())) {
                firstDateFirstTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
            } else if (SelectSlotForDonationActivity.time.equals(firstDateSecondTime.getText().toString().trim())) {
                firstDateSecondTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));

            } else if (SelectSlotForDonationActivity.time.equals(firstDateThirdTime.getText().toString().trim())) {
                firstDateThirdTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));

            }
        }else if (nextDateString.equals(SelectSlotForDonationActivity.date)){
            if (SelectSlotForDonationActivity.time.equals(secondDateFirstTime.getText().toString().trim())) {
                secondDateFirstTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));
            } else if (SelectSlotForDonationActivity.time.equals(secondDateSecondTime.getText().toString().trim())) {
                secondDateSecondTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));

            } else if (SelectSlotForDonationActivity.time.equals(secondDateThirdTime.getText().toString().trim())) {
                secondDateThirdTime.setTextColor(getActivity().getResources().getColor(R.color.purple_200));

            }
        }


        builder.setCancelable(false);
        builder.setView(view);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dialogToConfirmListner = (DialogToConfirmListner) context;

        } catch (ClassCastException e) {
            Log.e("ddd", "onAttach: " + e.getMessage());
        }
    }


    public interface DialogToConfirmListner {
        void sendtext(String date, String time);

    }

}
