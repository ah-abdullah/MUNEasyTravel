package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {
    ArrayList<Building> buildings = new ArrayList<>();
    private EditText roomEditText;
    Intent intent = null;

    public void showBuilding(View view) {
        intent = null;
        String room = "";
        room = roomEditText.getText().toString().toUpperCase();
        if (!room.equals("")) {
            String[] buildingPrefix = room.split("(?<=\\D)(?=\\d)", 2); // splitting the building prefix and the rest of the room number
            // buildingPrefix[0] will have the building prefix.
            for (int i = 0; i < buildings.size(); i++) {
                if (buildingPrefix[0].trim().equals(buildings.get(i).getBuildingName())) {
                    intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("room", buildings);
                    intent.putExtra("buildingIndex", Integer.toString(i));
                    break;
                }
            }
            if (intent != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please provide a valid room number.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide a room number.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        buildings.clear();
        setBuildingArrayList();
//        Log.i("check size", Integer.toString(buildings.size()));
        roomEditText = findViewById(R.id.roomEditText);
        roomEditText.setOnKeyListener(this);
    }

    public void setBuildingArrayList() {
        buildings.add(new Building("AA", 47.571119, -52.732470));
        buildings.add(new Building("EN", 47.5749547, -52.7353382));
        buildings.add(new Building("ED", 47.571138, -52.735965));
        buildings.add(new Building("BN", 47.575305, -52.734239));
        buildings.add(new Building("BT", 47.572472, -52.733165));
        buildings.add(new Building("CL", 47.575979, -52.731578));
        buildings.add(new Building("CS", 47.572119, -52.732740));
        buildings.add(new Building("ER", 47.573991, -52.734271));
        buildings.add(new Building("HH", 47.571609, -52.731290));
        buildings.add(new Building("MU", 47.572126, -52.730651));
        buildings.add(new Building("PA", 47.572083, -52.731621));
        buildings.add(new Building("SN", 47.572083, -52.731621));
        buildings.add(new Building("PE", 47.571072, -52.733971));
        buildings.add(new Building("QC", 47.577405, -52.730332));
        buildings.add(new Building("UC", 47.573277, -52.735325));
        buildings.add(new Building("A", 47.571119, -52.732470));
        buildings.add(new Building("C", 47.573114, -52.733526));
        buildings.add(new Building("H", 47.571497, -52.742860));
        buildings.add(new Building("J", 47.575434, -52.732859));
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // To ensure pressing enter on the on screen keyboard would also perform the intended search button tasks
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            showBuilding(v); // perform the Search button tasks
        }
        return false;
    }
}
