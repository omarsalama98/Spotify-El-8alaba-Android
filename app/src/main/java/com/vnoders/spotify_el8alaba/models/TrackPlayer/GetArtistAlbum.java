package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 */

/**
 * To turn the JSON to POJO from backend API
 */
public class GetArtistAlbum {

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("images")
    @Expose
    private List<AlbumImage> mImages;

    @SerializedName("id")
    @Expose
    private String mId;

    public String getName() {
        return this.mName;
    }

    public List<AlbumImage> getImages() {
        return this.mImages;
    }

    public String getId() {
        return this.mId;
    }

}
