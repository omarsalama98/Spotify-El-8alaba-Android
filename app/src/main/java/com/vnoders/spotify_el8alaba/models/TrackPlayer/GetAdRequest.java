package com.vnoders.spotify_el8alaba.models.TrackPlayer;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * Used to get an ad from backend and convert JSON to POJO
 */
public class GetAdRequest {

    @SerializedName("ad")
    @Expose
    private AdItem mAdItem;

    public AdItem getItem() {
        return this.mAdItem;
    }

}
