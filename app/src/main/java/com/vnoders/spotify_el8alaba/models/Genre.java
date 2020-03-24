package com.vnoders.spotify_el8alaba.models;

import android.graphics.Bitmap;

public class Genre {

    private Bitmap imageBitmap;
    private String Title;

    public Genre(Bitmap imageBitmap, String title) {
        this.imageBitmap = imageBitmap;
        Title = title;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
