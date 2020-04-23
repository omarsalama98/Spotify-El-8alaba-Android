package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GenreFragmentTest {

    private String genreName = "Chill";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void loadingFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(SearchByTypeConstantsHelper.GENRE_NAME_KEY, genreName);
        Fragment fragment = new GenreFragment();
        fragment.setArguments(bundle);
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, null)
                .commit();
    }

    @Test
    public void checkViews() {

        onView(withId(R.id.genre_fragment_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_top_title)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_scroll_view)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_main_title)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_main_title)).check(matches(withText(genreName)));
        onView(withId(R.id.genre_playlists_grid_view)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_see_more_button))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

    }

    @Test
    public void playlistsGridItemClick() {
        onView(withId(R.id.genre_playlists_grid_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //TODO: Check playlists fragment

        pressBack();
    }
}