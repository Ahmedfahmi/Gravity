package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedfahmi.gravity.managers.FirebaseManager;
import com.ahmedfahmi.gravity.managers.ImagesManager;
import com.ahmedfahmi.gravity.managers.UserProfileManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {


    private String email;
    private String firstName;
    private String userOnlineUrl;

    private ImageView profilePic;
    private ListView photosList;
    private TextView tvUserName;

    private Bundle extractLoginData;

    private Intent intentOfMedia;

    private ImagesManager imagesManager;
    private UserProfileManager userProfileManager;
    private FirebaseManager firebaseManager;

    private Firebase userDataFirstName;
    private Firebase picUrl;
    private Firebase photosUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);
        Firebase.setAndroidContext(this);
        intiate();
        prepareFirebase();


        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, photosUrl) {
            @Override
            protected void populateView(View view, String base64Image, int i) {

                //byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                // Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                //((ImageView) view.findViewById(R.id.imageView1)).setImageBitmap(bitmap);
                ((TextView) view.findViewById(android.R.id.text1)).setText(base64Image);


            }
        };
        photosList.setAdapter(firebaseListAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.i("hob", userOnlineUrl);

       /* if (userProfileManager.getProfilePic()!=null){
            profilePic.setImageBitmap(userProfileManager.getProfilePic());
            Log.i("pic","exsist");
        }else{
            Log.i("pic","null");
        }*/




        picUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    Bitmap bitmap = imagesManager.toBitmap(dataSnapshot);
                    profilePic.setImageBitmap(bitmap);

                    Log.i("C_", "nice");
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                Toast.makeText(getApplicationContext(), firebaseManager.errorMessage(firebaseError), Toast.LENGTH_LONG).show();

            }
        });


        userDataFirstName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstName = dataSnapshot.getValue().toString();
                String firstLetter = firstName.substring(0, 1).toUpperCase();
                String secondLetter = firstName.substring(1, firstName.length());
                String fullName = firstLetter + secondLetter;
                tvUserName.setText(fullName);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {


                Toast.makeText(getApplicationContext(), firebaseManager.errorMessage(firebaseError), Toast.LENGTH_LONG).show();

            }
        });


    }

    private void intiate() {
        photosList = (ListView) findViewById(R.id.photoList);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        tvUserName = (TextView) findViewById(R.id.profileUserName);
        extractLoginData = getIntent().getExtras();

        intentOfMedia = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagesManager = ImagesManager.getInstance();
        userProfileManager = UserProfileManager.getInstance();


        email = extractLoginData.getString("email");

        userOnlineUrl = email.substring(0, email.indexOf("@"));

        userProfileManager.setUserOnlineUrl(email);


    }

    private void prepareFirebase() {
        firebaseManager = FirebaseManager.instance();
        picUrl = firebaseManager.generateFirebase("profilePics/" + userOnlineUrl);
        photosUrl = firebaseManager.generateFirebase("photos/" + userOnlineUrl);
        userDataFirstName = firebaseManager.generateFirebase("User/" + userOnlineUrl + "/firstName");
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeProfilePic:
                startActivityForResult(intentOfMedia, 1);
                break;

            case R.id.explore:
                Intent intentOfUsersList = new Intent(getApplicationContext(), UsersListActivity.class);
                startActivity(intentOfUsersList);
                break;

            case R.id.addPhotos:
                startActivityForResult(intentOfMedia, 2);
                break;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            Bitmap selectedBitmap = null;
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String base64Image = imagesManager.convertBitmapToString(selectedBitmap);
            switch (requestCode) {
                case 1:
                    picUrl.setValue(base64Image);
                    break;
                case 2:
                    photosUrl.setValue(base64Image);
                    break;
            }


        }

    }



}


