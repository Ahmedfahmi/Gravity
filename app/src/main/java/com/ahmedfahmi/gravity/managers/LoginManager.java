package com.ahmedfahmi.gravity.managers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ahmedfahmi.gravity.Extra.Constants;
import com.ahmedfahmi.gravity.UserProfileActivity;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Ahmed Fahmi on 5/30/2016.
 */
public class LoginManager extends AppCompatActivity {
    private static LoginManager loginManager = new LoginManager();
    private FirebaseManager firebaseManager;
    private Firebase firebase;


    public static LoginManager instance() {

        return loginManager;
    }

    private LoginManager() {
        firebaseManager = FirebaseManager.instance();
        firebase = firebaseManager.getFirebase();
    }

    public void login(final Context context, final String email, String password) {

        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra(Constants.ACTIVE_EMAIL_EXTRA, email);
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show();
                context.startActivity(intent);

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(context, firebaseManager.errorMessage(firebaseError), Toast.LENGTH_LONG).show();

            }
        });
    }


}
