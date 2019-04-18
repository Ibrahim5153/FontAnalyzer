package com.example.fontanalyzer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fontanalyzer.Models.User;

public class AuthActivity extends AppCompatActivity {

    Button loginBtn;
    TextView notAusertv;
    EditText passwordEt;
    EditText emailEt;

    FontViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        viewModel = ViewModelProviders.of(this).get(FontViewModel.class);

        loginBtn = findViewById(R.id.login_button);
        emailEt = findViewById(R.id.login_email_et);
        passwordEt = findViewById(R.id.login_password_et);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(emailEt.getText().toString().equals("")){
                    emailEt.setError("Please enter email!");
                    return;
                }

                if(passwordEt.getText().toString().equals("")){

                    passwordEt.setError("Please enter password!");
                    return;
                }

                viewModel.getUserByEmail(emailEt.getText().toString()).observe(AuthActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(@Nullable User user) {

                        if(user != null){

                            if(passwordEt.getText().toString().equals(user.getPassword()))
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
                                editor.putBoolean("logged_in", true);
                                editor.apply();

                                AuthActivity.this.startActivity(new Intent(AuthActivity.this, ScaningActivity.class));
                                finish();
                            }
                            else{

                                passwordEt.requestFocus();
                                passwordEt.setError("Wrong password!");
                            }
                        }else {

                            emailEt.requestFocus();
                            emailEt.setError("User not found!");
                        }
                    }
                });

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
