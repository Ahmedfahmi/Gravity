package com.ahmedfahmi.gravity;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedfahmi.gravity.managers.SignupManager;
import com.firebase.client.Firebase;


public class SignupActivity extends AppCompatActivity {

    private SignupManager signupManager;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etMobile;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirmation;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Firebase.setAndroidContext(this);

        intiate();


    }

    private void intiate() {
        signupManager = SignupManager.instance();
        etFirstName = (EditText) findViewById(R.id.signupFirstName);
        etLastName = (EditText) findViewById(R.id.signupLastName);
        etEmail = (EditText) findViewById(R.id.signupEmail);
        etPassword = (EditText) findViewById(R.id.signupPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.signupPasswordConfirm);
        etMobile = (EditText) findViewById(R.id.signupMobile);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    public void signup(View view) {
        String firstName = etFirstName.getText().toString().toLowerCase();
        String lastName = etLastName.getText().toString().toLowerCase();
        String mobile = etMobile.getText().toString().toLowerCase();
        String email = etEmail.getText().toString().toLowerCase();
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirmation.getText().toString();
        Log.i("pass", email + password + passwordConfirm);
        if (password.equals(passwordConfirm) && firstName != null && lastName != null && mobile != null && password != null && email != null) {
            signupManager.signup(email, password, firstName, lastName, mobile);

        } else {
            Toast.makeText(getApplicationContext(), "check the correctness of all fields", Toast.LENGTH_LONG).show();
        }


    }


}
