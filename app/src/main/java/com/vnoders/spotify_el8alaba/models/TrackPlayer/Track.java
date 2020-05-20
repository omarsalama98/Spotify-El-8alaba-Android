package com.vnoders.spotify_el8alaba.models.TrackPlayer;

/**
 * @author Ali Adel
 * This is the track representor class that the track player module uses as a track that
 * gets played and has all data about the track to display
 */
public class Track {

    // Constants to use by the mType variable to know what kind of context the track is being
    // played in
    public static final String TYPE_ALBUM = "PLAYING_ALBUM";
    public static final String TYPE_PLAYLIST = "PLAYING_PLAYLIST";
    public static final String TYPE_ARTIST = "PLAYING_ARTIST";

    // ID of track
    private String mId;

    // Name of track
    private String mName;

    // Track duration
    private int mDuration;

    // artist name of track
    private String mArtistName;

    // type of track takes only 3 values: TYPE_ALBUM, TYPE_PLAYLIST, TYPE_ARTIST
    private String mType;

    // If type is album then name of album, if type of playlist then name of playlist
    // if type is artist then name of artist
    private String mTypeName;

    // URL of image to display
    private String mImage;

    // ID of artist
    private String mArtistId;

    // ID of album
    private String mAlbumId;

    // context URI to tell backend what context is being played right now
    private String mContextUri;

    // boolean to know if there is a next or not for UI
    private boolean mHasNext = false;

    // boolean to know if there is a previous or not for UI
    private boolean mHasPrev = false;

    // know if song is being currently played or not
    private boolean mIsPlaying = false;

    /**
     * Constructor to fill all data about the track
     * @param id to set the mId
     * @param name  to set the mName
     * @param duration  to set the mDuration
     * @param artistName    to set the mArtistName
     * @param type  to set the mType
     * @param typeName  to set the mTypeName
     * @param image to set the mImage
     * @param artistId  to set the mArtistId
     * @param albumId   to set the mAlbumId
     * @param contextUri    to set the mContextUri
     */
    public Track(String id, String name, int duration, String artistName, String type, String typeName, String image, String artistId, String albumId, String contextUri) {
        this.setId(id);
        this.setName(name);
        this.setDuration(duration);
        this.setArtistName(artistName);
        this.setType(type);
        this.setTypeName(typeName);
        this.setImage(image);
        this.setArtistId(artistId);
        this.setAlbumId(albumId);
        this.setContextUri(contextUri);
    }

    /**
     * Setters for all member variables
     */

    public void setContextUri(String contextUri) {
        this.mContextUri = contextUri;
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

    public void setHasNext(boolean hasNext) {
        this.mHasNext = hasNext;
    }

    public void setHasPrev(boolean hasPrev) {
        this.mHasPrev = hasPrev;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.mIsPlaying = isPlaying;
    }

    public void setArtistId(String artistId) {
        this.mArtistId = artistId;
    }

    public void setAlbumId(String albumId) {
        this.mAlbumId = albumId;
    }


    /**
     * Getters for all member variables
     */

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

    public String getAlbumId() {
        return this.mAlbumId;
    }

    public String getArtistId() {
        return this.mArtistId;
    }

    public String getContextUri() {
        return this.mContextUri;
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

}
