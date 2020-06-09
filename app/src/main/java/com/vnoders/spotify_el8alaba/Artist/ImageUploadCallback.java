package com.vnoders.spotify_el8alaba.Artist;

public interface ImageUploadCallback {

    void onProgressUpdate(int percentage);

    void onError(String message);

    void onSuccess(String message);
}