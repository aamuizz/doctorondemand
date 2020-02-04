package com.doctorondemand.doctorondemand;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doctorondemand.doctorondemand.findmydoctor.DrDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class loginactivity extends AppCompatActivity {
    EditText etemail,etpassword;
    private ProgressDialog progressDialog;
    ArrayList<DrDetails>arrayList = new ArrayList<>();
    boolean found;

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        etemail= findViewById(R.id.emaillogin);
        etpassword = findViewById(R.id.passwordlogin);
        etpassword.setText("");

    }
    public void SigninButton(View v){
       InternetConnectivity internetConnectivity = new InternetConnectivity(this);
       if(internetConnectivity.isConnected()) {

           if(etemail.getText().toString().equals("")||etpassword.getText().toString().equals("")){
               Toast.makeText(this,"Something Wrong! Please Try Again",Toast.LENGTH_SHORT).show();
           }
           else {
               //Progress dialog when log in button is pressed
               progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
               progressDialog.setMessage("Authenticating...");
               progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
               progressDialog.setIndeterminate(true);
               progressDialog.show();
               final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
               firebaseAuth.signInWithEmailAndPassword(etemail.getText().toString(), etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           //Firebase authentication is successful
                           progressDialog.setMessage("Loading");

                           //Checking if the value is in database
                           DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
                           databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   found = false;
                                   for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                       Map<String, String> drdetails = (Map<String, String>) ds.getValue();
                                       if (drdetails.get("Email").contains(etemail.getText().toString())) {
                                           found = true;
                                           break;
                                       }
                                   }
                                   Toast.makeText(getApplicationContext(), "Sign in Successful", Toast.LENGTH_SHORT).show();
                                   //if found in doctor database then go to my account doctor
                                   if (found) {
                                       progressDialog.cancel();
                                       Intent intent = new Intent(loginactivity.this, MyAccountDoctor.class);
                                       startActivity(intent);
                                   } else {
                                       //if found in patient then go to my account activity of patient
                                       progressDialog.cancel();
                                       Intent intent = new Intent(loginactivity.this, MyAccount.class);
                                       startActivity(intent);

                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });


                       } else {
                           firebaseAuth.signOut();
                           progressDialog.cancel();
                           Toast.makeText(getApplicationContext(), "Email or Password is Wrong", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       }else{
           Toast.makeText(this,"Network not Available",Toast.LENGTH_SHORT).show();
       }
    }

}
