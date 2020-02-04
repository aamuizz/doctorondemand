package com.doctorondemand.doctorondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView splashimage;
    TextView tvdrondemand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashimage = (ImageView)findViewById(R.id.splashimg);
        tvdrondemand = findViewById(R.id.tvdrondemand);
        //Creating Animation for Splash Screen
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        tvdrondemand.startAnimation(myanim);
        splashimage.startAnimation(myanim);
        final Intent i = new Intent(this,MainActivity.class);
        Thread time = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }

            }
        };
        time.start();
    }
    }


