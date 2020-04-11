package com.vnoders.spotify_el8alaba.ui.trackplayer;

import androidx.lifecycle.LiveData;
import androidx.test.rule.ActivityTestRule;

import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.TrackViewModel;
import com.vnoders.spotify_el8alaba.models.RealArtist;
import com.vnoders.spotify_el8alaba.models.RealTrack;
import com.vnoders.spotify_el8alaba.models.RealUserInfo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        LiveData<RealTrack> mViewTrack = TrackViewModel.getInstance().getCurrentTrack();

        List<RealArtist> artists = new ArrayList<>();
        artists.add(new RealArtist(new RealUserInfo("My Testing Artist")));
        RealTrack track = new RealTrack("12313", "My Testing Song", 2000, artists);
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
        onView(withId(R.id.love_button)).check(matches(isDisplayed()));
        onView(withId(R.id.track_control_buttons)).check(matches(isDisplayed()));
        onView(withId(R.id.skip_previous_button)).check(matches(isDisplayed()));
        onView(withId(R.id.play_pause_button)).check(matches(isDisplayed()));
        onView(withId(R.id.skip_next_button)).check(matches(isDisplayed()));
        onView(withId(R.id.remove_song_button)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_buttons_share_play_others)).check(matches(isDisplayed()));
        onView(withId(R.id.connect_devices_button)).check(matches(isDisplayed()));
        onView(withId(R.id.share_button)).check(matches(isDisplayed()));

        onView(withId(R.id.song_name_text))
                .check(matches(withText("My Testing Song")));
        onView(withId(R.id.author_name_text_bot))
                .check(matches(withText("My Testing Artist")));
        onView(withId(R.id.trackDuration))
                .check(matches(withText("00:02")));
    }
}