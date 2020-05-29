package com.vnoders.spotify_el8alaba.models.Artist;

public class MyTrack {

    private String id;
    private int streams;
    private String name;

    public MyTrack(String id, int streams) {
        this.id = id;
        this.streams = streams;
        this.name = "";
    }

    public int getStreams() {
        return streams;
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
}
