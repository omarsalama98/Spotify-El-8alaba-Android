package com.vnoders.spotify_el8alaba.repositories.LocalDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RecentSearches {

    @PrimaryKey
    public int searchItemid;

    @ColumnInfo(name = "name")
    public String itemName;

    @ColumnInfo(name = "info")
    public String itemInfo;

    @ColumnInfo(name = "image_url")
    public String itemImageUrl;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof RecentSearches)) {
            return false;
        }

        RecentSearches obj = (RecentSearches) o;
        return obj.itemName.equals(itemName) && obj.itemInfo.equals(itemInfo);
    }
}