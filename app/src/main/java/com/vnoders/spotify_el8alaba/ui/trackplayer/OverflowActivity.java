package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.PlayableTrack;

/**
 * @author Ali Adel
 * Activity to show overflow menu
 * Has to be called with a string extra which has Playable track object as json that is not null
 * if not activity finishes before displaying view
 */
public class OverflowActivity extends AppCompatActivity {

    // used by anyone who starts this to put object in intent with this name
    public static final String OVERFLOW_RECEIVED_TRACK = "OVERFLOW_DISPLAY_TRACK";
    // track that is received from intent (Treat it as not null)
    private PlayableTrack mTrack;

    // references to all the buttons in view
    private View mLikeButton;
    private View mHideButton;
    private View mAddButton;
    private View mArtistButton;
    private View mShareButton;
    private View mSleepButton;
    private View mReportButton;
    private View mCreditsButton;

    /**
     * inflate layout and initialize variables and close if track is not sent or null
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if intent doesn't have track object as extra close
        if (!getIntent().hasExtra(OVERFLOW_RECEIVED_TRACK)) {
            finish();
        }

        // get track object from string extra json
        mTrack = new Gson().fromJson(getIntent().getStringExtra(OVERFLOW_RECEIVED_TRACK), PlayableTrack.class);
        // if object is null close
        if (mTrack == null) {
            finish();
        }

        // set content view since object is not null
        setContentView(R.layout.activity_overflow_menu);

        // if user pressed anywhere that is not a button then close the activity
        findViewById(R.id.overflow_items_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // set the button vars to their views and set their on click listeners
        getReferences();
        // set the data displayed on screen with object
        setScreen();
    }

    /**
     * override on back pressed to close with animation of sliding to bottom
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter_from_top, R.anim.exit_to_top);
    }

    /**
     * override to close activity if user closes screen or turns focus away
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * get all references to views on screen and set their on click listeners
     */
    private void getReferences() {

        // if track is null don't do anything
        if (mTrack == null)
            return;

        mLikeButton = findViewById(R.id.overflow_like);
        mLikeButton.setOnClickListener(v -> likePressed());

        mHideButton = findViewById(R.id.overflow_hide);
        mHideButton.setOnClickListener(v -> hidePressed());

        mAddButton = findViewById(R.id.overflow_add_to_playlist);
        mAddButton.setOnClickListener(v -> addPressed());

        mArtistButton = findViewById(R.id.overflow_view_artist);
        mArtistButton.setOnClickListener(v -> artistPressed());

        mShareButton = findViewById(R.id.overflow_share);
        mShareButton.setOnClickListener(v -> sharePressed());

        mSleepButton = findViewById(R.id.overflow_sleep_timer);
        mSleepButton.setOnClickListener(v -> sleepPressed());

        mReportButton = findViewById(R.id.overflow_report);
        mReportButton.setOnClickListener(v -> reportPressed());

        mCreditsButton = findViewById(R.id.overflow_credits);
        mCreditsButton.setOnClickListener(v -> creditsPressed());
    }

    /**
     * sets data on screen according to track object received in intent
     */
    private void setScreen() {

        // if track is null don't do anything
        if (mTrack == null)
            return;

        // set the name of track
        ((TextView) findViewById(R.id.overflow_song_name)).setText(mTrack.getName());

        // set the author's name
        ((TextView) findViewById(R.id.overflow_author_name)).
                setText(mTrack.getAlbum().getArtists().get(0).getName());

        // set the image displayed in middle
        Picasso.get().load(mTrack.getAlbum().getImages().get(1).getUrl()).
                into(((ImageView) findViewById(R.id.overflow_img)));
    }

    /**
     * User clicked like song so handle it here
     */
    private void likePressed() {

    }

    /**
     * User clicked hide song from playlist so handle it here
     */
    private void hidePressed() {

    }

    /**
     * User clicked add song to playlist so handle it here
     */
    private void addPressed() {

    }

    /**
     * User clicked view artist so handle it here
     */
    private void artistPressed() {

    }

    /**
     * User clicked share so handle it here
     */
    private void sharePressed() {

    }

    /**
     * User clicked sleep timer so handle it here
     */
    private void sleepPressed() {

    }

    /**
     * User clicked report explicit content so handle it here
     */
    private void reportPressed() {

    }

    /**
     * User clicked show credits so handle it here
     */
    private void creditsPressed() {

    }
}

