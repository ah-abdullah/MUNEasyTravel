package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowCourseActivity extends AppCompatActivity {

    private ArrayList<String> courseName = new ArrayList<>();
    private ArrayList<String> roomNo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course);

        getSupportActionBar().setTitle("Courses");
        ListView courseListView = findViewById(R.id.courseListView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, courseName){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text1.setText(courseName.get(position));
                text2.setText(roomNo.get(position));
                return view;
            }
        };
        Observer observer = new UpdateCourseObserver(courseListView, adapter, courseName, roomNo);
        observer.update();
    }
}
