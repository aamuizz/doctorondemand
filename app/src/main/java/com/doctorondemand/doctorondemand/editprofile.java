package com.doctorondemand.doctorondemand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {
    EditText fullnameprofile, addresseditprofile, emaileditprofile, phonenoeditprofile, speceditprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        fullnameprofile = findViewById(R.id.fullnameprofile);
        addresseditprofile = findViewById(R.id.addresseditprofile);
        emaileditprofile = findViewById(R.id.emaileditprofile);
        phonenoeditprofile = findViewById(R.id.phonenoeditprofile);
        speceditprofile = findViewById(R.id.speceditprofile);

        //Getting the user details through firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> userdetails = (Map<String, String>) dataSnapshot.getValue();
                fullnameprofile.setText(userdetails.get("Name"));
                addresseditprofile.setText(userdetails.get("Address"));
                emaileditprofile.setText(userdetails.get("Email"));
                phonenoeditprofile.setText(userdetails.get("Phoneno"));
                speceditprofile.setText(userdetails.get("Specialization"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateprofile(View v) {
        //Updating the profile using firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Map<String,String>userdetails = new HashMap<>();
        userdetails.put("Name",fullnameprofile.getText().toString());
        userdetails.put("Email",emaileditprofile.getText().toString());
        userdetails.put("Address",addresseditprofile.getText().toString());
        userdetails.put("Phoneno",phonenoeditprofile.getText().toString());
        userdetails.put("Specialization",speceditprofile.getText().toString());
        InternetConnectivity internetConnectivity = new InternetConnectivity(this);
        if(internetConnectivity.isConnected()) {
            databaseReference.child(firebaseUser.getUid()).setValue(userdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(editprofile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(editprofile.this, Myprofiledoctor.class);
                        finish();
                        startActivity(intent);

                    } else {
                        Toast.makeText(editprofile.this, "Something Wrong! Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this,"Network Not Available",Toast.LENGTH_SHORT).show();
        }
        }
}