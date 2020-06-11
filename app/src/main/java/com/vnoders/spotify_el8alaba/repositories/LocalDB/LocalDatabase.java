package com.vnoders.spotify_el8alaba.repositories.LocalDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * An abstract class dor implementing room database.
 */
@Database(entities = {RecentSearches.class}, version = 4)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract RecentSearchesDao recentSearchesDao();
}