package com.example.universaldonor;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MapsStart extends AppCompatActivity {

    ArrayList<AddressBloodBank> addressBloodBanks = new ArrayList<AddressBloodBank>();
    EditText regionEditText;
    Spinner spinner;
    ArrayList<String> spinnerChoices = new ArrayList<String>();
    ArrayList<AddressBloodBank> filteredBanks = new ArrayList<AddressBloodBank>();
    int choice;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_start);

        regionEditText = findViewById(R.id.regionEditText);
        spinner = findViewById(R.id.spinner);
        spinnerChoices.add("City");
        spinnerChoices.add("States");
        spinnerChoices.add("Country");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerChoices);
        spinner.setAdapter(arrayAdapter);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String toAdd = "City";
                switch (i){
                    case 0:
                        toAdd = "City";
                        choice = 0;
                        break;
                    case 1:
                        toAdd = "State";
                        choice = 1;
                        break;
                    case 2:
                        toAdd = "State";
                        choice = 2;
                        break;
                }
                regionEditText.setHint("Enter your current " + toAdd);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                regionEditText.setHint("Enter your current " + "City");
            }
        });



        AddressBloodBank bank1 = new AddressBloodBank("Raipur", "Chattisgarh", "India", 21.1285, 81.7662);
        AddressBloodBank bank2 = new AddressBloodBank("Raipur", "Chattisgarh", "India", 21.2497, 81.6050);
        AddressBloodBank bank3 = new AddressBloodBank("Raipur", "Chattisgarh", "India", 21.1067, 81.7590);
        addressBloodBanks.add(bank1);
        addressBloodBanks.add(bank2);
        addressBloodBanks.add(bank3);




    }

    public void startMaps(View view){

        for(AddressBloodBank bank: addressBloodBanks){
            if (choice == 0){
                Log.d("bank", bank.city);
                Log.d("entered", regionEditText.getText().toString());
                if (choice == 0 && (bank.city).equals(regionEditText.getText().toString())){
                    filteredBanks.add(bank);
                }
                if (choice == 1 && bank.state.equals(regionEditText.getText().toString())){
                    filteredBanks.add(bank);
                }
                if (choice == 2 && bank.country.equals(regionEditText.getText().toString())){
                    filteredBanks.add(bank);
                }
            }
        }

        Intent intent = new Intent(this, MapsStart.class);

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=21.1938,81.3509"));
//        startActivity(intent);

    }
}
