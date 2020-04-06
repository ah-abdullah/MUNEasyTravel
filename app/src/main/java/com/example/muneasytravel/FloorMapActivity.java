package com.example.muneasytravel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * FloorMapActivity allows user to view the floor maps of each floor of the building the user searched for
 */
public class FloorMapActivity extends AppCompatActivity {
    int numberOfFloor;
    int currentFloor = 1;
    private ImageView floorMapImageView;
    private boolean _1FimageDownloaded = false;
    private boolean _2FimageDownloaded = false;
    private boolean _3FimageDownloaded = false;
    private boolean _4FimageDownloaded = false;
    private boolean _5FimageDownloaded = false;
    private ArrayList<String> imageURL = new ArrayList<>();
    private String buildingPrefix;
    private ArrayList<Bitmap> myImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_map);

        getSupportActionBar().hide();

        floorMapImageView = findViewById(R.id.floorMapImageView);

        Button floor1Button = findViewById(R.id.floor1Button);
        Button floor2Button = findViewById(R.id.floor2Button);
        Button floor3Button = findViewById(R.id.floor3Button);
        Button floor4Button = findViewById(R.id.floor4Button);
        Button floor5Button = findViewById(R.id.floor5Button);

        initializeImageURLArrayList();
        initializeMyImageArrayList();

        Intent intent = getIntent();
        numberOfFloor = intent.getIntExtra("numOfFloor", 1);
        buildingPrefix = intent.getStringExtra("buildingPrefix");

        switch(numberOfFloor) {
            case 1:
                floor1Button.setVisibility(View.VISIBLE);
                break;
            case 2:
                floor1Button.setVisibility(View.VISIBLE);
                floor2Button.setVisibility(View.VISIBLE);
                break;
            case 3:
                floor1Button.setVisibility(View.VISIBLE);
                floor2Button.setVisibility(View.VISIBLE);
                floor3Button.setVisibility(View.VISIBLE);
                break;
            case 4:
                floor1Button.setVisibility(View.VISIBLE);
                floor2Button.setVisibility(View.VISIBLE);
                floor3Button.setVisibility(View.VISIBLE);
                floor4Button.setVisibility(View.VISIBLE);
                break;
            case 5:
                floor1Button.setVisibility(View.VISIBLE);
                floor2Button.setVisibility(View.VISIBLE);
                floor3Button.setVisibility(View.VISIBLE);
                floor4Button.setVisibility(View.VISIBLE);
                floor5Button.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void showFloorMap1F(View view) {
        currentFloor = 1;
        if (!_1FimageDownloaded) { // ensures floor map of floor 1 is only downloaded once instead of every time a different floor button is tapped
            getFloorMap(); // getting the floor map image from the database
            _1FimageDownloaded = true; // ensures floor map of floor 1 is only downloaded once instead of every time a different floor button is tapped
        } else {
            floorMapImageView.setImageBitmap(myImage.get(currentFloor-1));
        }
    }

    public void showFloorMap2F(View view) {
        currentFloor = 2;
        if (!_2FimageDownloaded) { // ensures floor map of floor 2 is only downloaded once instead of every time a different floor button is tapped
            getFloorMap();
            _2FimageDownloaded = true;
        } else {
            floorMapImageView.setImageBitmap(myImage.get(currentFloor-1));
        }
    }

    public void showFloorMap3F(View view) {
        currentFloor = 3;
        if (!_3FimageDownloaded) { // ensures floor map of floor 3 is only downloaded once instead of every time a different floor button is tapped
            getFloorMap();
            _3FimageDownloaded = true;
        } else {
            floorMapImageView.setImageBitmap(myImage.get(currentFloor-1));
        }
    }

    public void showFloorMap4F(View view) {
        currentFloor = 4;
        if (!_4FimageDownloaded) { // ensures floor map of floor 4 is only downloaded once instead of every time a different floor button is tapped
            getFloorMap();
            _4FimageDownloaded = true;
        } else {
            floorMapImageView.setImageBitmap(myImage.get(currentFloor-1));
        }
    }

    public void showFloorMap5F(View view) {
        currentFloor = 5;
        if (!_5FimageDownloaded) { // ensures floor map of floor 5 is only downloaded once instead of every time a different floor button is tapped
            getFloorMap();
            _5FimageDownloaded = true;
        } else {
            floorMapImageView.setImageBitmap(myImage.get(currentFloor-1));
        }
    }

    // Is executed if user taps on floor map, moves user to a new activity where users can enhance the floor map (zoom in/out) for a more clear picture
    public void fullScreenImageView(View view) {
        Intent intent = new Intent(getApplicationContext(), FullScreenFloorMapActivity.class);
        intent.putExtra("currentFloor", currentFloor);
        intent.putExtra("imageURL", imageURL.get(currentFloor-1));
        startActivity(intent);
    }

    // getting the floor map image from Firebase database
    public void getFloorMap() {
        FirebaseDatabase.getInstance().getReference().child("FloorMaps").child(buildingPrefix).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                imageURL.set(currentFloor-1, dataSnapshot.child(Integer.toString(currentFloor)).getValue().toString());
                downloadImage(currentFloor-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    // downloading the image from the database
    public void downloadImage(int floorMapIndex) {
        ImageDownloader task = new ImageDownloader();
        try {
            myImage.set(floorMapIndex, task.execute(imageURL.get(floorMapIndex)).get());
            floorMapImageView.setImageBitmap(myImage.get(floorMapIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Is executed when users tap the Building Information Button, moves users to a new activity showing the important locations of the building the user searched for
    public void showImportantLocation(View view) {
        Intent intent = new Intent(getApplicationContext(), ImportantLocationActivity.class);
        intent.putExtra("buildingPrefix", buildingPrefix);
        startActivity(intent);
    }

    public void initializeMyImageArrayList() {
        myImage.add(null);
        myImage.add(null);
        myImage.add(null);
        myImage.add(null);
        myImage.add(null);
    }

    public void initializeImageURLArrayList() {
        imageURL.add("");
        imageURL.add("");
        imageURL.add("");
        imageURL.add("");
        imageURL.add("");
    }

}
