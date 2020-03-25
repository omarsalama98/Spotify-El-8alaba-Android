package com.vnoders.spotify_el8alaba;


import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeMainListItem;
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import java.util.ArrayList;
import java.util.List;

/**
 * just a mock class to get a list of track id's that have preview urls to use to play
 */
public class Mock {


    public static List<String> getTracksID() {
        List<String> songIds = new ArrayList<>();

        songIds.add("6f83eZ9DQW3W9M3grPDRmx");
        songIds.add("6NnaFwRgdbXdPBsgcOXkwA");
        songIds.add("2DTQ2zskGJJWyAnAppIpzN");
        songIds.add("3A4FRzgve9BjfKbvVXRIFO");
        songIds.add("1XnP7VphwaAl9aWRNHAESn");
        songIds.add("0omCpeIeTfzTS3YXZwMwg6");
        songIds.add("1lTfD2M6UpJRWi3YLb8qb5");
        songIds.add("1f6HC5dTviPmopQjYi4uVT");
        songIds.add("6c6fQ8k4yIUnLJEPy7zj3z");
        songIds.add("6IVM1YYZq6K6NG6qqC26o4");

        return songIds;
    }

    public static ArrayList<HomeMainListItem> getMainHomeList() {

        ArrayList<HomeInnerListItem> innerListItems;
        ArrayList<HomeMainListItem> mainListItems;

        mainListItems = new ArrayList<>();
        innerListItems = new ArrayList<>();

        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f0000000265af49474d91827160b56b27"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f0000000265af49474d91827160b56b27"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f0000000265af49474d91827160b56b27"));

        mainListItems.add(new HomeMainListItem("A7la sho8l", innerListItems));
        mainListItems.add(new HomeMainListItem("A7la kalam", innerListItems));
        mainListItems.add(new HomeMainListItem("zeft yaba", innerListItems));

        return mainListItems;
    }

    public static ArrayList<SearchListItem> getMockSearchData() {

        ArrayList<SearchListItem> myList = new ArrayList<>();
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("DD xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));

        return myList;
    }


}