package com.example.universaldonor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BankHistory extends Fragment {

    ListView bankHistoryListView;
    DatabaseReference donationsDatabaseReference;
    FirebaseAuth mAuth;
    DatabaseReference usersDatabaseReference;
    ArrayList<Donations> donationsArrayList = new ArrayList<Donations>();
    ArrayList<String> displayArrayList = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        donationsDatabaseReference = FirebaseDatabase.getInstance().getReference("donations");
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_bankhistory,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bankHistoryListView = view.findViewById(R.id.bankHistoryListView);
        String toAdd = "";
        final String username;
        donationsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Donations to_consider = dataSnapshot.getValue(Donations.class);
                String s = "";
                if (to_consider.getBloodBankId().equals(mAuth.getCurrentUser().getUid())) {
                    donationsArrayList.add(to_consider);
                    s += "donorID: " + to_consider.getUserId() + "\nDonated On: "
                            + to_consider.getDateDonated() +"\nAmount: " +
                            to_consider.getAmountOfBlood() + "\nBlood Type: "
                            + to_consider.getBloodType();
                    displayArrayList.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, displayArrayList);
        bankHistoryListView.setAdapter(arrayAdapter);
    }
}