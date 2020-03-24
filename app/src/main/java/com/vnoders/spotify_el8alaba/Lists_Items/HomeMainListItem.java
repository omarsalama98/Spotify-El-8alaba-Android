package com.vnoders.spotify_el8alaba.Lists_Items;

import java.util.ArrayList;

public class HomeMainListItem {

    private String Title;
    private ArrayList<HomeInnerListItem> innerListItems;

    public HomeMainListItem(String title,
            ArrayList<HomeInnerListItem> innerListItems) {
        Title = title;
        this.innerListItems = innerListItems;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<HomeInnerListItem> getInnerListItems() {
        return innerListItems;
    }

    public void setInnerListItems(
            ArrayList<HomeInnerListItem> innerListItems) {
        this.innerListItems = innerListItems;
    }

}
