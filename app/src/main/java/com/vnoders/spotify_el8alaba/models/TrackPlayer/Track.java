package com.vnoders.spotify_el8alaba.models.TrackPlayer;


public class Track {

    public static final String TYPE_ALBUM = "PLAYING_ALBUM";
    public static final String TYPE_PLAYLIST = "PLAYING_PLAYLIST";
    public static final String TYPE_ARTIST = "PLAYING_ARTIST";

    private String mId;

    private String mName;

    private int mDuration;

    private String mArtistName;

    private String mType;

    private String mTypeName;

    private String mImage;

    private boolean mHasNext = false;
    private boolean mHasPrev = false;
    private boolean mIsPlaying = false;

    public Track(String id, String name, int duration, String artistName, String type, String typeName, String image) {
        this.setId(id);
        this.setName(name);
        this.setDuration(duration);
        this.setArtistName(artistName);
        this.setType(type);
        this.setTypeName(typeName);
        this.setImage(image);
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

    public String getType() {
        return this.mType;
    }

    public String getTypeName() {
        return this.mTypeName;
    }

    public String getImage() {
        return this.mImage;
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

    public void setType(String type) {
        this.mType = type;
    }

    public void setTypeName(String typeName) {
        this.mTypeName = typeName;
    }

    public void setImage(String image) {
        this.mImage = image;
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
