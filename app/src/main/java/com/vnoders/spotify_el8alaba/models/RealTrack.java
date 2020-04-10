package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * Track object as returned from back end
 */
public class RealTrack {

    // id of track
    @SerializedName("_id")
    private String mId;

    // name of track
    @SerializedName("name")
    private String mName;

    // duration of track in MS
    @SerializedName("duration_ms")
    private int mDuration;

    // artists who made the track
    @SerializedName("artists")
    private List<RealArtist> mArtists;

    // if track has next or not for UI purposes
    private boolean mHasNext = false;
    // if track has previous or not for UI purposes
    private boolean mHasPrev = false;
    // if track is playing right now or not for UI purposes
    private boolean mIsPlaying = false;

    /**
     * Constructor to user for testing
     * @param id of track
     * @param name of track
     * @param duration of track in MS
     * @param artists who made the track
     */
    public RealTrack(String id, String name, int duration, List<RealArtist> artists) {
        this.mId = id;
        this.mName = name;
        this.mDuration = duration;
        this.mArtists = artists;
    }

    /**
     * @return id of track object
     */
    public String getId() {
        return this.mId;
    }

    /**
     * @return name of track
     */
    public String getName() {
        return this.mName;
    }

    /**
     * @return duration of track in MS
     */
    public int getDuration() {
        return this.mDuration;
    }

    /**
     * @return list of artists who made the track
     */
    public List<RealArtist> getArtists() {
        return this.mArtists;
    }

    /**
     * @return true: there is next track
     *         false: there is no track after this one
     */
    public boolean getHasNext() {
        return this.mHasNext;
    }

    /**
     * @return true: there is a previous track
     *         false: there is no track before this one
     */
    public boolean getHasPrev() {
        return this.mHasPrev;
    }

    /**
     * @return true: track is currently playing
     *         false: track is currently paused
     */
    public boolean getIsPlaying() {
        return this.mIsPlaying;
    }

    /**
     * @param hasNext if there is next track or not
     */
    public void setHasNext(boolean hasNext) {
        this.mHasNext = hasNext;
    }

    /**
     * @param hasPrev if there is previous track or not
     */
    public void setHasPrev(boolean hasPrev) {
        this.mHasPrev = hasPrev;
    }

    /**
     * @param isPlaying if track is currently playing or not
     */
    public void setIsPlaying(boolean isPlaying) {
        this.mIsPlaying = isPlaying;
    }

    /**
     * @param duration of track to show in UI
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }
}
