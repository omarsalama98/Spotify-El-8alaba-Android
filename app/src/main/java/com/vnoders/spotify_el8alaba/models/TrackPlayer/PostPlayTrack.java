package com.vnoders.spotify_el8alaba.models.TrackPlayer;

/**
 * @author Ali Adel
 * To send the data about the track to play to backend API
 */
public class PostPlayTrack {

    private String trackId;

    private String context_uri;

    public PostPlayTrack(String trackId, String context_uri) {
        this.setTrackId(trackId);
        this.setContextUri(context_uri);
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public void setContextUri(String context_uri) {
        this.context_uri = context_uri;
    }

    public String getTrackId() {
        return this.trackId;
    }

    public String getContext_uri() {
        return this.context_uri;
    }
}
