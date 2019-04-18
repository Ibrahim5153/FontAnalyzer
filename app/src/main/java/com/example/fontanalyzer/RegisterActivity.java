package com.example.fontanalyzer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fontanalyzer.Models.User;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyUserTv;
    FontViewModel viewModel;
    EditText emailet;
    EditText passwordet;
    EditText confirmPasswordet;
    Button registerBtn;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUser = new User();
        viewModel = ViewModelProviders.of(this).get(FontViewModel.class);

        emailet = findViewById(R.id.reg_email_et);
        passwordet = findViewById(R.id.reg_password_et);
        confirmPasswordet = findViewById(R.id.reg_password_cnfrm_et);
        registerBtn = findViewById(R.id.button_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailet.getText().toString().equalsIgnoreCase("")){

                    emailet.requestFocus();
                    emailet.setError("Enter valid email!");
                    return;
                }

                if(passwordet.getText().toString().equalsIgnoreCase("")){

                    passwordet.requestFocus();
                    passwordet.setError("Please enter password!");
                    return;

                }

                if(confirmPasswordet.getText().toString().equalsIgnoreCase("")){

                    confirmPasswordet.requestFocus();
                    confirmPasswordet.setError("Please enter password!");
                    return;

                }

                if(!passwordet.getText().toString().equals(confirmPasswordet.getText().toString())){

                    passwordet.requestFocus();
                    passwordet.setError("Password does not match!");
                    confirmPasswordet.setError("Password does not match!");
                    return;

                }

                Observer observer = new Observer<User>() {
                    @Override
                    public void onChanged(@Nullable User user) {
                        if(user == null){

                            Register();
                            finish();
                        }else {

                            emailet.requestFocus();
                            emailet.setError("User with this email already exist!");
                        }
                    }
                };


                viewModel.getUserByEmail(emailet.getText().toString()).observe(RegisterActivity.this,observer);
            }
        });

        alreadyUserTv = findViewById(R.id.already_user_tv);
        alreadyUserTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    void Register(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUser.setEmail(emailet.getText().toString());
                mUser.setPassword(passwordet.getText().toString());

                viewModel.insertUser(mUser);

            }
        });

    }
}
