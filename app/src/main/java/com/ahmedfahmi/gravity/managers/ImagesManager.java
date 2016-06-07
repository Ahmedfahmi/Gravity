package com.ahmedfahmi.gravity.managers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.ahmedfahmi.gravity.Extra.RoundedImageView;
import com.firebase.client.DataSnapshot;

import java.io.ByteArrayOutputStream;

/**
 * Created by Ahmed Fahmi on 6/1/2016.
 */
public class ImagesManager {
    private static ImagesManager imagesManager;

    public static ImagesManager getInstance() {
        if (imagesManager == null) {
            imagesManager = new ImagesManager();
        }
        return imagesManager;
    }

    private ImagesManager() {


    }


    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Bitmap toBitmap(DataSnapshot dataSnapshot) {
        String picture = dataSnapshot.getValue().toString();
        byte[] imageAsBytes = Base64.decode(picture.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        return bitmap;
    }

    public Bitmap toBitmap(String string) {

        byte[] imageAsBytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        return bitmap;
    }

}
