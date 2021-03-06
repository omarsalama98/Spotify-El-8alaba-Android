package com.vnoders.spotify_el8alaba.repositories.LocalDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

/**
 * A Data Access object interface for room database implementation.
 */
@Dao
public interface RecentSearchesDao {

    @Query("SELECT * FROM RecentSearches")
    List<RecentSearches> getAll();

    @Insert
    void insertAll(RecentSearches... recentSearches);

    @Delete
    void delete(RecentSearches recentSearches);

    @Query("DELETE FROM RecentSearches")
    void nukeTable();

}