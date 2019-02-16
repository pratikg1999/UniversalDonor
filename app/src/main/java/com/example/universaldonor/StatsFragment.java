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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private ListView lv;
    private FirebaseDatabase database;
    private DatabaseReference usersDatabase;
    DatabaseReference bloodBanksDatabase;
    DatabaseReference donationsDatabase;
    DatabaseReference aquiresDatabase;
    FirebaseAuth mAuth;
    String bankId;
    DataSnapshot dataSnapshot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usersDatabase = database.getReference("users");
        bloodBanksDatabase = database.getReference("bloodBanks");
        donationsDatabase = database.getReference("donations");
        aquiresDatabase = database.getReference("aquires");
        mAuth = FirebaseAuth.getInstance();


        lv = (ListView) getActivity().findViewById(R.id.listView);

        bankId = mAuth.getCurrentUser().getUid();

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> stats = new ArrayList<String>();
        DataSnapshot statsSnapshot = dataSnapshot.child("contacts");
        stats.add(" A+ " + bloodBanksDatabase.child(bankId).getKey());

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                stats );

        lv.setAdapter(arrayAdapter);
    }
}