package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ChartsFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void loadingFragment() {
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new ChartsFragment(), null)
                .commit();
    }

    @Test
    public void checkViews() {

        onView(withId(R.id.charts_fragment_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.charts_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.global_top_50_image)).check(matches(isDisplayed()));
        onView(withId(R.id.global_viral_50_image)).check(matches(isDisplayed()));
        onView(withId(R.id.global_top_50_sub_title)).check(matches(isDisplayed()));
        onView(withId(R.id.global_viral_50_sub_title)).check(matches(isDisplayed()));
        onView(withId(R.id.top_charts_by_country_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.viral_50_by_country_text_view)).check(matches(isDisplayed()));

        onView(withId(R.id.global_top_50_sub_title))
                .check(matches(withText(R.string.global_top_50)));
        onView(withId(R.id.global_viral_50_sub_title))
                .check(matches(withText(R.string.global_viral_50)));
        onView(withId(R.id.top_charts_by_country_text_view))
                .check(matches(withText(R.string.top_charts_by_country)));
        onView(withId(R.id.viral_50_by_country_text_view))
                .check(matches(withText(R.string.viral_50_by_country)));

    }
}