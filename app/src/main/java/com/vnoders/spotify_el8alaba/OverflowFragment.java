package com.vnoders.spotify_el8alaba;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.models.PlayableTrack;
import com.vnoders.spotify_el8alaba.models.RealTrack;

import java.lang.reflect.Field;

/**
 * @author Ali Adel
 * Fragment to show info about a track and options to change it
 */
public class OverflowFragment extends BottomSheetDialogFragment {

    // holds track displayed info about (NOT NULL)
    private RealTrack mTrack;

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
     * Make new fragment instance
     * @param track to display info about (MUST BE NOT NULL)
     */
    public OverflowFragment(RealTrack track) {
        mTrack = track;
    }


    /**
     * Instantiate the style of bottom sheet
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    /**
     * Instantiate view to be displayed and set up listeners and set data on items
     * @return view to show on screen
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overflow_menu, container, false);

        if (mTrack == null)
            dismiss();

        root.findViewById(R.id.overflow_items_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // set the button vars to their views and set their on click listeners
        getReferences(root);
        // set the data displayed on screen with object
        setScreen(root);

        return root;
    }

    /**
     * used to make fragment take whole screen and not get swiped down off screen
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog d = super.onCreateDialog(savedInstanceState);
        // view hierarchy is inflated after dialog is shown
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                //this disables outside touch
                d.getWindow().findViewById(R.id.touch_outside).setOnClickListener(null);
                //this prevents dragging behavior
                View content = d.getWindow().findViewById(R.id.design_bottom_sheet);
                content.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT));
                ((CoordinatorLayout.LayoutParams) content.getLayoutParams()).setBehavior(null);
            }
        });
        return d;
    }

    /**
     * get all references to views on screen and set their on click listeners
     */
    private void getReferences(View root) {

        // if track is null don't do anything
        if (mTrack == null)
            return;

        mLikeButton = root.findViewById(R.id.overflow_like);
        mLikeButton.setOnClickListener(v -> likePressed());

        mHideButton = root.findViewById(R.id.overflow_hide);
        mHideButton.setOnClickListener(v -> hidePressed());

        mAddButton = root.findViewById(R.id.overflow_add_to_playlist);
        mAddButton.setOnClickListener(v -> addPressed());

        mArtistButton = root.findViewById(R.id.overflow_view_artist);
        mArtistButton.setOnClickListener(v -> artistPressed());

        mShareButton = root.findViewById(R.id.overflow_share);
        mShareButton.setOnClickListener(v -> sharePressed());

        mSleepButton = root.findViewById(R.id.overflow_sleep_timer);
        mSleepButton.setOnClickListener(v -> sleepPressed());

        mReportButton = root.findViewById(R.id.overflow_report);
        mReportButton.setOnClickListener(v -> reportPressed());

        mCreditsButton = root.findViewById(R.id.overflow_credits);
        mCreditsButton.setOnClickListener(v -> creditsPressed());
    }

    /**
     * sets data on screen according to track object received in intent
     */
    private void setScreen(View root) {

        // if track is null don't do anything
        if (mTrack == null)
            return;

        // set the name of track
        ((TextView) root.findViewById(R.id.overflow_song_name)).setText(mTrack.getName());

        // set the author's name
        ((TextView) root.findViewById(R.id.overflow_author_name)).
                setText(mTrack.getArtists().get(0).getUserInfo().getName());

        // set the image displayed in middle
        (((ImageView) root.findViewById(R.id.overflow_img))).setImageResource(R.drawable.track_image_default);
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
