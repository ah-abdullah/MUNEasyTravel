package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ImportantLocationActivity is for users to view the important locations of each building
 */
public class ImportantLocationActivity extends AppCompatActivity {

    private ArrayList<String> locationName = new ArrayList<>();
    private ArrayList<String> roomNo = new ArrayList<>();
    private String buildingPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_location);

        getSupportActionBar().setTitle("Important Location");
        ListView importantLocationListView = findViewById(R.id.importantLocationListView);

        Intent intent = getIntent();
        buildingPrefix = intent.getStringExtra("buildingPrefix");

        // Setting up the ArrayAdapter that importantLocationListView is using
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, locationName){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text1.setText(locationName.get(position));
                text2.setText(roomNo.get(position));
                return view;
            }
        };

        // Application of Observer pattern
        Observer observer = new UpdateImportantLocationObserver(importantLocationListView, adapter, locationName, roomNo, buildingPrefix);
        observer.update();
    }
}
