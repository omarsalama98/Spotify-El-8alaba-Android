package com.vnoders.spotify_el8alaba.ui.trackplayer;

import androidx.test.core.app.ActivityScenario;

import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.ui.login.LoginActivit;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class TrackPlayerActivityTest {

    @Test
    public void checkViews() {
        ActivityScenario activityScenario=ActivityScenario.launch(TrackPlayerActivity.class);

        onView(withId(R.id.track_player_container)).check(matches(isDisplayed()));
        onView(withId(R.id.container_top_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.track_player_space)).check(matches(isDisplayed()));
        onView(withId(R.id.container_bot_fragment)).check(matches(isDisplayed()));
    }

}