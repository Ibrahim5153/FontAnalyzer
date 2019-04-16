package com.example.fontanalyzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AuthActivity extends AppCompatActivity {

    Button loginBtn;
    TextView notAusertv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthActivity.this.startActivity(new Intent(AuthActivity.this, ScaningActivity.class));
            }
        });

        notAusertv = findViewById(R.id.not_a_user_tv);
        notAusertv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthActivity.this.startActivity(new Intent(AuthActivity.this, RegisterActivity.class));
            }
        });
    }
}
