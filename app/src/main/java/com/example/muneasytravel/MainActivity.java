package com.example.muneasytravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {
    private ArrayList<Building> buildings = new ArrayList<>();
    private EditText roomEditText;
    private ImageView munImageView;
    private FirebaseAuth mAuth;
    private ConstraintLayout mainLayout;
    private RelativeLayout openingRelativeLayout;
    private boolean locationRequestGranted = false;
    private Intent intent = null;

    public void showBuilding(View view) {
        intent = null;
        String room = "";
        room = roomEditText.getText().toString().toUpperCase();
        if (!room.equals("")) {
            String[] buildingPrefix = room.split("(?<=\\D)(?=\\d)", 2); // splitting the building prefix and the rest of the room number
            // buildingPrefix[0] will have the building prefix.
            for (int i = 0; i < buildings.size(); i++) {
                if (buildingPrefix[0].trim().equals(buildings.get(i).getBuildingName())) {
                    intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("room", buildings.get(i));
//                    startActivity(intent);
                    break;
                }
            }
            if (intent != null) {
                requestLocationPermission();
                if (locationRequestGranted) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Please provide a valid room number.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide a room number.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        roomEditText = findViewById(R.id.roomEditText);
        munImageView = findViewById(R.id.munImageView);
        mainLayout = findViewById(R.id.mainLayout);
//        buildings.clear();
        openingRelativeLayout = findViewById(R.id.openingLayout);
        startOpeningAnimation();

        mainLayout.setOnClickListener(this);
        BuildingListInterface createBuildingList = new ConstructBuildingList(buildings);
        createBuildingList.setBuildingArrayList();
//        Log.i("check size", Double.toString(buildings.get(0).getLat()));
        roomEditText.setOnKeyListener(this);

        munImageView.setOnClickListener(this);
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

    // showing menus to add courses, show courses, and log out options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // when one of the menu options are pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addCourses) {
            Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.showCourses) {
            Intent intent = new Intent(getApplicationContext(), ShowCourseActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logoutMenu) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            // finishes the activity and returns to initial activity
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

//     Logout user if pressed the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    public void startOpeningAnimation() {
        openingRelativeLayout.animate().alpha(1).setDuration(1200).start();
        Runnable r = new Runnable() {
            @Override
            public void run(){
                openingRelativeLayout.animate().alpha(0).setDuration(1200).start();
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 1500);

        r = new Runnable() {
            @Override
            public void run(){
                openingRelativeLayout.setVisibility(View.INVISIBLE);
                mainLayout.animate().alpha(1).setDuration(1500).start();
            }
        };
        h.postDelayed(r, 2500);

        r = new Runnable() {
            @Override
            public void run(){
                getSupportActionBar().show();
            }
        };
        h.postDelayed(r, 3500);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // To ensure pressing enter on the on screen keyboard would also perform the intended search button tasks
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            showBuilding(v); // perform the Search button tasks
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        // To ensure the onscreen keyboard hides itself when tapped on the application window
        if (v.getId() == R.id.mainLayout || v.getId() == R.id.munImageView) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }
}
