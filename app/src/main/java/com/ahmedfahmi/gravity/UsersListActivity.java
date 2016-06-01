package com.ahmedfahmi.gravity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmedfahmi.gravity.Extra.RoundedImageView;
import com.ahmedfahmi.gravity.managers.FirebaseManager;
import com.ahmedfahmi.gravity.managers.ImagesManager;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

public class UsersListActivity extends AppCompatActivity {
    private FirebaseManager firebaseManager;
    private ListView usersList;
    private ImagesManager imagesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Firebase.setAndroidContext(this);


        firebaseManager = FirebaseManager.instance();
        usersList = (ListView) findViewById(R.id.usersListView);
        imagesManager = ImagesManager.getInstance();
        Firebase usersUrl = firebaseManager.generateFirebase("profilePics");


        FirebaseListAdapter firebaseListAdapter = new FirebaseListAdapter<String>(this, String.class, R.layout.users_list, usersUrl) {
            @Override
            protected void populateView(View view, String base64Image, int i) {
                Bitmap bitmap =imagesManager.toBitmap(base64Image);

                ((TextView) view.findViewById(R.id.textView1)).setText(getRef(i).getKey());


                //byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
               // Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);


                ((ImageView) view.findViewById(R.id.imageView1)).setImageBitmap(bitmap);


            }
        };

        usersList.setAdapter(firebaseListAdapter);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.toString();
                Log.i("T_", view.toString());
            }
        });
    }
}
