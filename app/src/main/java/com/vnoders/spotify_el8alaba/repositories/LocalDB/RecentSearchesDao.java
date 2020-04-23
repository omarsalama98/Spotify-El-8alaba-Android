package com.vnoders.spotify_el8alaba.repositories.LocalDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecentSearchesDao {

    @Query("SELECT * FROM RecentSearches")
    List<RecentSearches> getAll();

    @Insert
    void insertAll(RecentSearches... recentSearches);

    @Delete
    void delete(RecentSearches recentSearches);
}