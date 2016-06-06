package com.ahmedfahmi.gravity.managers;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmedfahmi.gravity.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Ahmed Fahmi on 6/1/2016.
 */
public class UserProfileManager {
    private static UserProfileManager userProfileManager;

    private String userOnlineUrl;
    private String email;

    private FirebaseManager firebaseManager;
    private ImagesManager imagesManager;

    private Firebase userDataFirstName;
    private Firebase picUrl;
    private Firebase photosUrl;

    private Bitmap profilePic;

    private ImageView profileImg;


    public void setUserOnlineUrl(String email) {
        this.email = email;
        this.userOnlineUrl = email.substring(0, email.indexOf("@"));
    }

    public void setUserOnlineUrl2(String email) {

        this.userOnlineUrl = email;
    }





    public static UserProfileManager getInstance() {
        if (userProfileManager == null) {
            userProfileManager = new UserProfileManager();
        }
        return userProfileManager;
    }

    private UserProfileManager() {
        prepareFirebase();
        imagesManager = ImagesManager.getInstance();

    }

    private void prepareFirebase() {
        firebaseManager = FirebaseManager.instance();
        picUrl = firebaseManager.generateFirebase("profilePics/" + userOnlineUrl);
        photosUrl = firebaseManager.generateFirebase("photos/" + userOnlineUrl);
        userDataFirstName = firebaseManager.generateFirebase("User/" + userOnlineUrl + "/firstName");
    }

  /*  public Bitmap getProfilePic() {
        picUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    profilePic = imagesManager.toBitmap(dataSnapshot);




                    Log.i("C_", "nice");

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                //Toast.makeText(getApplicationContext(), firebaseManager.errorMessage(firebaseError), Toast.LENGTH_LONG).show();

            }
        });
        if (profilePic!=null){
            return profilePic;
        }

return null;

    }*/


}
