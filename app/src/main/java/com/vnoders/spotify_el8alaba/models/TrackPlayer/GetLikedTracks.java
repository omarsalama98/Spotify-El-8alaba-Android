package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Ali Adel
 */

/**
 * Class to turn JSON into POJO received from backend
 */
public class GetLikedTracks {

    @SerializedName("items")
    @Expose
    List<LikedTrackWrapper> mLikedTracks;

    public List<LikedTrackWrapper> getLikedTracks() {
        return this.mLikedTracks;
    }
}
