package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class GetPlaylistItem {

    @SerializedName("track")
    private GetPlaylistTrack mTrack;

    public GetPlaylistTrack getTrack() {
        return this.mTrack;
    }
}
