package com.example.universaldonor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class BankHomeFragment extends Fragment implements View.OnClickListener,Dialog.dialogListner{

    Button newDonation;
    String userId = "2c9fYNm6crUJtu7TPgQ6GUwYj4m1";
    long numUnits;
    private DatabaseReference usersDatabase;
    DatabaseReference bloodBanksDatabase;
    DatabaseReference donationsDatabase;
    DatabaseReference aquiresDatabase;
    private FirebaseDatabase database;
    ArrayList<User> users;
    ArrayList<String> usernames;
    FirebaseAuth mAuth;
    String bankId;
    String bG;
    Date date;
    ArrayAdapter<String> adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bankhome,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newDonation = (Button)getActivity().findViewById(R.id.newDonation);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(getContext());
        usersDatabase = database.getReference("users");
        bloodBanksDatabase = database.getReference("bloodBanks");
        donationsDatabase = database.getReference("donations");
        aquiresDatabase = database.getReference("aquires");
        users = new ArrayList<>();
        usernames = new ArrayList<>();

        newDonation.setOnClickListener(this);

        bankId = mAuth.getCurrentUser().getUid();

        date = new Date();




        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bG = dataSnapshot.child(userId).child("bloodGroup").getValue(String.class);
                users.clear();
                usernames.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    User newUser = userSnapshot.getValue(User.class);
                    users.add(newUser);
                    usernames.add(newUser.getUserName());

                }
                adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, usernames);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }

    @Override
    public void onClick(View v) {

        if(v == newDonation){

            openDialog();
        }

    }

    public void openDialog(){

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(view);
        dialogBuilder.setTitle("Donate blood");
        //final EditText userIdET = view.findViewById(R.id.userId);
        final EditText numUnitsET = view.findViewById(R.id.numUnits);
        final Spinner usernamesSpinner = view.findViewById(R.id.usernamesSpinner);
        usernamesSpinner.setAdapter(adapter);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int index = usernamesSpinner.getSelectedItemPosition();
                userId = users.get(index).getUserId();
                //userId = userIdET.getText().toString();
                numUnits = Long.parseLong(numUnitsET.getText().toString());
                Donations donations = new Donations(userId,bankId,bG,numUnits,date);
                donationsDatabase.push().setValue(donations);
                Toast.makeText(getContext(), "Donation created", Toast.LENGTH_SHORT).show();
            }
        });
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();



//        Dialog dialog = new Dialog();
//        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
//        Donations donations = new Donations(userId,bankId,bG,numUnits,date);

//        donationsDatabase.push().setValue(donations);
    }

    @Override
    public void applyTexts(String user, long units) {

        userId = user;
        numUnits = units;

    }
}
