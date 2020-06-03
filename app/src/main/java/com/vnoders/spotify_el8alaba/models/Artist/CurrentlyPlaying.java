package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentlyPlaying {

    @SerializedName("track")
    @Expose
    private String track;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("repeat_state")
    @Expose
    private Boolean repeatState;
    @SerializedName("shuffle_state")
    @Expose
    private Boolean shuffleState;
    @SerializedName("volume_percent")
    @Expose
    private Integer volumePercent;
    @SerializedName("is_playing")
    @Expose
    private Boolean isPlaying;
    @SerializedName("progress_ms")
    @Expose
    private Integer progressMs;
    @SerializedName("context")
    @Expose
    private Context context;

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getRepeatState() {
        return repeatState;
    }

    public void setRepeatState(Boolean repeatState) {
        this.repeatState = repeatState;
    }

    public Boolean getShuffleState() {
        return shuffleState;
    }

    public void setShuffleState(Boolean shuffleState) {
        this.shuffleState = shuffleState;
    }

    public Integer getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(Integer volumePercent) {
        this.volumePercent = volumePercent;
    }

    public Boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(Boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public Integer getProgressMs() {
        return progressMs;
    }

    public void setProgressMs(Integer progressMs) {
        this.progressMs = progressMs;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}