package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;


/**
 * for json parsing to wrap tracks in list when using the endpoint get tracks
 */
public class TrackList {

    @SerializedName("tracks")
    private List<PlayableTrack> tracks;

    public List<PlayableTrack> getTracks() {
        return tracks;
    }
}
