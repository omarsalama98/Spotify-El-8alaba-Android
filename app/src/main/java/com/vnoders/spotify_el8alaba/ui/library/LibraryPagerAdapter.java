package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.vnoders.spotify_el8alaba.R;
import org.jetbrains.annotations.NotNull;

/**
 * A [LibraryPagerAdapter] that returns a fragment corresponding to one of the
 * sections/tabs/pages.
 */
public class LibraryPagerAdapter extends FragmentStateAdapter {

    static final int[] TAB_TITLES = {R.string.title_library_playlists, R.string.title_library_artists, R.string.title_library_albums};

    private static final Fragment[] fragments = {new LibraryPlaylistFragment(),
            new ArtistFragment(), new AlbumFragment()};

    public LibraryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
//
//    LibraryPagerAdapter(@NonNull Fragment fragment) {
//        super(fragment);
//    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }



}