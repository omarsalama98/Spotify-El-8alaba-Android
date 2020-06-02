package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class RequestBodyIds {

    @SerializedName("ids")
    @Expose
    private List<String> ids;

    public RequestBodyIds(List<String> ids) {
        this.ids = ids;
    }

    public RequestBodyIds(String id) {
        this.ids = Collections.singletonList(id);
    }
}
