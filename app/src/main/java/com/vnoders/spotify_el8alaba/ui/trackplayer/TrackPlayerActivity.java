package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;

public class TrackPlayerActivity extends AppCompatActivity {

    private Mock.mock_track mCurrentTrack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_track_player);

        getSupportActionBar().hide();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        mCurrentTrack = Mock.getMockTrack();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter_from_top, R.anim.exit_to_top);
    }

    public Mock.mock_track getCurrentTrack() {
        return mCurrentTrack;
    }
}
