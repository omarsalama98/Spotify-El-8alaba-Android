package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

public class FollowItem {


    private String mUserName;
    private String mFollowersNumber;
    private int mUserImageResource;

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

    public int getmUserImageResource() {
        return mUserImageResource;
    }

    public void setmUserImageResource(int mUserImageResource) {
        this.mUserImageResource = mUserImageResource;
    }




    public FollowItem(String mUserName, String mFollowersNumber, int mUserImageResource) {
        this.mUserName = mUserName;
        this.mFollowersNumber = mFollowersNumber;
        this.mUserImageResource = mUserImageResource;
    }
}
