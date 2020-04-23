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

public class TrackTopFragmentTest {

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