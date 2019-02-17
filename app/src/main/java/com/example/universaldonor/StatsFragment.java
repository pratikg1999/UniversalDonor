package com.example.universaldonor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private ListView lv;
    private FirebaseDatabase database;
    private DatabaseReference usersDatabase;
    DatabaseReference bloodBanksDatabase;
    DatabaseReference donationsDatabase;
    DatabaseReference aquiresDatabase;
    DatabaseReference bloodStatsReference;
    FirebaseAuth mAuth;
    String bankId;
    BloodStats bloodStats;
    ArrayList<String> stats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        usersDatabase = database.getReference("users");
        bloodBanksDatabase = database.getReference("bloodBanks");
        donationsDatabase = database.getReference("donations");
        aquiresDatabase = database.getReference("aquires");
        bankId = mAuth.getCurrentUser().getUid();
        bloodStatsReference = database.getReference("bloodBanks").child(bankId).child("bloodStats");
        bloodStats = new BloodStats();
        stats = new ArrayList<String>();
        updateStats(bloodStats, stats);

        lv = (ListView) getActivity().findViewById(R.id.listView);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                stats );

        lv.setAdapter(arrayAdapter);
        bloodStatsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodStats = dataSnapshot.getValue(BloodStats.class);
                updateStats(bloodStats, stats);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /*bloodBanksDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodStats = dataSnapshot.child(bankId).child("bloodStats").getValue(BloodStats.class);
                updateStats(bloodStats, stats);
                Log.d("datasnapshot captured", dataSnapshot.toString());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*stats.add("A+ " + bloodStats.getaPlus());
        stats.add("A- " + bloodStats.getaMinus());
        stats.add("B+ " + bloodStats.getbPlus());
        stats.add("B- " + bloodStats.getbMinus());
        stats.add("O+ " + bloodStats.getoPlus());
        stats.add("O- " + bloodStats.getoMinus());
        stats.add("AB+ " + bloodStats.getAbPlus());
        stats.add("AB- " + bloodStats.getAbMinus());*/






        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
    }

    private void updateStats(BloodStats bloodStats, List<String> stats) {
        stats.clear();
        stats.add(BloodType.APLUS + " " + bloodStats.getaPlus());
        stats.add(BloodType.AMINUS + " " + bloodStats.getaMinus());
        stats.add(BloodType.BPLUS + " " + bloodStats.getbPlus());
        stats.add(BloodType.BMINUS + " " + bloodStats.getbMinus());
        stats.add(BloodType.ABPLUS+ " " + bloodStats.getAbPlus());
        stats.add(BloodType.ABMINUS+ " " + bloodStats.getAbMinus());
        stats.add(BloodType.OPLUS +  " " + bloodStats.getoPlus());
        stats.add(BloodType.OMINUS + " " + bloodStats.getoMinus());
    }
}
