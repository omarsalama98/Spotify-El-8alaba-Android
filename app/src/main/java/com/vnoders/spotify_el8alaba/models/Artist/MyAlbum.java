package com.vnoders.spotify_el8alaba.models.Artist;

public class MyAlbum {

    private String id;
    private String name;
    private String imgUrl;
    private Boolean selected;

    public MyAlbum(String id) {
        this.id = id;
        this.name = "";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
