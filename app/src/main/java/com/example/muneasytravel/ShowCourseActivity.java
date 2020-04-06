package com.example.muneasytravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ShowCourseActivity is for registered users to view the courses they have stored in the database
 * Tapping one any of the added courses will direct users to the MapsActivity with the appropriate building marked on the map
 * ShowCourseActivity not accessible by guest users
 */
public class ShowCourseActivity extends AppCompatActivity {

    private ArrayList<String> courseName = new ArrayList<>();
    private ArrayList<String> roomNo = new ArrayList<>();
    private ArrayList<Building> buildings;
    private boolean locationRequestGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course);

        getSupportActionBar().setTitle("Courses");
        Intent intent = getIntent();
        buildings = (ArrayList<Building>) intent.getSerializableExtra("buildingList"); // collect the building list passed from MainActivity/AddCourseActivity
        ListView courseListView = findViewById(R.id.courseListView);
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // If user taps any of the added courses, the following are executed
                ValidateUserInputInterface validateRoomNo = new ValidateRoomNo(roomNo.get(position), buildings); // Procedure to validate room no. is delegated (application of Dependency Inversion Principle (DIP))
                if (validateRoomNo.validate()) { // room no. is validated, so add course name and room number to the database
                    Integer i = ((ValidateRoomNo) validateRoomNo).getFoundBuildingIndex();
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("room", buildings.get(i));
                    requestLocationPermission(); // ask user for location access permission if user never granted the permission
                    if (locationRequestGranted) { // location access permission granted, so move user to MapsActivity
                        startActivity(intent); // moving user to MapsActivity
                    }
                }
            }
        });

        // Setting up the ArrayAdapter that courseListView is using
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, courseName){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text1.setText(courseName.get(position));
                text2.setText(roomNo.get(position));
                return view;
            }
        };

        // Application of Observer pattern
        Observer observer = new UpdateCourseObserver(courseListView, adapter, courseName, roomNo);
        observer.update();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // permission granted so request for location updates
                    locationRequestGranted = true;
                }
            }
        }
    }

    public void requestLocationPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // did not get permission so request for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                // user gave permission already, so request location updates
                locationRequestGranted = true;
            }
        }
    }
}
