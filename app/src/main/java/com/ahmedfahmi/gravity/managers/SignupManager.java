package com.ahmedfahmi.gravity.managers;

import android.util.Log;
import android.widget.Toast;

import com.ahmedfahmi.gravity.LoginActivity;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Ahmed Fahmi on 5/30/2016.
 */
public class SignupManager {

    private String email;
    private String password;
    private static SignupManager signupManager;
    private FirebaseManager firebaseManager;
    private Firebase firebaseMain;
    private Firebase firebaseUserUrl;


    private SignupManager() {
        firebaseManager = FirebaseManager.instance();
        firebaseMain = firebaseManager.getFirebase();
        firebaseUserUrl = firebaseManager.generateFirebase("User");

    }

    public static SignupManager instance() {
        if (signupManager == null) {
            signupManager = new SignupManager();
        }
        return signupManager;
    }


    public void signup(final String email, final String password, final String firstName, final String lastName, final String mobile) {
        firebaseMain.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Log.i("TAG", "ok");
                User user = new User(email, password, mobile, lastName, firstName);
                firebaseUserUrl.child(email.substring(0, email.indexOf("@"))).setValue(user);


            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Log.i("TAG", firebaseError.toString());

            }
        });


    }
}
