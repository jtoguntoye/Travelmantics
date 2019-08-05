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

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        MenuItem insertMenu = menu.findItem(R.id.insert_menu);
        if(FirebaseUtil.isAdmin == true){
            insertMenu.setVisible(true);
        }
        else{
            insertMenu.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.insert_menu:
                Intent intent = new Intent(this, DealActivity.class);
                startActivity(intent);
                return true;
            case R.id.log_out:
                //attach logout functionality to the listActivity
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUtil.AttachListener();
                            }
                        });
                FirebaseUtil.DetachListener();
                return true;
        }
        return super.onOptionsItemSelected(item);
        }

        public void showMenu(){
        invalidateOptionsMenu();// used to indicate to android that the content of the menu
                                //has changed and the menu should be redrawn
        }


    }



