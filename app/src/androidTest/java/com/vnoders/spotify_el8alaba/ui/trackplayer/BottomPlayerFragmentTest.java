package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.app.Activity;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.TrackViewModel;
import com.vnoders.spotify_el8alaba.models.RealArtist;
import com.vnoders.spotify_el8alaba.models.RealTrack;
import com.vnoders.spotify_el8alaba.models.RealUserInfo;
import com.vnoders.spotify_el8alaba.ui.login.LoginActivit;
import com.vnoders.spotify_el8alaba.ui.search.ChartsFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class BottomPlayerFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

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

        onView(withId(R.id.bottom_player_container)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_player_image)).check(matches(isDisplayed()));
        onView(withId(R.id.song_information)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_player_buttons_container)).check(matches(isDisplayed()));
        onView(withId(R.id.love_button)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_player_play_pause_button)).check(matches(isDisplayed()));

        onView(withId(R.id.song_information))
                .check(matches(withText("My Testing Song • My Testing Artist")));
    }
}