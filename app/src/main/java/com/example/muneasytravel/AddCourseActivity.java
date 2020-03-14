package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCourseActivity extends AppCompatActivity {

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

    public void addButtonClicked(View view) {
        AddCourseInterface storeCourse = new AddCourseFirebase(courseNameEditText, roomNoEditText);
        if (storeCourse.addCourse()) {
            Intent intent = new Intent(getApplicationContext(), ShowCourseActivity.class);
            startActivity(intent);
        }
    }

    public void cancelButtonClicked(View view) {
        finish();
    }
}
