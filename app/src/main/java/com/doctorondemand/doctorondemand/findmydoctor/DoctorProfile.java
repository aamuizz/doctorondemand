package com.doctorondemand.doctorondemand.findmydoctor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctorondemand.doctorondemand.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoctorProfile extends AppCompatActivity {
    TextView namedrprofile,specdrprofile,addressdrprofile,phonenodrprofile,emaildrprofile,status,onlineiconprofile;
    ImageView drprofilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        namedrprofile = findViewById(R.id.namedrprofile);
        specdrprofile = findViewById(R.id.specdrprofile);
        addressdrprofile = findViewById(R.id.addressdrprofile);
        phonenodrprofile = findViewById(R.id.phonenodrprofile);
        emaildrprofile = findViewById(R.id.emaildrprofile);
        status = findViewById(R.id.statuson);
        drprofilepic = findViewById(R.id.drprofilepic);
        onlineiconprofile=findViewById(R.id.onlineiconprofile);
        Bundle mBundle = getIntent().getExtras();
        if(mBundle!=null){
            namedrprofile.setText(mBundle.get("Name").toString());
            specdrprofile.setText(mBundle.get("Specialization").toString());
            addressdrprofile.setText(mBundle.get("Address").toString());
            phonenodrprofile.setText(mBundle.get("Phoneno").toString());
            emaildrprofile.setText(mBundle.get("Email").toString());
            status.setText(mBundle.get("Status").toString());

            if(mBundle.get("Status").toString().equals("Offline")){
                status.setTextColor(Color.RED);
                onlineiconprofile.setBackgroundResource(R.drawable.ic_brightness_red_black_24dp);
            }
            else{
                status.setTextColor(Color.GREEN);
                onlineiconprofile.setBackgroundResource(R.drawable.ic_brightness_1_black_24dp);
            }


        }

    }

}
