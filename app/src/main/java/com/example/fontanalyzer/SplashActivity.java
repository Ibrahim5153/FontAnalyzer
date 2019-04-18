package com.example.fontanalyzer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                if(prefs.getBoolean("first_time", false) == true &&  prefs.getBoolean("logged_in", false) == true) {

                    finish();
                    Intent intent = new Intent(SplashActivity.this, ScaningActivity.class);
                    SplashActivity.this.startActivity(intent);

                }else if(prefs.getBoolean("first_time", false) == true){

                    finish();
                    Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                    SplashActivity.this.startActivity(intent);
                }else {

                    finish();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                }

            }
        }, 2000);



    }
}
