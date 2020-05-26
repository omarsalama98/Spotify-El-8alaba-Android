package com.vnoders.spotify_el8alaba.ui.trackplayer;

import androidx.lifecycle.LiveData;
import androidx.test.rule.ActivityTestRule;

import com.vnoders.spotify_el8alaba.MainActivity;
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

public class BottomPlayerFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

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

        onView(withId(R.id.bottom_player_container)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_player_image)).check(matches(isDisplayed()));
        onView(withId(R.id.song_information)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_player_buttons_container)).check(matches(isDisplayed()));
        onView(withId(R.id.love_button_main_fragment_bot)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_player_play_pause_button)).check(matches(isDisplayed()));

        onView(withId(R.id.song_information))
                .check(matches(withText("My Testing Song • My Testing Artist")));
    }
}