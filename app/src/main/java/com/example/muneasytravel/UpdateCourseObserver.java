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

/***************************************************
 * UpdateCourseObserver extends Observer
 * Used to update the ShowCourseActivity courseListView when users are moved to ShowCourseActivity
 */
public class UpdateCourseObserver extends Observer {

    private ListView courseListView;
    private ArrayList<String> courseName;
    private ArrayList<String> roomNo;
    private ArrayAdapter adapter;

    public UpdateCourseObserver(ListView courseListView, ArrayAdapter adapter, ArrayList<String> courseName, ArrayList<String> roomNo ) {
        this.courseListView = courseListView;
        this.adapter = adapter;
        this.courseName = courseName;
        this.roomNo = roomNo;
    }

    // Getting the stored courses from Firebase Database
    @Override
    public void update() {
        courseListView.setAdapter(adapter); // courseListView is using the adapter ArrayAdapter to show the added courses
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Course").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                courseName.add(dataSnapshot.child("CourseName").getValue().toString());
                roomNo.add(dataSnapshot.child("Room").getValue().toString());
                adapter.notifyDataSetChanged(); // update adapter if anything is changed (courses are added by users)
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
