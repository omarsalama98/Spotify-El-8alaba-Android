package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class LibraryAlbumItem {

    @SerializedName("album")
    @Expose
    private LibraryAlbum album;

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public LibraryAlbum getAlbum() {
        return album;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LibraryAlbumItem that = (LibraryAlbumItem) obj;

        return Objects.equals(id, that.id) &&
                Objects.equals(album, that.album);
    }

}
