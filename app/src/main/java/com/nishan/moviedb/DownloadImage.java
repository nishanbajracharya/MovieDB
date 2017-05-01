package com.nishan.moviedb;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;

class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private RelativeLayout layout = null;

    public DownloadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    public DownloadImage(ImageView imageView, RelativeLayout layout) {
        this.imageView = imageView;
        this.layout = layout;
    }

    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);

        if(layout != null) {
            Drawable bg = imageView.getDrawable();
            layout.setBackground(bg);
        }
    }
}