package com.example.fontanalyzer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button getstartedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                1);

        getstartedBtn = findViewById(R.id.button_lets_start);

        getstartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
                editor.putBoolean("first_time", true);
                editor.apply();

                MainActivity.this.startActivity(new Intent(MainActivity.this, AuthActivity.class));
                finish();

            }
        });

    }
}
