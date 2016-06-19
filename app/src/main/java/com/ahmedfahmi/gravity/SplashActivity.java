package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ahmed Fahmi on 6/8/2016.
 */
public class SplashActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}


