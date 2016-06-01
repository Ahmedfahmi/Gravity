package com.ahmedfahmi.gravity.managers;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by Ahmed Fahmi on 5/30/2016.
 */
public class FirebaseManager {


    private final String FIREBASE_URL = "https://fahmiphotos.firebaseio.com/";
    private static FirebaseManager firebaseManager;
    private Firebase firebase;
    private FirebaseListAdapter firebaseListAdapter;



    private FirebaseManager() {
        firebase = new Firebase(FIREBASE_URL);
    }

    public static FirebaseManager instance() {
        if (firebaseManager == null) {
            firebaseManager = new FirebaseManager();
        }
        return firebaseManager;
    }




    public Firebase getFirebase() {
        return firebase;
    }
    public Firebase createChild(String childName) {
        return this.firebase.child(childName);
    }
    public Firebase generateFirebase(String name) {
        return new Firebase(FIREBASE_URL + name);
    }

    public String errorMessage(FirebaseError msg){
        String message = msg.toString().substring(msg.toString().indexOf(" "));

        return message;
    }


    }



