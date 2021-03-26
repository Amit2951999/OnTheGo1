package com.csgang.android.onthego;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    public static double latitude = 0;
    public static double longitude = 0;
    GPSTracker gps;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        gps = new GPSTracker(MyLocationActivity.this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        if(latitude == 0 || longitude == 0) {
            Toast.makeText(this, "Location not found yet.", Toast.LENGTH_SHORT).show();
            finish();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myLoc = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myLoc).title("Your current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
