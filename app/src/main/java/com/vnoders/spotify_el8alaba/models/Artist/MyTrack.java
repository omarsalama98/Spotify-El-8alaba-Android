package com.vnoders.spotify_el8alaba.models.Artist;

public class MyTrack {

    private String id;
    private int streams;
    private String name;

    public MyTrack(String id, int streams) {
        this.id = id;
        this.streams = streams;
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

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof MyTrack)) {
            return false;
        }

        MyTrack obj = (MyTrack) o;
        return obj.getName().equals(name) && obj.getId().equals(id);
    }
}
