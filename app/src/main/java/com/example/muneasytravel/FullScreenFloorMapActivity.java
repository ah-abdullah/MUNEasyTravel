package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

/**
 * FullScreenFloorMapActivity allows users to enhance the floor map (zoom in/out) for a more clear picture
 */
public class FullScreenFloorMapActivity extends AppCompatActivity {

    private int currentFloor;
    private PhotoView photoView;
    private Bitmap myImage;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_floor_map);

        photoView = findViewById(R.id.photo_view);
        Intent intent = getIntent();
        currentFloor = intent.getIntExtra("currentFloor", 1);
        imageURL = intent.getStringExtra("imageURL"); // getting the image URL of the corresponding floor map from FloorMapActivity
        downloadImage();
    }

    // downloading the corresponding floor map and showcasing it on screen for user to enhance the image for more clear picture
    public void downloadImage() {
        ImageDownloader task = new ImageDownloader();
        try {
            myImage = task.execute(imageURL).get();
            photoView.setImageBitmap(myImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
