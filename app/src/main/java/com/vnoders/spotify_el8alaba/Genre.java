package com.vnoders.spotify_el8alaba;

import android.graphics.Bitmap;

public class Genre {

    private Bitmap image;
    private String title;

    public Genre(Bitmap image, String title) {
        this.image = image;
        this.title = title;
    }

    public Bitmap getImageBitmap() {
        return image;
    }

    public void setImageBitmap(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
