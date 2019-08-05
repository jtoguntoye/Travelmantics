package com.travelmantics;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.AuthProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseUtil firebaseUtil;
    public static FirebaseAuth  mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthStateListener;;
    public static ArrayList<TravelDeal> mDeals;
    private static final int RC_SIGN_IN = 123;
    private static ListActivity Caller;
    public static boolean isAdmin;
//private empty constructor created for the class so that an object of the  class is not instantiated
//from outside this class
    private FirebaseUtil(){}

public static void dbreference(String ref, final ListActivity callerActivity){
        if (firebaseUtil == null){
            Caller =callerActivity;
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    //put the firebase signIn in an If statement to avoid returning user to
                    //the signin page everytime even after logging in since the
                    if(mFirebaseAuth.getCurrentUser() == null){
                    FirebaseUtil.signIn();
                    }
                    else{
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);

                    }
                    Toast.makeText(callerActivity.getBaseContext(),
                            "Welcome back", Toast.LENGTH_LONG).show();

                }
            };

        }
        mDeals = new ArrayList<TravelDeal>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
}

    private static void checkAdmin(String uId) {
        FirebaseUtil.isAdmin = false;
        DatabaseReference ref = mFirebaseDatabase.getReference().child("administrator")
                .child(uId);

        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FirebaseUtil.isAdmin =true;
                Caller.showMenu();
                Log.d("Admin", "You are an administrator");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //set the child eventListener to the database reference
        ref.addChildEventListener(listener);
    }

    private static void signIn(){
    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    // Create and launch sign-in intent
    Caller.startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN);
}

public static void AttachListener(){
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
}

public static void DetachListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
}

}
