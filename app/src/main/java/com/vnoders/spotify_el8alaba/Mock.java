package com.vnoders.spotify_el8alaba;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.fragment.app.Fragment;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeMainListItem;
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import com.vnoders.spotify_el8alaba.models.Genre;
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

    static ArrayList<Genre> getTopGenres(Fragment fragment) {

        Bitmap bitmap = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.bika);
        Bitmap bitmap6 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.bugatti);
        Bitmap bitmap7 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.tesla);

        ArrayList<Genre> topGenresList = new ArrayList<>();
        topGenresList.add(new Genre(bitmap, "Sha3by"));
        topGenresList.add(new Genre(bitmap7, "Sha3by"));
        topGenresList.add(new Genre(bitmap7, "Sha3by"));
        topGenresList.add(new Genre(bitmap6, "Sha3by"));

        return topGenresList;
    }

    static ArrayList<Genre> getAllGenres(Fragment fragment) {

        Bitmap bitmap = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.bika);
        Bitmap bitmap2 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.beach);
        Bitmap bitmap3 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.siu);
        Bitmap bitmap4 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.sii);
        Bitmap bitmap5 = BitmapFactory
                .decodeResource(fragment.getResources(), R.drawable.sestoelemento);

        ArrayList<Genre> browseAllGenresList = new ArrayList<>();

        browseAllGenresList.add(new Genre(bitmap4, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap5, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap3, "Football"));
        browseAllGenresList.add(new Genre(bitmap3, "Football"));
        browseAllGenresList.add(new Genre(bitmap3, "Football"));
        browseAllGenresList.add(new Genre(bitmap4, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap4, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap5, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap5, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));

        return browseAllGenresList;
    }


}