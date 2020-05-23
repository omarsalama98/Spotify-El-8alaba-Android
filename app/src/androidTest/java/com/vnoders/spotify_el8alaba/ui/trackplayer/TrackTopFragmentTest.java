package com.vnoders.spotify_el8alaba.ui.trackplayer;

import androidx.lifecycle.LiveData;
import androidx.test.rule.ActivityTestRule;

import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class TrackTopFragmentTest {

    @Rule
    public ActivityTestRule<TrackPlayerActivity> activityTestRule = new ActivityTestRule<>(
            TrackPlayerActivity.class);

    @Before
    public void loadingData() {
        LiveData<Track> mViewTrack = TrackViewModel.getInstance().getCurrentTrack();

        Track track = new Track("12313", "My Testing Song", 2000,
                "My Testing Artist", "PLAYING_ARTIST", "My Testing Artist",
                null, "1234", "1534", "asdasf");
        TrackViewModel.getInstance().updateCurrentTrack(track);
    }

    @Test
    public void checkViews() {

        onView(withId(R.id.track_player_top_fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.top_back_button)).check(matches(isDisplayed()));
        onView(withId(R.id.author_select_top)).check(matches(isDisplayed()));
        onView(withId(R.id.playing_from_text)).check(matches(isDisplayed()));
        onView(withId(R.id.author_name_text_top)).check(matches(isDisplayed()));
        onView(withId(R.id.top_overflow_menu)).check(matches(isDisplayed()));

        onView(withId(R.id.playing_from_text))
                .check(matches(withText("Playing from artist")));
        onView(withId(R.id.author_name_text_top))
                .check(matches(withText("My Testing Artist")));
    }
}