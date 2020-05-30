package com.vnoders.spotify_el8alaba.models.userProfile;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.AlbumImage;

import java.util.List;

/**
 * @author Ali Adel
 * Used to parse data from backend and get the playlists of user
 */
public class UserPlaylistItem {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("images")
    private List<AlbumImage> mImages;

    @SerializedName("owner")
    private UserPlaylistOwner mOwner;

    @SerializedName("description")
    private String mDescription;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public List<AlbumImage> getImages() {
        return this.mImages;
    }

    public UserPlaylistOwner getOwner() {
        return this.mOwner;
    }

    public String getDescription() {
        if (TextUtils.isEmpty(this.mDescription))
            return "No Description Found!";

        return this.mDescription;
    }
}
