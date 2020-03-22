package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.vnoders.spotify_el8alaba.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the
 * sections/tabs/pages.
 */
public class LibraryPagerAdapter extends FragmentPagerAdapter {

//    @StringRes
    private static final int[] TAB_TITLES = {R.string.title_library_playlists, R.string.title_library_artists, R.string.title_library_albums};
//    private static final String[] TAB_TITLES = {"R.string.Playlists", "R.string.Artists"," R.string.Albums"};

    private static final Fragment[] fragments = {new PlaylistFragment() , new ArtistFragment() , new AlbumFragment()};

    private final Context context;

    public LibraryPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return fragments[position];
//        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
//        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}