package com.example.universaldonor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingDeque;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, UserSignUp.OnFragmentInteractionListener, BloodBankSignUp.OnFragmentInteractionListener {

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Button signUpAsUser;
    private Button signUpAsBloodBank;
    FragmentManager fragmentManager;
    LocationManager locationManager;
    LocationListener locationListener;
    static double latitude;
    static double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        signup = (Button) findViewById(R.id.signup);
        signUpAsUser = findViewById(R.id.signupAsUserButton);
        signUpAsBloodBank = findViewById(R.id.signupAsBloodBankButton);
//        email = (EditText) findViewById(R.id.email);
//        password = (EditText) findViewById(R.id.password);
//        login = (TextView) findViewById(R.id.login);
        database = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
//        signup.setOnClickListener(this);
//        login.setOnClickListener(this);
        signUpAsBloodBank.setOnClickListener(this);
        signUpAsUser.setOnClickListener(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        fragmentManager = getSupportFragmentManager();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupAsUserButton:
                UserSignUp userSignUp = UserSignUp.newInstance();
                fragmentManager.beginTransaction().replace(R.id.signUpArea, userSignUp).commit();
                break;
            case R.id.signupAsBloodBankButton:
                BloodBankSignUp bloodBankSignUp = BloodBankSignUp.newInstance();
                fragmentManager.beginTransaction().replace(R.id.signUpArea, bloodBankSignUp).commit();
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                return;
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void getLatLng() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        Log.d("latitude", latitude+"");
        Log.d("longitude", longitude+"");

    }
}