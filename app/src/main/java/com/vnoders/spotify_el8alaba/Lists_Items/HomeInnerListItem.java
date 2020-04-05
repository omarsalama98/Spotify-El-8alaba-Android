package com.vnoders.spotify_el8alaba.Lists_Items;

public class HomeInnerListItem {

    private String title;
    private String subTitle;
    private String imageURL;

    public HomeInnerListItem(String title, String subTitle, String imageURL) {
        this.title = title;
        this.subTitle = subTitle;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
