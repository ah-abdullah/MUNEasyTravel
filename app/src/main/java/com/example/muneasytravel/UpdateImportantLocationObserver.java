package com.example.muneasytravel;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**************************************************************************************************************************
 * UpdateImportantLocationObserver extends Observer
 * Used to update the ImportantLocationActivity importantLocationListView when users are moved to ImportantLocationActivity
 */
public class UpdateImportantLocationObserver extends Observer {

    private ListView importantLocationListView;
    private ArrayList<String> locationName;
    private ArrayList<String> roomNo;
    private ArrayAdapter adapter;
    private String buildingPrefix;

    public UpdateImportantLocationObserver(ListView importantLocationListView, ArrayAdapter adapter, ArrayList<String> locationName, ArrayList<String> roomNo, String buildingPrefix ) {
        this.importantLocationListView = importantLocationListView;
        this.adapter = adapter;
        this.locationName = locationName;
        this.roomNo = roomNo;
        this.buildingPrefix = buildingPrefix;
    }

    // Getting the stored building info from Firebase Database
    @Override
    public void update() {
        importantLocationListView.setAdapter(adapter); // importantLocationListView is using the adapter ArrayAdapter to show the building info
        FirebaseDatabase.getInstance().getReference().child("BuildingInfo").child(buildingPrefix).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                locationName.add(dataSnapshot.child("LocationName").getValue().toString());
                roomNo.add(dataSnapshot.child("Room").getValue().toString());
                adapter.notifyDataSetChanged(); // update adapter
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
