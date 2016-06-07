package com.ahmedfahmi.gravity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Firebase.setAndroidContext(this);
        initiate();


    }

    private void initiate() {
        signupManager = SignupManager.instance();
        etFirstName = (EditText) findViewById(R.id.signupFirstName);
        etLastName = (EditText) findViewById(R.id.signupLastName);
        etEmail = (EditText) findViewById(R.id.signupEmail);
        etPassword = (EditText) findViewById(R.id.signupPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.signupPasswordConfirm);
        etMobile = (EditText) findViewById(R.id.signupMobile);


    }

    public void signUp(View view) {
        String firstName = etFirstName.getText().toString().toLowerCase();
        String lastName = etLastName.getText().toString().toLowerCase();
        String mobile = etMobile.getText().toString().toLowerCase();
        String email = etEmail.getText().toString().toLowerCase();
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirmation.getText().toString();
        if (

                firstName != null
                        && lastName != null
                        && mobile != null
                        && password != null
                        && email != null
                        && password.length() >= 6
                        && password.equals(passwordConfirm)
                        && mobile.length() >= 11


                ) {
            signupManager.signUp(this, email, password, firstName, lastName, mobile);

        } else {

            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "password must be more than 6 characters", Toast.LENGTH_LONG).show();
            } else if (!password.equals(passwordConfirm)) {
                Toast.makeText(getApplicationContext(), "password fields doesn't match", Toast.LENGTH_LONG).show();
            } else if (mobile.length() < 11) {
                Toast.makeText(getApplicationContext(), "mobile number is too short", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "check the correctness of all fields", Toast.LENGTH_LONG).show();
            }


        }


    }


}
