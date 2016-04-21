package com.example.mark.mapdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private LocationManager locationmanager_;
    private GoogleMap mMap;
    private EditText latE, longE;
    private Button submitLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFrag ent and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latE = (EditText) findViewById(R.id.LatField);
        longE = (EditText) findViewById(R.id.LongField);
        submitLoc = (Button) findViewById(R.id.subButton);

        submitLoc.setOnClickListener(this);
    }



    /*
    1. Display Map
    2. Input Location
    3. Get user location
    4. get distance and toast it
    5. display 2 most recent locations inputted

     */


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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
//https://developers.google.com/maps/documentation/android-api/location#the_my_location_layer

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //LatLng Baltimore = new LatLng(39, -76);



        //mMap.addMarker(new MarkerOptions().position(Baltimore).title("Marker in Baltimore"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Baltimore));
    }

    public void toastIt(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void compareLoc(){
        //toastIt("in compare location function...");
        if(latE.getText().toString() != "Latitude" && longE.getText().toString() != "Longitude")
        {
            double latDouble = new Double(latE.getText().toString()).doubleValue();
            double longDouble = new Double(longE.getText().toString()).doubleValue();
            LatLng input = new LatLng(latDouble, longDouble);
            //toastIt("Lat = " + latDouble + " Long = " + longDouble);
            mMap.addMarker(new MarkerOptions().position(input).title("Marker at inputted location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(input));

            Location inputLoc = new Location("");
            inputLoc.setLatitude(latDouble);
            inputLoc.setLongitude(longDouble);
            Location userLoc = new Location("");
            userLoc.setLatitude(userLoc.getLatitude());
            userLoc.setLongitude(userLoc.getLongitude());

            double distance = userLoc.distanceTo(inputLoc);
            toastIt("You are "+ distance +" meters from your inputted location.");

        }else
            toastIt("input both lat and long values please");

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.subButton:
                compareLoc();
                break;
        }

    }
}
