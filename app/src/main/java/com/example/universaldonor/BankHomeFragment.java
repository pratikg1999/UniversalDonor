package com.example.universaldonor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    String userId;
    long numUnits;
    private DatabaseReference usersDatabase;
    DatabaseReference bloodBanksDatabase;
    DatabaseReference donationsDatabase;
    DatabaseReference aquiresDatabase;
    private FirebaseDatabase database;
    FirebaseAuth mAuth;
    String bankId;
    String bG;
    Date date;



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

        newDonation.setOnClickListener(this);

        bankId = mAuth.getCurrentUser().getUid();

        date = new Date();




        bloodBanksDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bG = dataSnapshot.child(userId).child("bloodGroup").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Donations donations = new Donations(userId,bankId,bG,numUnits,date);

        donationsDatabase.push().setValue(donations);





    }

    @Override
    public void onClick(View v) {

        if(v == newDonation){

            openDialog();
        }

    }

    public void openDialog(){

        Dialog dialog = new Dialog();
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void applyTexts(String user, long units) {

        userId = user;
        numUnits = units;

    }
}
