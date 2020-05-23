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

public class TrackBotFragmentTest {

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

        onView(withId(R.id.track_player_bot_fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.song_name_text)).check(matches(isDisplayed()));
        onView(withId(R.id.author_name_text_bot)).check(matches(isDisplayed()));
        onView(withId(R.id.seek_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.track_progress_text_bot)).check(matches(isDisplayed()));
        onView(withId(R.id.trackProgress)).check(matches(isDisplayed()));
        onView(withId(R.id.trackDuration)).check(matches(isDisplayed()));
        onView(withId(R.id.buttons_bot_container)).check(matches(isDisplayed()));
        onView(withId(R.id.love_button_track_player_bot)).check(matches(isDisplayed()));
        onView(withId(R.id.skip_previous_button)).check(matches(isDisplayed()));
        onView(withId(R.id.play_pause_button)).check(matches(isDisplayed()));
        onView(withId(R.id.skip_next_button)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_buttons_share_play_others)).check(matches(isDisplayed()));
        onView(withId(R.id.share_button)).check(matches(isDisplayed()));

        onView(withId(R.id.song_name_text))
                .check(matches(withText("My Testing Song")));
        onView(withId(R.id.author_name_text_bot))
                .check(matches(withText("My Testing Artist")));
        onView(withId(R.id.trackDuration))
                .check(matches(withText("00:02")));
    }
}