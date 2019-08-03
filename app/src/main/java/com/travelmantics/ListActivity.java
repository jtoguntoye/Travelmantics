package com.travelmantics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

        private FirebaseDatabase mFirebaseDAtabase;
        private DatabaseReference mDatabaseREference;
        private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.DetachListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //load the deals in the onresume method since FirebaseUI
        // causes user to sign in first in onCreate
        FirebaseUtil.dbreference("traveldeals", this );
        RecyclerView DealsRecyclerView = findViewById(R.id.dealsRecyclerView);
        final DealsAdapter dealsAdapter =new DealsAdapter();
        DealsRecyclerView.setAdapter(dealsAdapter);
        LinearLayoutManager layoutManager  =
                new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        DealsRecyclerView.setLayoutManager(layoutManager);
        FirebaseUtil.AttachListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.insert_menu:
                Intent intent = new Intent(this, DealActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
        }

    }



