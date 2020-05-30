package com.vnoders.spotify_el8alaba.models.userProfile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * Used to parse data from backend and get the playlists of user
 */
public class GetUsersPlaylists {

    @SerializedName("items")
    private List<UserPlaylistItem> mItems;

    public List<UserPlaylistItem> getItems() {
        return this.mItems;
    }

}
