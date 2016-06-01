package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedfahmi.gravity.Extra.RoundedImageView;
import com.ahmedfahmi.gravity.managers.FirebaseManager;
import com.ahmedfahmi.gravity.managers.ImagesManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {
    private FirebaseManager firebaseManager;
    private Firebase picUrl;
    private String email;
    private ImageView profilePic;
    private String userOnlineUrl;
    private Bundle extractLoginData;
    private TextView tvUserName;
    private String firstName;
    private Firebase photosUrl;
    private Intent intentOfMedia;
    private ListView photoList;
    private ImagesManager imagesManager;


    @Override
    protected void onStart() {
        super.onStart();

        Log.i("hob", userOnlineUrl);


        picUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {



                    String picture = dataSnapshot.getValue().toString();
                    byte[] imageAsBytes = Base64.decode(picture.getBytes(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    profilePic.setImageBitmap(RoundedImageView.getCroppedBitmap(bitmap, 100));
                    Log.i("C_", "nice");
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                String msg = firebaseError.toString().substring(firebaseError.toString().indexOf(" "));
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            }
        });

        //https://fahmiphotos.firebaseio.com/User/AhmedFahmi/firstName
        //Firebase usersData = new Firebase("https://fahmiphotos.firebaseio.com/User/AhmedFahmi/firstName");
        Firebase userDataFirstName = firebaseManager.generateFirebase("User/" + userOnlineUrl + "/firstName");


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

            }
        });


    }

    private void intiate() {
        extractLoginData = getIntent().getExtras();
        email = extractLoginData.getString("email");
        userOnlineUrl = email.substring(0, email.indexOf("@"));
        firebaseManager = FirebaseManager.instance();
        picUrl = firebaseManager.generateFirebase("profilePics/" + userOnlineUrl);
        photosUrl = firebaseManager.generateFirebase("photos/" + userOnlineUrl);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        tvUserName = (TextView) findViewById(R.id.profileUserName);
        intentOfMedia = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoList = (ListView) findViewById(R.id.photoList);
        imagesManager = ImagesManager.getInstance();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);
        Firebase.setAndroidContext(this);
        intiate();
        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, photosUrl) {
            @Override
            protected void populateView(View view, String base64Image, int i) {

                //byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                // Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                //((ImageView) view.findViewById(R.id.imageView1)).setImageBitmap(bitmap);
                ((TextView) view.findViewById(android.R.id.text1)).setText(base64Image);
                Log.i("Image", "datat cahe");

            }
        };
        photoList.setAdapter(firebaseListAdapter);


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
            Uri selectedImage = data.getData();


            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }


            String base64Image = imagesManager.convertBitmapToString(bitmap);
            if (requestCode == 1) {
                picUrl.setValue(base64Image);
            } else if (requestCode == 2) {
                photosUrl.setValue(base64Image);
            }


            Log.i("E_", email);

        }

    }

  
}


