package com.vnoders.spotify_el8alaba.ui.library;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.vnoders.spotify_el8alaba.R;

/**
 * This class that returns a fragment corresponding to one of the tabs in the user library {@link
 * LibraryFragment}
 * The returned fragments' names are saved in the array {@link #TAB_TITLES}
 * The returned fragments are saved in the array {@link #fragments}
 */
public class LibraryPagerAdapter extends FragmentStateAdapter {

    static final int[] TAB_TITLES = {R.string.title_library_playlists, R.string.title_library_artists, R.string.title_library_albums};

    private static final Fragment[] fragments = {new LibraryPlaylistFragment(),
            new ArtistFragment(), new AlbumFragment()};

    /**
     * @param fragmentActivity The {@link FragmentActivity} which contains the {@link Fragment} in
     *                         which the {@link ViewPager2} lives directly in. In our case the
     *                         Fragment is {@link LibraryFragment} and the FragmentActivity is
     *                         {@link LibraryFragment#getActivity()}
     */
    public LibraryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * @param position the position of the required fragment. i.e. the tab number
     *
     * @return Fragment associated with the specified position
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    /**
     * @return The total number of items in this adapter. i.e the number of tabs
     */
    @Override
    public int getItemCount() {
        return fragments.length;
    }

}