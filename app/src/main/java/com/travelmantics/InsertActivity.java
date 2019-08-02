package com.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText TxtTitle, TxtPrice, TxtDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        FirebaseUtil.dbreference("traveldeals");

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference =FirebaseUtil.mDatabaseReference;
        TxtTitle = findViewById(R.id.txtTitle);
        TxtPrice =findViewById(R.id.txtPrice);
        TxtDescription = findViewById(R.id.txtDescription);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.save_menu:
                    saveDeal();
                    Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
                    clean();
                    return true;
            }

        return super.onOptionsItemSelected(item);
    }



    private void saveDeal() {
            String title =TxtTitle.getText().toString();
            String price = TxtPrice.getText().toString();
            String description = TxtDescription.getText().toString();
            TravelDeal travelDeal = new TravelDeal(title, price, description, "");
            mDatabaseReference.push().setValue(travelDeal);

    }
    private void clean() {
        TxtTitle.setText("");
        TxtPrice.setText("");
        TxtDescription.setText("");
        TxtTitle.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
    return true;}
}





