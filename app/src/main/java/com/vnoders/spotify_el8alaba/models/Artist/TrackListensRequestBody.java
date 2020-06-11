package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class TrackListensRequestBody {

    @SerializedName("ids")
    @Expose
    private List<String> ids;

    @SerializedName("period")
    @Expose
    private String period;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;


    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
