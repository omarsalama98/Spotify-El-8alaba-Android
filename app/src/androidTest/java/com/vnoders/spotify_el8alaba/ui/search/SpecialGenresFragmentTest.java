package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
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

public class SpecialGenresFragmentTest {

    private String genreName = "Salama";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void loadingFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(SearchByTypeConstantsHelper.GENRE_NAME_KEY, genreName);
        Fragment fragment = new SpecialGenresFragment();
        fragment.setArguments(bundle);
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, null)
                .commit();
    }

    @Test
    public void checkViews() {

        onView(withId(R.id.special_genre_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_top_title)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_scroll_view)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_main_title)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_top_playlists_recycler_view))
                .check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_categories_grid_view)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_categories_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.special_genre_popular_playlists_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

        onView(withId(R.id.special_genre_categories_text_view))
                .check(matches(withText(R.string.categories)));
        onView(withId(R.id.special_genre_popular_playlists_text_view))
                .check(matches(withText(R.string.popular_playlists)));
        onView(withId(R.id.special_genre_fragment_main_title)).check(matches(withText(genreName)));
        onView(withId(R.id.special_genre_fragment_scroll_view)).perform(swipeUp());

    }

    @Test
    public void playlistsListItemClick() {

        onView(withId(R.id.special_genre_top_playlists_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public void genresListItemClick() {

        onView(withId(R.id.special_genre_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.special_genre_categories_grid_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.special_genre_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_top_title)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_scroll_view)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_fragment_main_title)).check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_top_playlists_recycler_view))
                .check(matches(isDisplayed()));
        onView(withId(R.id.special_genre_categories_grid_view)).check(matches(isDisplayed()));

        onView(withId(R.id.special_genre_fragment_main_title)).check(matches(withText("Sha3by")));

        pressBack();
        //*************************    Going Back     **********************************\\

        onView(withId(R.id.special_genre_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.special_genre_categories_grid_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.charts_fragment_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.charts_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.global_top_50_image)).check(matches(isDisplayed()));
        onView(withId(R.id.global_viral_50_sub_title)).check(matches(isDisplayed()));
        onView(withId(R.id.viral_50_by_country_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.top_charts_by_country_text_view)).check(matches(isDisplayed()));

        pressBack();


    }
}