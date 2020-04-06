package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * AddCourseActivity is for registered users to store their course name and room number in the database
 * AddCourseActivity not accessible by guest users
 */
public class AddCourseActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private EditText courseNameEditText;
    private EditText roomNoEditText;
    private ArrayList<Building> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        getSupportActionBar().setTitle("Add Course");
        Intent intent = getIntent();
        buildings = (ArrayList<Building>) intent.getSerializableExtra("buildingList"); // collect the building list passed from MainActivity

        courseNameEditText = findViewById(R.id.courseNameEditText);
        roomNoEditText = findViewById(R.id.roomNoEditText);
        ConstraintLayout addCourseLayout = findViewById(R.id.addCourseLayout);
        addCourseLayout.setOnClickListener(this);
        roomNoEditText.setOnKeyListener(this);
    }

    // Is executed if Add button is tapped
    public void addButtonClicked(View view) {
        String room = "";
        room = roomNoEditText.getText().toString().toUpperCase();
        if (!room.equals("")) { // checks if user left the text field empty
            ValidateUserInputInterface validateRoomNo = new ValidateRoomNo(room, buildings); // Procedure to validate room no. is delegated (application of Dependency Inversion Principle (DIP))
            if (validateRoomNo.validate()) { // room no. is validated, so add course name and room number to the database
                AddCourseInterface storeCourse = new AddCourseFirebase(courseNameEditText, roomNoEditText); // Procedure to add to the database is delegated (application of Dependency Inversion Principle (DIP))
                if (storeCourse.addCourse()) { // // Adding course to the database is successful, so move user to ShowCourseActivity
                    Intent intent = new Intent(getApplicationContext(), ShowCourseActivity.class);
                    startActivity(intent); // moving user to ShowCourseActivity
                }
            } else { // Invalid room no., so show error message to the user
                Toast.makeText(this, "Please provide a valid room number.", Toast.LENGTH_SHORT).show();
            }
        } else { // user left the room no. text field empty, so show error message to the user
            Toast.makeText(this, "Please provide a room number.", Toast.LENGTH_SHORT).show();
        }
    }

    // Is executed if Cancel button is tapped
    public void cancelButtonClicked(View view) {
        finish();
    }

    // Codes below are for enhanced user experience while using the on-screen keyboard
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // To ensure pressing enter on the on screen keyboard would also perform the intended add button tasks
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            addButtonClicked(v); // perform the Add button tasks
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        // To ensure the onscreen keyboard hides itself when tapped on the application window
        if (v.getId() == R.id.addCourseLayout) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
