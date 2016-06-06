package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedfahmi.gravity.managers.LoginManager;
import com.ahmedfahmi.gravity.managers.SignupManager;
import com.firebase.client.Firebase;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;

    private TextView tvSignup;
    private ImageView gravityImage;

    private Intent intentOfSignupActivity;
    private Intent intentOfUserList;
    private Intent intentOfUserProfile;

    private SignupManager signupManager;
    private LoginManager loginManager;

    public static final String EMAIL_EXTRA = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        intiate();


    }

    public void login(View view) {
        String email = etEmail.getText().toString().toLowerCase();
        String passWord = etPassword.getText().toString();
        intentOfUserProfile.putExtra(EMAIL_EXTRA, email);
        loginManager.login(email, passWord);

        //fix delay here!!
        if (loginManager.isLogged()) {
            startActivity(intentOfUserProfile);
        }
    }


    private void intiate() {


        etEmail = (EditText) findViewById(R.id.loginEmail);
        etPassword = (EditText) findViewById(R.id.loginPassword);
        tvSignup = (TextView) findViewById(R.id.tvSignup);
        gravityImage = (ImageView) findViewById(R.id.gravityImage);
        signupManager = SignupManager.instance();
        loginManager = LoginManager.instance();
        intentOfSignupActivity = new Intent(getApplicationContext(), SignupActivity.class);
        intentOfUserList = new Intent(getApplicationContext(), UsersListActivity.class);
        intentOfUserProfile = new Intent(getApplicationContext(), UserProfileActivity.class);
        loginManager.setContext(this);


    }

    public void switchToSignup(View view) {
        startActivity(intentOfSignupActivity);
    }

}

