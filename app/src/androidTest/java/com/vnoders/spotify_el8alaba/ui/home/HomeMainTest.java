package com.vnoders.spotify_el8alaba.ui.home;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HomeMainTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void loadingFragment() {
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new HomeFragment(), null)
                .commit();
    }

    @Test
    public void checkElementsTest() {
        onView(withId(R.id.home_fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.home_scroll_view)).check(matches(isDisplayed()));
        onView(withId(R.id.home_recently_played_title)).check(matches(isDisplayed()));
        onView(withId(R.id.home_recently_played_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.home_main_list_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.settings_image_view)).check(matches(isDisplayed()));
    }

    @Test
    public void recentlyPlayedItemClickedTest() {
        onView(withId(R.id.home_recently_played_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        //onView(withId(R.id.playlist_home_up)).check(matches(isDisplayed()));

    }
}
