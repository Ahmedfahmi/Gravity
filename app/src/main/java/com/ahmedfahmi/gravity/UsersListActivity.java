package com.ahmedfahmi.gravity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    Firebase usersProfilePicUrl;
    private ListView usersList;
    private ImagesManager imagesManager;
    private FirebaseManager firebaseManager;
    private LinkedList<String> mails = new LinkedList<>();
    private Intent userProfileIntent;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Firebase.setAndroidContext(this);

        initiate();


        FirebaseListAdapter firebaseListAdapter = new FirebaseListAdapter<String>(this, String.class, R.layout.users_list, usersProfilePicUrl) {
            @Override
            protected void populateView(View view, String base64Image, int i) {
                Bitmap bitmap = imagesManager.toBitmap(base64Image);
                Bitmap roundedBitmap = RoundedImageView.getCroppedBitmap(bitmap, 100);

                ((TextView) view.findViewById(R.id.textView1)).setText(getRef(i).getKey());

                ((ImageView) view.findViewById(R.id.imageView1)).setImageBitmap(roundedBitmap);

                mails.add(i, getRef(i).getKey());

            }


        };

        usersList.setAdapter(firebaseListAdapter);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.toString();
                mails.get(position);
                userProfileIntent.putExtra(Constants.ACTIVE_EMAIL_EXTRA, mails.get(position));
                userProfileIntent.putExtra(Constants.MENU_DISABLED, true);
                userProfileIntent.putExtra(Constants.CALLING_SERVICE_STATUS, true);
                startActivity(userProfileIntent);

            }
        });
    }

    private void initiate() {
        userProfileIntent = new Intent(getApplicationContext(), UserProfileActivity.class);
        firebaseManager = FirebaseManager.instance();
        imagesManager = ImagesManager.getInstance();
        usersProfilePicUrl = firebaseManager.generateFirebase(Constants.USER_PROFILE_PIC_URL);
        usersList = (ListView) findViewById(R.id.usersListView);
    }
}
