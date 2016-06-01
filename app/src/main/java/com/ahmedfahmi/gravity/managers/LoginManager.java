package com.ahmedfahmi.gravity.managers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ahmedfahmi.gravity.LoginActivity;
import com.ahmedfahmi.gravity.UserProfileActivity;
import com.ahmedfahmi.gravity.UsersListActivity;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Ahmed Fahmi on 5/30/2016.
 */
public class LoginManager extends AppCompatActivity{
    private static LoginManager loginManager = new LoginManager();
    private FirebaseManager firebaseManager;
    private Firebase firebase;
    private Context context;
    private Intent intent;

    public void setContext(Context context) {
        this.context = context;
        intent = new Intent(context, UserProfileActivity.class);
    }

    public boolean isLogged() {
        return logged;
    }

    private boolean logged = false;


    public static LoginManager instance() {

        return loginManager;
    }

    private LoginManager() {
        firebaseManager = FirebaseManager.instance();
        firebase = firebaseManager.getFirebase();
    }

    public void login(String email,String password){
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.i("TAG","ok");

                //startActivity(intent);
                logged=true;






            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.i("TAG",firebaseManager.errorMessage(firebaseError));

            }
        });
    }


}
