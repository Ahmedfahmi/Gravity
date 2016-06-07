package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.ahmedfahmi.gravity.Extra.Constants;
import com.ahmedfahmi.gravity.Extra.RoundedImageView;
import com.ahmedfahmi.gravity.managers.FirebaseManager;
import com.ahmedfahmi.gravity.managers.ImagesManager;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import java.util.LinkedList;

public class UsersListActivity extends AppCompatActivity {
    public static final String MENU_DISABLED = "MENU_STATUS";
    private FirebaseManager firebaseManager;
    private ListView usersList;
    private ImagesManager imagesManager;
    private LinkedList<String> mails = new LinkedList<>();
    private Intent userProfileIntent;
    private Bundle extractUserProfileExtra;
    private String email;


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Firebase.setAndroidContext(this);


        userProfileIntent = new Intent(getApplicationContext(), UserProfileActivity.class);
        firebaseManager = FirebaseManager.instance();
        usersList = (ListView) findViewById(R.id.usersListView);
        imagesManager = ImagesManager.getInstance();
        Firebase usersUrl = firebaseManager.generateFirebase("profilePics");
        extractUserProfileExtra = getIntent().getExtras();
        email = extractUserProfileExtra.getString(Constants.ACTIVE_EMAIL_EXTRA);
        Log.e("mail", email);


        FirebaseListAdapter firebaseListAdapter = new FirebaseListAdapter<String>(this, String.class, R.layout.users_list, usersUrl) {
            @Override
            protected void populateView(View view, String base64Image, int i) {
                Bitmap bitmap = imagesManager.toBitmap(base64Image);
                Bitmap roundedBitmap = RoundedImageView.getCroppedBitmap(bitmap, 100);



                    ((TextView) view.findViewById(R.id.textView1)).setText(getRef(i).getKey());

                    ((ImageView) view.findViewById(R.id.imageView1)).setImageBitmap(roundedBitmap);
                mails.add(i, getRef(i).getKey());







                Log.e("E_", getRef(i).getKey() + Integer.toString(i));


            }


        };

        usersList.setAdapter(firebaseListAdapter);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.toString();
                mails.get(position);
                Log.i("T_", mails.get(position));
                userProfileIntent.putExtra(Constants.ACTIVE_EMAIL_EXTRA, mails.get(position));
                userProfileIntent.putExtra(MENU_DISABLED, true);
                startActivity(userProfileIntent);


            }
        });
    }
}
