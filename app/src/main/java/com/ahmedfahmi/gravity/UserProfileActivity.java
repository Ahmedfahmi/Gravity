package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedfahmi.gravity.Extra.Constants;
import com.ahmedfahmi.gravity.Extra.RoundedImageView;
import com.ahmedfahmi.gravity.managers.FirebaseManager;
import com.ahmedfahmi.gravity.managers.ImagesManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {


    private String email_Login;
    private String email_UsersList;
    private String firstName;
    private String userOnlineUrl;

    private ImageView profilePic;
    private ListView photosList;
    private TextView tvUserName;
    private ImageButton callBtn;


    private Bundle extractLoginActivityExtras;
    private Bundle extractUserListActivity;

    private Intent intentOfMedia;
    private Intent intentOfLoginActivity;
    private Intent intentOfUsersList;

    private ImagesManager imagesManager;

    private FirebaseManager firebaseManager;

    private Firebase userDataFirstName;
    private Firebase profilePicUrl;
    private Firebase photosUrl;
    private Firebase userMobileUrl;

    private String mobileNumber;

    private boolean menuDisabled = false;
    private boolean callingServiceDisabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);
        Firebase.setAndroidContext(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        intiate();
        prepareFirebase();
        menuDisabled = extractUserListActivity.getBoolean(Constants.MENU_DISABLED);
        callingServiceDisabled = extractUserListActivity.getBoolean(Constants.CALLING_SERVICE_STATUS);


        profilePicUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    Bitmap bitmap = imagesManager.toBitmap(dataSnapshot);
                    Bitmap roundedBitmap = RoundedImageView.getCroppedBitmap(bitmap, 100);
                    profilePic.setImageBitmap(roundedBitmap);

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


        userMobileUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mobileNumber = dataSnapshot.getValue().toString();
                if (mobileNumber != null && callingServiceDisabled) {
                    callBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), firebaseManager.errorMessage(firebaseError), Toast.LENGTH_LONG).show();

            }
        });


        FirebaseListAdapter<String> photosUrllistAdapter = new FirebaseListAdapter<String>(this, String.class, R.layout.image_list, photosUrl) {
            @Override
            protected void populateView(View view, String s, int i) {


                ImagesManager imagesManager = ImagesManager.getInstance();

                Bitmap bitmap = imagesManager.toBitmap(s);


                ((ImageView) view.findViewById(R.id.pics)).setImageBitmap(bitmap);


            }
        };
        photosList.setAdapter(photosUrllistAdapter);


    }

    private void intiate() {
        photosList = (ListView) findViewById(R.id.photoList);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        tvUserName = (TextView) findViewById(R.id.profileUserName);
        callBtn = (ImageButton) findViewById(R.id.callBtn);
        callBtn.setVisibility(View.INVISIBLE);
        extractLoginActivityExtras = getIntent().getExtras();
        extractUserListActivity = getIntent().getExtras();


        intentOfMedia = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentOfLoginActivity = new Intent(this, LoginActivity.class);
        intentOfUsersList = new Intent(getApplicationContext(), UsersListActivity.class);


        imagesManager = ImagesManager.getInstance();



        email_Login = extractLoginActivityExtras.getString(Constants.ACTIVE_EMAIL_EXTRA);
        email_UsersList = extractUserListActivity.getString(Constants.ACTIVE_EMAIL_EXTRA);
        if (email_Login.contains("@")) {
            userOnlineUrl = email_Login.substring(0, email_Login.indexOf("@")) + "/";

        } else {

            userOnlineUrl = email_UsersList + "/";

        }


    }

    private void prepareFirebase() {
        firebaseManager = FirebaseManager.instance();
        profilePicUrl = firebaseManager.generateFirebase(Constants.USER_PROFILE_PIC_URL + userOnlineUrl);
        photosUrl = firebaseManager.generateFirebase(Constants.PHOTOS_URL + userOnlineUrl);
        userDataFirstName = firebaseManager.generateFirebase(Constants.USER_URL + userOnlineUrl + Constants.USER_DATA_FIRSTNAME_URL);
        userMobileUrl = firebaseManager.generateFirebase(Constants.USER_URL + userOnlineUrl + Constants.USER_DATA_MOBILE_URL);


    }


    public boolean onCreateOptionsMenu(Menu menu) {

        if (!menuDisabled) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.profile_menu, menu);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeProfilePic:
                startActivityForResult(intentOfMedia, 1);
                break;

            case R.id.explore:
                intentOfUsersList.putExtra(Constants.ACTIVE_EMAIL_EXTRA, userOnlineUrl);

                startActivity(intentOfUsersList);
                break;

            case R.id.addPhotos:
                startActivityForResult(intentOfMedia, 2);
                break;

            case R.id.logout:
                startActivity(intentOfLoginActivity);


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
                    profilePicUrl.setValue(base64Image);
                    break;
                case 2:
                    photosUrl.push().setValue(base64Image);
                    break;
            }


        }

    }

    public void call(View view) {


        dialPhoneNumber(mobileNumber);


    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}


