package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class NavigationThroughSearchTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void loadingFragment() {
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new SearchGenresFragment(), null)
                .commit();
    }

    @Test
    public void checkViewsExist() {
        onView(withId(R.id.search_fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.text)).check(matches(isDisplayed()));
        onView(withId(R.id.genres_search_bar_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.search_genres_scroll_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.search_top_genres_gridview)).check(matches(isDisplayed()));
        onView(withId(R.id.search_browse_all_genres_gridview))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
    }

    @Test
    public void clickGenreItems() {
        onView(withId(R.id.search_fragment_container)).check(matches(isDisplayed()));

        onView(withId(R.id.search_top_genres_gridview))
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

        onView(withId(R.id.search_fragment_container)).check(matches(isDisplayed()));

        onView(withId(R.id.search_top_genres_gridview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.charts_fragment_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.charts_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.global_top_50_image)).check(matches(isDisplayed()));
        onView(withId(R.id.global_viral_50_sub_title)).check(matches(isDisplayed()));
        onView(withId(R.id.viral_50_by_country_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.top_charts_by_country_text_view)).check(matches(isDisplayed()));

        pressBack();
        //*************************    Going Back     **********************************\\

        onView(withId(R.id.search_fragment_container)).check(matches(isDisplayed()));

        onView(withId(R.id.search_top_genres_gridview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.genre_fragment_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_top_title)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_main_title)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_scroll_view)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_playlists_grid_view)).check(matches(isDisplayed()));
        onView(withId(R.id.genre_fragment_see_more_button))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
    }

    @Test
    public void clickSearchBar() {
        String searchQuery = "Gamed";

        onView(withId(R.id.search_fragment_container)).check(matches(isDisplayed()));

        onView(withId(R.id.genres_search_bar_text_view)).perform(click());

        onView(withId(R.id.search_bar_edit_text))
                .perform(typeText(searchQuery), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.search_list_recycler_view))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.search_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        pressBack();

        //      Makes unjustified error so I tested it alone in SearchByTypeFragmentTest

        /*onView(withId(R.id.see_all_profiles_text_view)).perform(scrollTo());
        onView(withId(R.id.see_all_playlists_text_view)).perform(click());


        onView(withId(R.id.search_by_type_fragment_title))
                .check(matches(withText("\"" + searchQuery + "\"" + " in Playlists")));

        onView(withId(R.id.search_by_type_results_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        pressBack();

        pressBack();*/

    }
}
