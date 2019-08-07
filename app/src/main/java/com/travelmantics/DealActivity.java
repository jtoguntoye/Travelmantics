package com.travelmantics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.Resource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class DealActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText TxtTitle, TxtPrice, TxtDescription;
    TravelDeal deal;
    private static final int PICTURE_RESULT = 42;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference =FirebaseUtil.mDatabaseReference;
        TxtTitle = findViewById(R.id.EdittxtTitle);
        TxtPrice =findViewById(R.id.EdittxtPrice);
        TxtDescription = findViewById(R.id.EdittxtDescription);
        imageView = findViewById(R.id.image_deal);



        Intent intent = getIntent();
        TravelDeal deal= (TravelDeal) intent.getSerializableExtra("Deal");

        if(deal == null){
            deal = new TravelDeal();
        }
        this.deal = deal;
        TxtTitle.setText(deal.getTitle());
        TxtDescription.setText(deal.getDescription());
        TxtPrice.setText(deal.getPrice());

        showImage(deal.getImageUrl());
        Button btnImage  = findViewById(R.id.btnImage);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent.createChooser(intent,
                        "insert Picture"), PICTURE_RESULT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICTURE_RESULT&& resultCode ==RESULT_OK){
            Uri imageUri = data.getData();
            final StorageReference ref = FirebaseUtil.mStorageRef.child(imageUri.getLastPathSegment());
            ref.putFile(imageUri).addOnSuccessListener(this,    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    String pictureName = taskSnapshot.getStorage().getPath();
                    while
                    (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        String url = downloadUrl.toString();
                   // String url = ref.getDownloadUrl().toString();
                    deal.setImageUrl(url);
                    deal.setImageName(pictureName);
                    showImage(url);
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                 case R.id.save_menu:
                    saveDeal();
                    Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
                    clean();
                    return true;
                case R.id.delete_deal:
                    deleteDeal();
                    getBackToList();

                    return true;
            }

        return super.onOptionsItemSelected(item);
    }



    private void saveDeal() {
        deal.setTitle(TxtTitle.getText().toString());
        deal.setDescription(TxtDescription.getText().toString());
        deal.setPrice(TxtPrice.getText().toString());
        if(deal.getID() == null){
        mDatabaseReference.push().setValue(deal);
        }
        mDatabaseReference.child(deal.getID())
                .setValue(deal);
    }
    private void deleteDeal() {
        if(deal == null){
            Toast.makeText(this, "You have to make a deal first before you can delete",
                    Toast.LENGTH_LONG).show();;
        }
        mDatabaseReference.child(deal.getID())
                .removeValue();
        if(deal.getImageName()!=null && deal.getImageName().isEmpty()==false){
            StorageReference picRef = FirebaseUtil.mStorage.getReference().child(deal.getImageName());
            picRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                     @Override
                                                     public void onSuccess(Void aVoid) {
                                                Log.d("Delete image:", "Image deleted");
                                                     }
                                                 }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                Log.d("Delete image", e.getMessage());
                }
            });
        }

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
    private void enableEditTexts(boolean isEnabled){
        TxtTitle.setEnabled(isEnabled);
        TxtDescription.setEnabled(isEnabled);
        TxtPrice.setEnabled(isEnabled);

    }
    private void showImage(String url){
        if(url!=null && url.isEmpty()==false){
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get()
                    .load(url)
                    .resize(width,width*2/3)
                    .centerCrop()
                    .into(imageView);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        if(FirebaseUtil.isAdmin){
            menu.findItem(R.id.delete_deal).setVisible(true);
            menu.findItem(R.id.save_menu).setVisible(true);
            enableEditTexts(true);
        }
        else{
            menu.findItem(R.id.delete_deal).setVisible(false);
            menu.findItem(R.id.save_menu).setVisible(false);
            enableEditTexts(false);
        }


    return true;
    }




}





