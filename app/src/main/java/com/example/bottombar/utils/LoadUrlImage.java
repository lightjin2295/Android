package com.example.bottombar.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class LoadUrlImage extends AsyncTask<String, Void, Bitmap> {
    ImageView img_view;
    Bitmap bitmap_result;
    public LoadUrlImage(ImageView iv_result){
        this.img_view = iv_result;
    }

    protected Bitmap doInBackground(String... strings) {
        String uri_link = strings[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(uri_link).openStream();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }


    protected void onPostExecute(Bitmap result) {
        bitmap_result = result;
        String TAG = "Image";
        Log.i(TAG, "Height :" + result.getHeight());
        Log.i(TAG, "Width :" + result.getWidth());
        img_view.setImageBitmap(result);

    }

    public Bitmap getBitmap_result() {
        return bitmap_result;
    }
}