package com.vnoders.spotify_el8alaba;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.vnoders.spotify_el8alaba.ui.home.HomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.LibraryFragment;
import com.vnoders.spotify_el8alaba.ui.premium.PremiumFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchGenresFragment;

public class MainActivity extends AppCompatActivity {

    private LibraryFragment libraryFragment;
    private SearchGenresFragment searchGenresFragment;
    private HomeFragment homeFragment;
    private PremiumFragment premiumFragment;
    String fragmentName = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                        }
                        startFragment(homeFragment);
                        return true;
                    case R.id.navigation_search:
                        if (searchGenresFragment == null) {
                            searchGenresFragment = new SearchGenresFragment();
                        }
                        startFragment(searchGenresFragment);
                        return true;
                    case R.id.navigation_your_library:
                        if (libraryFragment == null) {
                            libraryFragment = new LibraryFragment();
                        }
                        startFragment(libraryFragment);
                        return true;
                    case R.id.navigation_premium:
                        if (premiumFragment == null) {
                            premiumFragment = new PremiumFragment();
                        }
                        startFragment(premiumFragment);
                        return true;
                }
                return false;
            }
        });

        // start service that plays tracks here to get tracks from online and play it
        Intent intent = new Intent(this, MediaPlaybackService.class);
        startService(intent);
    }

    /**
     * @param fragment the fragment we want to load
     */
    private void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();

    }


    /**
     * @return an instance of media playback service for use by fragments
     */
    public MediaPlaybackService getService() {
        return mService;
    }


    // connection to service
    private MediaPlaybackService mService;
    // boolean to know if currently bound to service or not
    private boolean mBound = false;

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

}
