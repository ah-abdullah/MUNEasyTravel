package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddCourseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText courseNameEditText;
    private EditText roomNoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        getSupportActionBar().setTitle("Add Course");
        courseNameEditText = findViewById(R.id.courseNameEditText);
        roomNoEditText = findViewById(R.id.roomNoEditText);
    }

    public void addCourse(View view) {
        Map<String,String> courseMap = new HashMap<>();
        courseMap.put("CourseName", courseNameEditText.getText().toString());
        courseMap.put("Room", roomNoEditText.getText().toString());
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Course").push().setValue(courseMap);
        //FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Course").child("Room").setValue(roomNoEditText.getText().toString());
        Intent intent = new Intent(getApplicationContext(), ShowCourseActivity.class);
        startActivity(intent);
    }

    public void cancelCourse(View view) {
        finish();
    }
}
