package com.vnoders.spotify_el8alaba.models.userProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 */

/**
 * Used to parse data from backend and get the playlists of user
 */
public class UserPlaylistOwner {

    @SerializedName("id")
    @Expose
    private String mId;

    public String getId() {
        return this.mId;
    }

}
