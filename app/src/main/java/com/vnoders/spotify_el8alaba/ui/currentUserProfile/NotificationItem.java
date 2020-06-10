package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

/**
 * @author Mohamed Samy
 * This class containing notification data like notification bode and title and the date which the event had been occured.
 */
public class NotificationItem {

    public NotificationItem(String mTitle, String mBody, String mDate) {
        this.mTitle = mTitle;
        this.mBody = mBody;
        this.mDate = mDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmBody() {
        return mBody;
    }

    public void setmBody(String mBody) {
        this.mBody = mBody;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    private String mTitle;
    private String mBody;
    private String mDate;

}
