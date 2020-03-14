package com.example.muneasytravel;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StoreCourseInDatabase implements AddCourseInterface {
    private EditText courseNameEditText;
    private EditText roomNoEditText;

    public StoreCourseInDatabase(EditText courseNameEditText, EditText roomNoEditText) {
        this.courseNameEditText = courseNameEditText;
        this.roomNoEditText = roomNoEditText;
    }

    @Override
    public boolean addCourse() {
        Map<String,String> courseMap = new HashMap<>();
        courseMap.put("CourseName", courseNameEditText.getText().toString());
        courseMap.put("Room", roomNoEditText.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Course").push().setValue(courseMap);
        return true;
    }
}
