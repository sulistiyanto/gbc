package com.tiyanrcode.guestbookclient.configure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by sulistiyanto on 3/30/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;

    public  DownloadImageTask(ImageView bmImage){
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon = null;
        try {
            InputStream inputStream = new URL(urlDisplay).openStream();
            mIcon = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            Bitmap bmp2 = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            bmImage.setImageBitmap(bmp2);
        }
    }
}
