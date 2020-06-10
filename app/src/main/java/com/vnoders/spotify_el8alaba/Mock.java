package com.vnoders.spotify_el8alaba;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.fragment.app.Fragment;
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.Genre;
import com.vnoders.spotify_el8alaba.models.Home.HomePlaylist;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchPlaylist;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.models.library.ArtistUserInfo;
import java.util.ArrayList;

/**
 * just a mock class to get a list of track id's that have preview urls to use to play
 */
public class Mock {

    public static ArrayList<Category> getHomeList() {

        ArrayList<HomePlaylist> innerListItems;
        ArrayList<Category> mainListItems;

        mainListItems = new ArrayList<>();
        innerListItems = new ArrayList<>();
        ArrayList<TrackImage> images = new ArrayList<>();
        images.add(
                new TrackImage("https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));

        innerListItems.add(new HomePlaylist("Akpa", "Akpro",
                images
                , "194"));
        innerListItems.add(new HomePlaylist("Akpa", "Akpro",
                images
                , "194"));
        innerListItems.add(new HomePlaylist("Akpa", "Akpro",
                images
                , "194"));
        innerListItems.add(new HomePlaylist("Akpa", "Akpro",
                images
                , "194"));
        innerListItems.add(new HomePlaylist("Akpa", "Akpro",
                images
                , "194"));

        mainListItems.add(new Category(innerListItems, "A7la sho8l"));

        ArrayList<HomePlaylist> innerListItems2;
        innerListItems2 = new ArrayList<>();
        ArrayList<TrackImage> images2 = new ArrayList<>();
        images2.add(
                new TrackImage("https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));

        innerListItems2.add(new HomePlaylist("Akpan", "Akpro",
                images2
                , "194"));
        innerListItems2.add(new HomePlaylist("Akpan", "Akpro",
                images2
                , "194"));
        innerListItems2.add(new HomePlaylist("Akpan", "Akpro",
                images2
                , "194"));
        innerListItems2.add(new HomePlaylist("Akpan", "Akpro",
                images2
                , "194"));
        innerListItems2.add(new HomePlaylist("Akpa", "Akpro",
                images2
                , "194"));

        mainListItems.add(new Category(innerListItems2, "A7la sho8l2"));
        mainListItems.add(new Category(innerListItems, "A7la sho8l3"));
        mainListItems.add(new Category(innerListItems, "A7la sho8l4"));

        ArrayList<HomePlaylist> innerListItems3;
        innerListItems3 = new ArrayList<>();
        ArrayList<TrackImage> images3 = new ArrayList<>();
        images3.add(
                new TrackImage("https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));

        innerListItems3.add(new HomePlaylist("Akpass", "Akpro",
                images3
                , "194"));
        innerListItems3.add(new HomePlaylist("Akpass", "Akpro",
                images3
                , "194"));
        innerListItems3.add(new HomePlaylist("Akpass", "Akpro",
                images3
                , "194"));
        innerListItems3.add(new HomePlaylist("Akpass", "Akpro",
                images3
                , "194"));
        innerListItems3.add(new HomePlaylist("Akpa", "Akpro",
                images3
                , "194"));
        mainListItems.add(new Category(innerListItems3, "A7la sho8l5"));

        return mainListItems;
    }

    public static ArrayList<SearchListItem> getMockSearchData() {

        ArrayList<SearchListItem> myList = new ArrayList<>();
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL2 xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("DD xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOLAT xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("ROFL", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("Song", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "Rar",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));

        return myList;
    }

    public static ArrayList<Genre> getTopGenres(Fragment fragment) {

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

    public static ArrayList<Genre> getAllGenres(Fragment fragment) {

        Bitmap bitmap = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.bika);
        Bitmap bitmap2 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.beach);
        Bitmap bitmap3 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.siu);
        Bitmap bitmap4 = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.sii);
        Bitmap bitmap5 = BitmapFactory
                .decodeResource(fragment.getResources(), R.drawable.sestoelemento);

        ArrayList<Genre> browseAllGenresList = new ArrayList<>();

        /*
        try {
        browseAllGenresList.add(new Genre(Picasso.get()
                .load("https://t.scdn.co/images/acc7b5d7b1264d0593ec05c020d0a689.jpeg").get(),
                "New Releases"));

        browseAllGenresList.add(new Genre(Picasso.get()
                .load("https://t.scdn.co/images/4b7472015a274eadbc00119f5141e548.jpeg").get()
                , "Charts"));

        browseAllGenresList.add(new Genre(Picasso.get()
                .load("https://t.scdn.co/images/4b7472015a274eadbc00119f5141e548.jpeg").get(),
                "Concerts"));

        browseAllGenresList.add(new Genre(Picasso.get()
                .load("https://t.scdn.co/images/4b7472015a274eadbc00119f5141e548.jpeg").get(),
                "At Home"));

        browseAllGenresList.add(new Genre(Picasso.get()
                    .load("https://t.scdn.co/images/68433b0ee5b5465b8e926c42b84cbcdb.jpeg").get(),
                    "Made for You"));

        } catch (IOException e) {
            e.printStackTrace();
        }*/

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

    public static ArrayList<Object> getRecentlyPlayed() {
        ArrayList<Object> recentlyPlayed = new ArrayList<>();

        ArrayList<Image> images = new ArrayList<>();
        images.add(
                new Image("https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        ArtistUserInfo artistUserInfo = new ArtistUserInfo(images);
        recentlyPlayed.add(new SearchArtist("Hamo Bika", artistUserInfo, "07775000"));
        recentlyPlayed.add(new SearchArtist("Hamo Bika", artistUserInfo, "07775006"));

        ArrayList<TrackImage> image = new ArrayList<>();
        image.add(
                new TrackImage("https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        recentlyPlayed.add(new SearchAlbum("Koto Moto", image, "07775008"));
        recentlyPlayed.add(new SearchAlbum("Koto Moto", image, "07775004"));
        recentlyPlayed.add(new SearchPlaylist("Hamo Bika", image, "07775003"));
        recentlyPlayed.add(new SearchPlaylist("Hamo Bika", image, "07775002"));

        return recentlyPlayed;
    }


}