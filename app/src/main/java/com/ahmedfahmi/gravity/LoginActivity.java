package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ahmedfahmi.gravity.managers.LoginManager;

import com.firebase.client.Firebase;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;

    private Intent intentOfSignupActivity;

    private LoginManager loginManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        initiate();
    }

    public void login(View view) {
        String email = etEmail.getText().toString().toLowerCase();
        String password = etPassword.getText().toString();
        loginManager.login(this, email, password);
    }


    private void initiate() {
        etEmail = (EditText) findViewById(R.id.loginEmail);
        etPassword = (EditText) findViewById(R.id.loginPassword);
        loginManager = LoginManager.instance();
        intentOfSignupActivity = new Intent(getApplicationContext(), SignupActivity.class);
    }

    public void switchToSignup(View view) {
        startActivity(intentOfSignupActivity);
    }

}

