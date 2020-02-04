package com.doctorondemand.doctorondemand;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.doctorondemand.doctorondemand.findmydoctor.DoctorProfile;
import com.doctorondemand.doctorondemand.findmydoctor.findmydoctor;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccount extends AppCompatActivity {
    ImageView finddoctoriv;

    @Override
    public void onBackPressed() {
        alertbox(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        //checking the internet connectivity
        final InternetConnectivity internetConnectivity = new InternetConnectivity(this);
        finddoctoriv = (ImageView)findViewById(R.id.finddoctorIV);
        finddoctoriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(internetConnectivity.isConnected()) {
                    Intent intent = new Intent(getApplicationContext(), findmydoctor.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Network Not Available",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void alertbox(final Context context){
        //Alert Dialog for log out
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
                                FirebaseAuth.getInstance().signOut();
                                MyAccount.super.onBackPressed();

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
