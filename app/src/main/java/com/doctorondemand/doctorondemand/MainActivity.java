package com.doctorondemand.doctorondemand;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView hometv1,hometv3;
    Typeface breesariffont;
    long time = System.currentTimeMillis();


    @Override
    public void onBackPressed() {
        //if back button is pressed two times with interval of less than 2 sec then it should close the app
        if(time+2000>System.currentTimeMillis()){
            super.onBackPressed();

        }
        else{
            Toast.makeText(this,"Press Back Again to Exit",Toast.LENGTH_SHORT).show();
        }
        time = System.currentTimeMillis();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hometv1 = findViewById(R.id.hometv1);
        hometv3 = findViewById(R.id.hometv3);
        breesariffont = Typeface.createFromAsset(this.getAssets(),"font/BreeSerif-Regular.ttf");
        hometv1.setTypeface(breesariffont);
        hometv3.setTypeface(breesariffont);


    }

    public void button1Click(View v) {
        Intent intent = new Intent(this, loginactivity.class);
        startActivity(intent);
    }
    public void button2Click(View v){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }
}
