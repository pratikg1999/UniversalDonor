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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MapsStart extends AppCompatActivity {

    ArrayList<BloodBank> addressBloodBanks = new ArrayList<BloodBank>();
    EditText regionEditText;
    Spinner spinner;
    ArrayList<String> spinnerChoices = new ArrayList<String>();
    ArrayList<String> bloodGroupArrayList = new ArrayList<String>();
    static ArrayList<BloodBank> filteredBanks = new ArrayList<BloodBank>();
    Spinner bloodGroupSpinner;
    ListView listView;
    int choice;
    static int bloodGroupChoice = -1;

    void filterBanks() {
        filteredBanks = new ArrayList<BloodBank>();
        for (BloodBank bank : addressBloodBanks) {
            Log.d("entered", regionEditText.getText().toString());
            if (choice == 0 && (bank.getCity()).equals(regionEditText.getText( ).toString())) {
                filteredBanks.add(bank);
                Log.d("choice", String.valueOf(choice));
            }
            if (choice == 1 && bank.getState().equals(regionEditText.getText().toString())) {
                filteredBanks.add(bank);
            }
            if (choice == 2 && bank.getAddress().equals(regionEditText.getText().toString())) {
                filteredBanks.add(bank);
            }
        }
    }

    void setOnClickListener(Spinner newSpinner){
        newSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloodGroupChoice = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                bloodGroupChoice = 0;
            }
        });
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_start);

        regionEditText = findViewById(R.id.regionEditText);
        bloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
        listView = findViewById(R.id.bannksShowListView);

        spinner = findViewById(R.id.spinner);
        spinnerChoices.add("City");
        spinnerChoices.add("States");
        spinnerChoices.add("Country");

        bloodGroupArrayList.add("A+");
        bloodGroupArrayList.add("B+");
        bloodGroupArrayList.add("AB+");
        bloodGroupArrayList.add("AB-");
        bloodGroupArrayList.add("A-");
        bloodGroupArrayList.add("B-");
        bloodGroupArrayList.add("O+");
        bloodGroupArrayList.add("O-");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerChoices);
        spinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> bloodGroupArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bloodGroupArrayList);
        bloodGroupSpinner.setAdapter(bloodGroupArrayAdapter);

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

        setOnClickListener(bloodGroupSpinner);

//        BloodBank bank1 = new BloodBank("Jeevan IIIT", new BloodStats(2,2,2,2,2,2,2,2),
//                21.1285, 81.7662, "Raipur", "Chattisgarh");
//        BloodBank bank2 = new BloodBank("NIT Raipur", new BloodStats(2,2,2,2,2,2,2,2),
//                21.2497, 81.6050, "Raipur", "Chattisgarh");
//        BloodBank bank3 = new BloodBank("HNLU", new BloodStats(9,2,2,2,2,2,2,2),
//                21.1067, 81.7590, "Raipur", "Chattisgarh");
//        addressBloodBanks.add(bank1);
//        addressBloodBanks.add(bank2);
//        addressBloodBanks.add(bank3);




    }

    public void startMaps(View view){

        if(regionEditText.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the region", Toast.LENGTH_LONG).show();
            return;
        }

        filterBanks();

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=21.1938,81.3509"));
//        startActivity(intent);

    }

    public void displayBanksList(View view){

        if(regionEditText.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the region", Toast.LENGTH_LONG).show();
            return;
        }
        filterBanks();

        ArrayList<String> banksShowArrayList = new ArrayList<String>();

        for(BloodBank bank: filteredBanks){
            String toShow = "";
            toShow += bank.getBankName() + " ";
            String blood = "";

            switch (bloodGroupChoice){
                case 0:
                    blood = "A+ Units: " + String.valueOf(bank.getBloodStats().getaPlus());
                    break;
                case 1:
                    blood = "B+ Units: " + String.valueOf(bank.getBloodStats().getbPlus());
                    break;
                case 2:
                    blood = "AB+ Units: " + String.valueOf(bank.getBloodStats().getAbPlus());
                    break;
                case 3:
                    blood = "AB- Units: " + String.valueOf(bank.getBloodStats().getAbMinus());
                    break;
                case 4:
                    blood = "A- Units: " + String.valueOf(bank.getBloodStats().getaMinus());
                    break;
                case 5:
                    blood = "B- Units: " + String.valueOf(bank.getBloodStats().getbMinus());
                    break;
                case 6:

                    blood = "O+ Units: " + String.valueOf(bank.getBloodStats().getoPlus());
                    break;
                case 7:
                    blood = "O- Units: " + String.valueOf(bank.getBloodStats().getoMinus());
                    break;
                default:
                    Toast.makeText(this,"Some Error Occured", Toast.LENGTH_LONG).show();
                    return;
            }

            toShow += blood;
            Log.i("toshow", toShow);
            banksShowArrayList.add(toShow);



        }

        ArrayAdapter<String> bloodShowAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, banksShowArrayList);
        listView.setAdapter(bloodShowAdapter);

    }




}
