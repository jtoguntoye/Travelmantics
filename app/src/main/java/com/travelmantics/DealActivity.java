package com.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText TxtTitle, TxtPrice, TxtDescription;
    TravelDeal mDeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        FirebaseUtil.dbreference("traveldeals",this);

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference =FirebaseUtil.mDatabaseReference;
        TxtTitle = findViewById(R.id.EdittxtTitle);
        TxtPrice =findViewById(R.id.EdittxtPrice);
        TxtDescription = findViewById(R.id.EdittxtDescription);
        Intent intent = getIntent();
        TravelDeal deal= (TravelDeal) intent.getSerializableExtra("Deal");

        if(deal == null){
            deal = new TravelDeal();
        }
        this.mDeal = deal;
        TxtTitle.setText(deal.getTitle());
        TxtDescription.setText(deal.getDescription());
        TxtPrice.setText(deal.getPrice());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.save_menu:
                    saveDeal();
                    Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
                    clean();
                case R.id.delete_deal:
                    deleteDeal();
                    getBackToList();

                    return true;
            }

        return super.onOptionsItemSelected(item);
    }



    private void saveDeal() {
        mDeal.setTitle(TxtTitle.getText().toString());
        mDeal.setDescription(TxtDescription.getText().toString());
        mDeal.setPrice(TxtPrice.getText().toString());
        if(mDeal.getID() == null){
        mDatabaseReference.push().setValue(mDeal);
        }
        mDatabaseReference.child(mDeal.getID())
                .setValue(mDeal);
    }
    private void deleteDeal(){
        if(mDeal == null){
            Toast.makeText(this, "You have to make a deal first before you can delete",
                    Toast.LENGTH_LONG).show();;
        }
        mDatabaseReference.child(mDeal.getID())
                .removeValue();

    }

    private void getBackToList(){
        Intent BacktoList = new Intent(getApplicationContext(),ListActivity.class);
        startActivity(BacktoList);
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





