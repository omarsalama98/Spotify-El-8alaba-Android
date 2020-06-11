package com.vnoders.spotify_el8alaba.ui.home;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import org.hamcrest.Matcher;
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

    /**
     * @param id the id for the recycler view child that i want to perform an action on
     */
    private static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = view.findViewById(id);
                click().perform(uiController,
                        recyclerView.findViewHolderForAdapterPosition(0).itemView);
            }
        };
    }

    @Test
    public void settingsIconClickTest() {
        onView(withId(R.id.settings_image_view)).perform(click());
        onView(withId(R.id.current_user_profile_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.user_name)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.home_fragment_container)).check(matches(isDisplayed()));

    }

    @Test
    public void recentlyPlayedItemClickTest() {
        onView(withId(R.id.home_recently_played_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public void mainListItemClickTest() {
        onView(withId(R.id.home_main_list_recycler_view))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0,
                                clickChildViewWithId(R.id.home_inner_list_recycler_view)));
    }


}
