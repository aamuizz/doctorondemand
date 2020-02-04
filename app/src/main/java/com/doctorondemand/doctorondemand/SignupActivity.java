package com.doctorondemand.doctorondemand;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText firstname,lastname,address,email,phoneno,password,specialization;
    Spinner accounttype;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        String[] accountlist = { "Account Type","Patient","Doctor" };
        final ArrayAdapter<String> accountspinadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountlist);
        accountspinadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        accounttype = findViewById(R.id.accounttype);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phoneno = findViewById(R.id.phoneno);
        address = findViewById(R.id.address);
        accounttype.setAdapter(accountspinadapter);
        specialization = findViewById(R.id.specialization);
        accounttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Account Type")) {
                    
                } else {
                    if(adapterView.getItemAtPosition(i).equals("Doctor")){
                        specialization.setVisibility(View.VISIBLE);
                    }
                    else{
                        specialization.setVisibility(View.INVISIBLE);
                    }
                    String item = adapterView.getItemAtPosition(i).toString();
                    final Toast toast = Toast.makeText(adapterView.getContext(), "Account Type: " + item, Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    },1000);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public void signupclick(View v){
        InternetConnectivity internetConnectivity = new InternetConnectivity(this);
        if(internetConnectivity.isConnected()) {
            //Progress dialog for loading when sign up button is pressed
            progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (firstname.getText().toString().equals("")|| lastname.getText().toString().equals("") || email.getText().toString().equals("")|| !email.getText().toString().endsWith(".com") || !email.getText().toString().contains("@") ||
                    password.getText().toString().equals("") && address.getText().toString().equals("")|| phoneno.getText().toString().equals("")|| accounttype.getSelectedItem() == "Account Type") {
                progressDialog.cancel();
                Toast.makeText(this, "Something Wrong! Please Try Again", Toast.LENGTH_SHORT).show();
            }
            else if(accounttype.getSelectedItem()=="Doctor"&&specialization.getText().toString().equals("")){
                progressDialog.cancel();
                Toast.makeText(this, "Something Wrong! Please Try Again", Toast.LENGTH_SHORT).show();
            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Map<String, String> usermap = new HashMap<>();
                                    usermap.put("Name", firstname.getText().toString() + " " + lastname.getText().toString());
                                    usermap.put("Address", address.getText().toString());
                                    usermap.put("Email", email.getText().toString());
                                    usermap.put("Phoneno", phoneno.getText().toString());
                                    if (accounttype.getSelectedItem().equals("Doctor")) {
                                        usermap.put("Specialization", specialization.getText().toString());
                                        usermap.put("Status", "Offline");
                                    }
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(accounttype.getSelectedItem().toString());
                                    ref.child(firebaseUser.getUid()).setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.cancel();
                                                Intent intent = new Intent(SignupActivity.this,loginactivity.class);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                            } else {
                                                progressDialog.cancel();
                                                Toast.makeText(getApplicationContext(), "Something Went Wrong! Please Try Again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    firebaseAuth.signOut();
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Error in Sign up", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        }
        else{
            Toast.makeText(this,"Network Not Available",Toast.LENGTH_SHORT).show();
        }
    }
}
