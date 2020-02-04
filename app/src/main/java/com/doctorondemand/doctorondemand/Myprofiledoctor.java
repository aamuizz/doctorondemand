package com.doctorondemand.doctorondemand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Myprofiledoctor extends AppCompatActivity {
    TextView nameprofiledr,addressprofiledr,phonenoprofiledr,emailprofiledr,specmyprofiledr;
    String useruid1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //progress dialogue when my profile is pressed
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        setContentView(R.layout.activity_myprofiledoctor);
        nameprofiledr = findViewById(R.id.nameprofiledr);
        addressprofiledr = findViewById(R.id.addressprofiledr);
        phonenoprofiledr = findViewById(R.id.phonenoprofiledr);
        emailprofiledr = findViewById(R.id.emailprofiledr);
        specmyprofiledr = findViewById(R.id.specprofiledr);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        useruid1= firebaseUser.getUid();

        //Using firebase database to fetch the details for doctors profile
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Doctor");
        myref.child(useruid1).child("Status").onDisconnect().setValue("Offline");
       final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");

         databaseReference.child(useruid1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String,String>drdetails = (Map)dataSnapshot.getValue();
                    nameprofiledr.setText(drdetails.get("Name"));
                    addressprofiledr.setText(drdetails.get("Address"));
                    phonenoprofiledr.setText(drdetails.get("Phoneno"));
                    emailprofiledr.setText(drdetails.get("Email"));
                    specmyprofiledr.setText(drdetails.get("Specialization"));
                    progressDialog.cancel();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void statuschanger(String status){
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Doctor");
        myref.child(useruid1).child("Status").setValue(status);

    }

    public void editprofile(View v){
        finish();
        Intent intent = new Intent(this,editprofile.class);
        startActivity(intent);

    }

}
