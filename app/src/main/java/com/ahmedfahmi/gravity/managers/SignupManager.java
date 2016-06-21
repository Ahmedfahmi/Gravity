package com.ahmedfahmi.gravity.managers;

import android.content.Context;
import android.widget.Toast;

import com.ahmedfahmi.gravity.Extra.Constants;
import com.ahmedfahmi.gravity.model.User;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Ahmed Fahmi on 5/30/2016.
 */
public class SignupManager {


    private static SignupManager signupManager;
    private FirebaseManager firebaseManager;
    private Firebase firebaseInstant;
    private Firebase firebaseUserUrl;


    private SignupManager() {
        firebaseManager = FirebaseManager.instance();
        firebaseInstant = firebaseManager.getFirebase();
        firebaseUserUrl = firebaseManager.generateFirebase(Constants.USER_URL);

    }

    public static SignupManager instance() {
        if (signupManager == null) {
            signupManager = new SignupManager();

        }
        return signupManager;
    }


    public void signUp(final Context context, final String email, final String password, final String firstName, final String lastName, final String mobile) {
        firebaseInstant.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                User user = new User(email, password, mobile, lastName, firstName);
                firebaseManager.createChild(firebaseUserUrl, email.substring(0, email.indexOf("@"))).setValue(user);
                Toast.makeText(context, "Signed up Successfully", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(context, firebaseManager.errorMessage(firebaseError), Toast.LENGTH_LONG).show();

            }
        });


    }
}
