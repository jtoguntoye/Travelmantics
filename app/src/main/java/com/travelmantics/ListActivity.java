package com.travelmantics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

        ArrayList<TravelDeal> deals;
        private FirebaseDatabase mFirebaseDatabase;
        private DatabaseReference mFirebaseReference;

        private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView DealsRecyclerView = findViewById(R.id.dealsRecyclerView);
        final DealsAdapter dealsAdapter =new DealsAdapter();
        DealsRecyclerView.setAdapter(dealsAdapter);
        LinearLayoutManager layoutManager  =
                new LinearLayoutManager(this, RecyclerView.VERTICAL,false);

        DealsRecyclerView.setLayoutManager(layoutManager);


    }


}


