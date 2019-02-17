package com.example.universaldonor;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class Dialog extends AppCompatDialogFragment {

    private EditText userId;
    private EditText numUnits;
    private dialogListner listner;


    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        userId = view.findViewById(R.id.userId);
        numUnits = view.findViewById(R.id.numUnits);
        builder.setView(view)
                .setTitle("New Donation")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String user = userId.getText().toString();
                        long units = Long.parseLong(numUnits.getText().toString());
                        listner.applyTexts(user,units);

                    }
                });

        return builder.create();
    }

    public interface dialogListner{

        void applyTexts(String user, long units);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        try {
            listner = (dialogListner) context;
        }
        catch (ClassCastException e){

            throw new ClassCastException (context.toString());

        }
    }
}
