package com.dm.digidonors.Webinar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.dm.digidonors.R;

public class DialogToConfirm extends AppCompatDialogFragment {

    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private Button register;
    public DialogToConfirmListner dialogToConfirmListner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view =inflater.inflate(R.layout.layout_dialog_confirming_details_of_webinar,null);

        name = view.findViewById(R.id.enterName);
        email =view.findViewById(R.id.enterEmail);
        phoneNumber =view.findViewById(R.id.enterPhoneNumber);
        register =view.findViewById(R.id.registerFinal);




        builder.setView(view).setTitle("Confirmation");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredName = name.getText().toString().trim();
                String enteredEmail = email.getText().toString().trim();
                String enteredPhone = phoneNumber.getText().toString().trim();

                if (!enteredName.isEmpty()&&!enteredEmail.isEmpty()&&!enteredPhone.isEmpty()){
                    dialogToConfirmListner.sendtext(enteredName, enteredEmail, enteredPhone);
                    dismiss();
                }else {
                    Toast.makeText(getActivity(), "Please enter the details", Toast.LENGTH_SHORT).show();
                    builder.setCancelable(false);

                }


            }
        });
//        builder.setView(view).setTitle("Confirmation")
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.dismiss();
//                    }
//                }).setPositiveButton("register", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//                String enteredName =name.getText().toString().trim();
//                String enteredEmail =email.getText().toString().trim();
//                String enteredPhone =phoneNumber.getText().toString().trim();
//                builder.setCancelable(false);
//
//                dialogToConfirmListner.sendtext(enteredName,enteredEmail,enteredPhone);
//            }
//        });

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dialogToConfirmListner =(DialogToConfirmListner) context;

        }catch (ClassCastException e){
            Log.e("ddd", "onAttach: "+e.getMessage() );
        }
    }

    public interface DialogToConfirmListner {
        void sendtext(String name ,String email ,String phone);
    }

}
