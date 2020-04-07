package com.example.muneasytravel;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * MapsActivity is used to tag the destination building of the user on the map, as well as their current location
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton currentLocationButton;
    private boolean currentLocationButtonIcon = true;
    private LatLng destination;
    private LatLng userLocation;
    private Building building;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        currentLocationButton = findViewById(R.id.currentLocationButton);
    }


    public void updateMap(Location location) {
        mMap.clear();
        // Add a marker in the user location
        userLocation = new LatLng(location.getLatitude(), location.getLongitude());

        // Add a marker in the destination building
        mMap.addMarker(new MarkerOptions().position(destination).title("Destination Building"));
    }

    // Is executed when currentLocationButton is pressed
    public void moveToCurrentLocation(View view) {

        if (currentLocationButtonIcon) {
            currentLocationButtonIcon = false;
            currentLocationButton.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_myplaces));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17f));
        } else {
            currentLocationButtonIcon = true;
            currentLocationButton.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_mylocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 17f));
        }
    }

    // Is executed when Start Navigation button is pressed
    public void startNavigation(View view) {
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + userLocation.latitude + "," + userLocation.longitude +
                        "&daddr=" + destination.latitude + "," + destination.longitude));
        startActivity(navigationIntent);
    }

    // Is executed when Floor Map button is pressed, moves user to FloorMapActivity
    public void openFloorMap(View view) {
        Intent intent = new Intent(getApplicationContext(), FloorMapActivity.class);
        intent.putExtra("numOfFloor", building.getNumOfFloors()); // pass the number of floors the building has
        intent.putExtra("buildingPrefix", building.getBuildingName());
        startActivity(intent); // move user to FloorMapActivity
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false); // since we are using our own currentLocationButton

        Intent intent = getIntent();
        building = (Building) intent.getSerializableExtra("room");

        destination = new LatLng(building.getLat(), building.getLon());
        // Move the camera to the destination building
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 17f));

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateMap(location);
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

        try {
            // ignore warnings in the following two lines as permission is already checked in the previous activity
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                updateMap(lastKnownLocation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}