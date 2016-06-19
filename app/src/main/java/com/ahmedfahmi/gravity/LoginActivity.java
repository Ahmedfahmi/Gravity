package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ahmedfahmi.gravity.managers.LoginManager;
import com.firebase.client.Firebase;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private EditText etEmail;
    private EditText etPassword;
    private Intent intentOfSignupActivity;
    private LoginManager loginManager;
    private InputMethodManager inputMethodManager;
    private String email;
    private String password;
    private RelativeLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        initiate();
        mainLayout.setOnClickListener(this);
    }

    public void login(View view) {
        email = etEmail.getText().toString().toLowerCase();
        password = etPassword.getText().toString();
        loginManager.login(this, email, password);
    }


    private void initiate() {
        etEmail = (EditText) findViewById(R.id.loginEmail);
        etPassword = (EditText) findViewById(R.id.loginPassword);
        loginManager = LoginManager.instance();
        intentOfSignupActivity = new Intent(getApplicationContext(), SignupActivity.class);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mainLayout = (RelativeLayout) findViewById(R.id.loginMainLayout);
        etPassword.setOnKeyListener(this);
    }

    public void switchToSignUp(View view) {
        startActivity(intentOfSignupActivity);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            login(v);
        }

        return false;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.loginMainLayout) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

    }
}

