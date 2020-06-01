package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * To be used to turn the JSON of several tracks to one object
 */
public class GetSeveralTracks {

    @SerializedName("Tracks")
    @Expose
    private List<GetTrack> mTracks;

    public List<GetTrack> getTracks() {
        return this.mTracks;
    }

}
