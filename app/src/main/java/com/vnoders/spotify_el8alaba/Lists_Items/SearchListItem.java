package com.vnoders.spotify_el8alaba.Lists_Items;

public class SearchListItem {

    private String name;
    private String info;
    private String imageURL;

    public SearchListItem(String name, String info, String imageURL) {
        this.name = name;
        this.info = info;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getImageURL() {
        return imageURL;
    }
}
