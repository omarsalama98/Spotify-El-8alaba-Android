package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.palette.graphics.Palette;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vnoders.spotify_el8alaba.OnSwipeTouchListener;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.AdItem;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;


/**
 * @author Ali Adel Class that shows the media player and binds with media playback service
 */
public class TrackPlayerActivity extends AppCompatActivity {

    // how much to extend gradient of background to get smoother gradient
    private static final int GRADIENT_SMOOTHNESS = 10;

    // connection to service
    private MediaPlaybackService mService;
    // boolean to know if currently bound to service or not
    private boolean mBound = false;

    // holds track image view
    private ImageView mTrackImageView;

    // holds grey screen for ad layout
    private View mGreyScreen;
    // holds ad layout
    private View mAdWrapper;
    // holds ad image
    private ImageView mAdImage;
    // holds ad text
    private TextView mAdText;

    /**
     * Connection to bind with media playback service
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlaybackService.MediaPlaybackBinder binder =
                    (MediaPlaybackService.MediaPlaybackBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    /**
     * updates UI when called with track
     *
     * @param track current track being played holding info
     */
    private void updateUI(Track track) {
        if (track == null) {
            return;
        }

        if (TextUtils.isEmpty(track.getImage())) {
            mTrackImageView.setImageResource(R.drawable.track_image_default);
        } else {
            Picasso.get().load(track.getImage()).into(mTarget);
        }
    }

    /**
     * Function to display Ad to screen
     * @param adItem to display to user
     */
    private void displayAd(AdItem adItem) {
        if (adItem == null) {
            mGreyScreen.setVisibility(View.GONE);
            mAdWrapper.setVisibility(View.GONE);
            return;
        }

        // show the  items
        mGreyScreen.setVisibility(View.VISIBLE);
        mAdWrapper.setVisibility(View.VISIBLE);

        // show text of ad
        if (adItem.getText() != null)
            mAdText.setText(adItem.getText());
        else
            mAdText.setText(" ");

        // show image of ad
        if (adItem.getImages() != null) {
            if (adItem.getImages().size() > 0) {
                if (!(TextUtils.isEmpty(adItem.getImages().get(0).getUrl()))) {
                    Picasso.get().load(adItem.getImages().get(0).getUrl()).into(mAdImage);
                    return;
                }
            }
        }

        mAdImage.setImageResource(R.drawable.track_image_default);
    }

    /**
     * Init ad items to be gone
     */
    private void initAdComponents() {
        mGreyScreen.setVisibility(View.GONE);
        mAdWrapper.setVisibility(View.GONE);

        // set listener to hide ad when clicked
        mGreyScreen.setOnClickListener(v -> TrackViewModel.getInstance().updateAdItem(null));

        mAdWrapper.setOnClickListener(v -> TrackViewModel.getInstance().updateAdItem(null));
    }

    /**
     * override back pressed to show sliding down animation when quitting
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter_from_top, R.anim.exit_to_top);
    }

    /**
     * @return an instance of media playback service for use by fragments
     */
    public MediaPlaybackService getService() {
        return mService;
    }

    // holds view container of track player
    private View mTrackPlayerContainer;

    /**
     * Just set the layout and get track currently being played
     *
     * @param savedInstanceState bundle of saved state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_track_player);

        // get reference to container
        mTrackPlayerContainer = findViewById(R.id.track_player_container);
        // get reference to image
        mTrackImageView = findViewById(R.id.track_image_view);

        // to remove the color of bottom navigation
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // get reference to ad items
        mGreyScreen = findViewById(R.id.track_play_grey_screen);
        mAdWrapper = findViewById(R.id.ad_wrapper);
        mAdImage = findViewById(R.id.track_player_ad_image);
        mAdText = findViewById(R.id.track_player_ad_text);
        // init Ad settings
        initAdComponents();

        // listener for touches on image and surrounding space for swipe gestures
        // tell the service if swipe is detected to skip
        findViewById(R.id.track_player_space)
                .setOnTouchListener(new OnSwipeTouchListener(TrackPlayerActivity.this) {
                    @Override
                    public void onSwipeLeft() {
                        mService.skipToNext();
                    }

                    @Override
                    public void onSwipeRight() {
                        mService.skipToPrev();
                    }
                });

        // setting observer to know when data changes
        TrackViewModel.getInstance().getCurrentTrack().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track realTrack) {
                updateUI(realTrack);
            }
        });

        // observe to know when to display track
        TrackViewModel.getInstance().getAdItem().observe(this, new Observer<AdItem>() {
            @Override
            public void onChanged(AdItem adItem) {
                displayAd(adItem);
            }
        });
    }

    /**
     * Override it to bind to media playback service
     */
    @Override
    protected void onStart() {
        super.onStart();

        // bind the activity to service
        Intent intent = new Intent(this, MediaPlaybackService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Override it to sever connection to bound service
     */
    @Override
    protected void onStop() {
        super.onStop();

        // if bound then unbind to release service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * Used by picasso to know when image loading finished to get dominant color and set background
     * as color of image
     */
    private Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // set the image view to image got by picasso
            mTrackImageView.setImageBitmap(bitmap);

            // create gradient dependent on vibrant color of img
            ShapeDrawable drawable = new ShapeDrawable(new RectShape());
            drawable.getPaint().setShader
                    (new LinearGradient(0, 0,
                            0, mTrackPlayerContainer.getMeasuredHeight() * GRADIENT_SMOOTHNESS,
                            getColor(bitmap, 0.2f), getColor(bitmap, 0.1f),
                            Shader.TileMode.CLAMP));

            // set the background to gradient
            mTrackPlayerContainer.setBackground(drawable);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            e.printStackTrace();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    /**
     * helper method to get vibrant color darkened
     *
     * @param bitmap image to get color from
     * @param factor factor to darken color by
     * @return vibrant color of image darkened by factor
     */
    private int getColor(Bitmap bitmap, float factor) {
        return manipulateColor(getVibrantColor(bitmap), factor);
    }

    /**
     * helper method to get vibrant color of image
     *
     * @param bitmap image to get color from
     * @return vibrant color of image
     */
    private int getVibrantColor(Bitmap bitmap) {
        return Palette.from(bitmap).generate().getVibrantColor(Color.parseColor("#00FFFF"));
    }

    /**
     * manipulate color to darken it by factor
     *
     * @param color  color wanting to manipulate
     * @param factor factor to darken by
     * @return color darkened by factor
     */
    private int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }
}
