package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FollowArtistRequestBody {

    @SerializedName("ids")
    @Expose
    private List<String> ids;

    public FollowArtistRequestBody(List<String> ids) {
        this.ids = ids;
    }
}
