package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSeveralTracks {

    @SerializedName("Tracks")
    private List<GetTrack> mTracks;

    public List<GetTrack> getTracks() {
        return this.mTracks;
    }

}
