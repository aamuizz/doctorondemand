package com.doctorondemand.doctorondemand;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyAccountDoctor extends AppCompatActivity {
    ImageView myaccountdoctor;
    InternetConnectivity internetConnectivity = new InternetConnectivity(this);
    String useruid;



    @Override
    public void onBackPressed() {
        alertbox(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_doctor);
        myaccountdoctor = findViewById(R.id.myaccountdoctor);
        //Changing the status of the user
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            useruid = firebaseUser.getUid();
            statuschanger("Online");
            DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Doctor");
            myref.child(useruid).child("Status").onDisconnect().setValue("Offline");


        myaccountdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(internetConnectivity.isConnected()) {
                    Intent intent = new Intent(getApplicationContext(), Myprofiledoctor.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MyAccountDoctor.this,"Network Not Available",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void statuschanger(String status){
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Doctor");
        myref.child(useruid).child("Status").setValue(status);

    }
    public void alertbox(final Context context){
        //log out dialogue button
        LayoutInflater li = LayoutInflater.from(context);

        View promptsView = li.inflate(R.layout.logoutdialoguebox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                statuschanger("Offline");
                                FirebaseAuth.getInstance().signOut();
                                MyAccountDoctor.super.onBackPressed();

                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
