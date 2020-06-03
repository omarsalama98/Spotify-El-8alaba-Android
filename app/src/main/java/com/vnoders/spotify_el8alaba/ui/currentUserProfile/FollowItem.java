package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

public class FollowItem {


    private String mUserName;
    private String mFollowersNumber;
    private String mImageURL;
    private String mid;
    private String mType;

    public FollowItem(String mUserName, String mFollowersNumber, String mImageURL, String mid,
            String mType) {
        this.mUserName = mUserName;
        this.mFollowersNumber = mFollowersNumber;
        this.mImageURL = mImageURL;
        this.mid = mid;
        this.mType = mType;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmFollowersNumber() {
        return mFollowersNumber;
    }

    public void setmFollowersNumber(String mFollowersNumber) {
        this.mFollowersNumber = mFollowersNumber;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
