package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void loadingFragment() {
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new SearchFragment(), null)
                .commit();
    }


    @Test
    public void checkInitialViewsExist() {

        onView(withId(R.id.search_main_background_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_frame_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.search_edit_text_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_back_arrow)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.search_camera_image_in_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.reset_search_image))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));

        onView(withId(R.id.search_text_layout))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.search_bar_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.search_camera_image_in_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));

        onView(withId(R.id.search_history_list_container)).check(matches(isDisplayed()));
        onView(withId(R.id.search_history_list_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.clear_recent_searches_text_view)).check(matches(isDisplayed()));

        onView(withId(R.id.search_result_list_layout))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.search_list_recycler_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.see_all_artists_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.see_all_songs_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.see_all_playlists_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.see_all_albums_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.see_all_profiles_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));

        onView(withId(R.id.search_empty_background_layout))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.search_image_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.search_frag_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));

    }

    @Test
    public void checkResetSearch() {

        onView(withId(R.id.search_bar_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_edit_text))
                .perform(typeText("loljrisngraes"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.search_camera_image_in_edit_text))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.reset_search_image)).check(matches(isDisplayed()));
        onView(withId(R.id.reset_search_image)).perform(click());
        onView(withId(R.id.search_bar_edit_text)).check(matches(withText("")));

    }

    @Test
    public void checkHistoryAndSearchResultLists() {
        onView(withId(R.id.search_bar_edit_text)).perform(typeText("lol"));

        onView(withId(R.id.search_history_list_container))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.search_history_list_recycler_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.clear_recent_searches_text_view))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));

        onView(withId(R.id.search_result_list_layout))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.search_list_recycler_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.see_all_artists_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.see_all_songs_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.see_all_playlists_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.see_all_albums_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.see_all_profiles_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

    }

    @Test
    public void checkClearingHistoryList() {

        onView(withId(R.id.search_main_background_layout)).perform(closeSoftKeyboard());
        onView(withId(R.id.clear_recent_searches_text_view)).perform(click());

        onView(withId(R.id.search_empty_background_layout))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.search_image_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.search_frag_text_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));

    }


}