package com.example.universaldonor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Marker userMarker;
    Boolean flag = TRUE;
    LinearLayout descriptionLinearLayout;
    TextView descriptionTextView;
    LatLng currentLatLng;
    HashMap<Marker, LatLng> latLngHashMap = new HashMap<Marker, LatLng>();
    Button navigateButton;
    int markerCount = 0;

    String getBloodAndQuantity(BloodBank bank) {
        String to_return = "";
            switch (MapsStart.bloodGroupChoice) {
                case 0:
                    to_return = bank.getBankName() + "\n A+ Units: " + String.valueOf(bank.getBloodStats().getaPlus());
                    break;
                case 1:
                    to_return = bank.getBankName() + "\nB+ Units: " + String.valueOf(bank.getBloodStats().getbPlus());
                    break;
                case 2:
                    to_return = bank.getBankName() + "\nAB+ Units: " + String.valueOf(bank.getBloodStats().getAbPlus());
                    break;
                case 3:
                    to_return = bank.getBankName() + "\nAB- Units: " + String.valueOf(bank.getBloodStats().getAbMinus());
                    break;
                case 4:
                    to_return = bank.getBankName() + "\nA- Units: " + String.valueOf(bank.getBloodStats().getaMinus());
                    break;
                case 5:
                    to_return = bank.getBankName() + "\nB- Units: " + String.valueOf(bank.getBloodStats().getbMinus());
                    break;
                case 6:

                    to_return = bank.getBankName() + "\nO+ Units: " + String.valueOf(bank.getBloodStats().getoPlus());
                    break;
                case 7:
                    to_return = bank.getBankName() + "\nO- Units: " + String.valueOf(bank.getBloodStats().getoMinus());
                    break;
                default:
                    Toast.makeText(this, "Some Error Occured", Toast.LENGTH_LONG).show();
                    return "No data found";
            }

        return to_return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        descriptionLinearLayout = findViewById(R.id.descriptionLinearLayout);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        navigateButton = findViewById(R.id.navigateButton);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(4, 4)).title("You are here").icon(BitmapDescriptorFactory.
                defaultMarker((BitmapDescriptorFactory.HUE_GREEN))));

        for(BloodBank bank: MapsStart.filteredBanks){
            String bloodAndQuantity = getBloodAndQuantity(bank);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(bank.getLatitude(), bank.getLongitude())).title(bloodAndQuantity));
            latLngHashMap.put(marker, new LatLng(bank.getLatitude(), bank.getLongitude()));


        }


        // Add a marker in Sydney and move the camera


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng latLng =new LatLng(location.getLatitude(), location.getLongitude());
                userMarker.setPosition(latLng);
                if (flag) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    flag = FALSE;
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        //--------------------Checking Permission------------------------//
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else{

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                descriptionTextView.setText(marker.getTitle());
                navigateButton.setVisibility(View.VISIBLE);
                LatLng latLng = latLngHashMap.get(marker);
                currentLatLng = latLng;
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" +
//                        String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude)));
//                startActivity(intent);
                return false;
            }
        });

    }


    public void navigateGoogleMaps(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + String.valueOf(currentLatLng.latitude) + ","
        + String.valueOf(currentLatLng.longitude)));
        startActivity(intent);
    }

    public void backToCurrent(View view){
        flag = TRUE;

    }

}
