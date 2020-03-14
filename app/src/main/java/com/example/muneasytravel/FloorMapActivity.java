package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FloorMapActivity extends AppCompatActivity {
    int numberOfFloor;
    private Button floor1Button;
    private Button floor2Button;
    private Button floor3Button;
    private Button floor4Button;
    private Button floor5Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_map);

        getSupportActionBar().hide();

        floor1Button = findViewById(R.id.floor1Button);
        floor2Button = findViewById(R.id.floor2Button);
        floor3Button = findViewById(R.id.floor3Button);
        floor4Button = findViewById(R.id.floor4Button);
        floor5Button = findViewById(R.id.floor5Button);

        Intent intent = getIntent();
        numberOfFloor = intent.getIntExtra("numOfFloor", 1);

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
}
