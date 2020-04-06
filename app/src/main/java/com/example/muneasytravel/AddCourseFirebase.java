package com.example.muneasytravel;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/*******************************************************************************
 * AddCourseFirebase implements AddCourseInterface
 * Used to store user provided course name and room no. to the Firebase Database
 */
public class AddCourseFirebase implements AddCourseInterface {
    private EditText courseNameEditText;
    private EditText roomNoEditText;

    public AddCourseFirebase(EditText courseNameEditText, EditText roomNoEditText) {
        this.courseNameEditText = courseNameEditText;
        this.roomNoEditText = roomNoEditText;
    }

    // Process adding course to the Firebase Database
    @Override
    public boolean addCourse() {
        Map<String,String> courseMap = new HashMap<>();
        courseMap.put("CourseName", courseNameEditText.getText().toString());
        courseMap.put("Room", roomNoEditText.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Course").push().setValue(courseMap); // pushing course name and room no. to the database
        return true;
    }
}
