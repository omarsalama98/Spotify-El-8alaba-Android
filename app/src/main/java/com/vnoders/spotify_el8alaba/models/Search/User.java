package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Image;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class User {

    @SerializedName("image")
    @Expose
    private List<Image> images;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public List<Image> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}