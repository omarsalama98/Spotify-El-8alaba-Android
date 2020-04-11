package com.vnoders.spotify_el8alaba.ui.search;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.Bundle;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class SearchByTypeFragmentTest {

    private String searchQuery = "Ay 7aga";
    private String searchType = "Playlists";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void loadingFragment() {
        SearchByTypeFragment fragment = new SearchByTypeFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SearchByTypeConstantsHelper.SEARCH_TYPE_KEY, searchType);
        arguments.putString(SearchByTypeConstantsHelper.SEARCH_QUERY_KEY,
                searchQuery);
        fragment.setArguments(arguments);
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, null)
                .commit();
    }

    @Test
    public void checkViews() {
        onView(withId(R.id.search_by_type_fragment_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.search_by_type_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.search_by_type_fragment_title)).check(matches(isDisplayed()));
        onView(withId(R.id.search_by_type_results_recycler_view)).check(matches(isDisplayed()));

        onView(withId(R.id.search_by_type_fragment_title))
                .check(matches(withText("\"" + searchQuery + "\"" + " in " + searchType)));
    }

    @Test
    public void clickingOnItemInList() {
        onView(withId(R.id.search_by_type_results_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        //Check for playlist later
        pressBack();
    }
}