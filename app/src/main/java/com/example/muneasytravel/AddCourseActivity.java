package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity {

    private EditText courseNameEditText;
    private EditText roomNoEditText;
    private ArrayList<Building> buildings;
//    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        getSupportActionBar().setTitle("Add Course");
        Intent intent = getIntent();
        buildings = (ArrayList<Building>) intent.getSerializableExtra("buildingList");

        courseNameEditText = findViewById(R.id.courseNameEditText);
        roomNoEditText = findViewById(R.id.roomNoEditText);
    }

    public void addButtonClicked(View view) {
        String room = "";
        room = roomNoEditText.getText().toString().toUpperCase();
        if (!room.equals("")) {
            ValidateUserInputInterface validateRoomNo = new ValidateRoomNo(room, buildings);
            if (validateRoomNo.validate()) {
                AddCourseInterface storeCourse = new AddCourseFirebase(courseNameEditText, roomNoEditText);
                if (storeCourse.addCourse()) {
                    Intent intent = new Intent(getApplicationContext(), ShowCourseActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Please provide a valid room number.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide a room number.", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelButtonClicked(View view) {
        finish();
    }
}
