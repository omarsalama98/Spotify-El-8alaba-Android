package com.vnoders.spotify_el8alaba.models.TrackPlayer;


public class Track {

    private String mId;

    private String mName;

    private int mDuration;

    private String mArtistName;

    private boolean mHasNext = false;
    private boolean mHasPrev = false;
    private boolean mIsPlaying = false;

    public Track(String id, String name, int duration, String artistName) {
        this.setId(id);
        this.setName(name);
        this.setDuration(duration);
        this.setArtistName(artistName);
    }

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public String getArtistName() {
        return this.mArtistName;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setArtistName(String artistName) {
        this.mArtistName = artistName;
    }

    public boolean getHasNext() {
        return this.mHasNext;
    }

    public boolean getHasPrev() {
        return this.mHasPrev;
    }

    public boolean getIsPlaying() {
        return this.mIsPlaying;
    }

    public void setHasNext(boolean hasNext) {
        this.mHasNext = hasNext;
    }

    public void setHasPrev(boolean hasPrev) {
        this.mHasPrev = hasPrev;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.mIsPlaying = isPlaying;
    }


}
