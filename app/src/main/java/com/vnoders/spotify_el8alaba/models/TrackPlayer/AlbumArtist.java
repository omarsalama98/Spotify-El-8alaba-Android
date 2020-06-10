package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * Class to turn the JSON from the get track endpoint of backend to POJO
 */
public class AlbumArtist {

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("id")
    @Expose
    private String mId;

    public String getName() {
        return this.mName;
    }

    public String getId() {
        return this.mId;
    }

}
