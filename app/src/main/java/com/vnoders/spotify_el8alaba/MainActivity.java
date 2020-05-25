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
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.LocalDatabase;
import com.vnoders.spotify_el8alaba.ui.home.HomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.LibraryFragment;
import com.vnoders.spotify_el8alaba.ui.premium.PremiumFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchGenresFragment;
import com.vnoders.spotify_el8alaba.ui.trackplayer.MediaPlaybackService;

public class MainActivity extends AppCompatActivity {

    private static int prevFragment = R.id.navigation_home;
    private BottomNavigationView navView;
    public static LocalDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView = findViewById(R.id.nav_view);

        db = Room.databaseBuilder(getApplicationContext(),
                LocalDatabase.class, "database-name").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        navView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if (prevFragment != R.id.navigation_home) {
                            startFragment(new HomeFragment(), item.getItemId());
                            prevFragment = R.id.navigation_home;
                        }
                        return true;

                    case R.id.navigation_search:
                        if (prevFragment != R.id.navigation_search) {
                            startFragment(new SearchGenresFragment(), item.getItemId());
                            prevFragment = R.id.navigation_search;
                        } else {
                            startFragment(new SearchFragment(), item.getItemId());
                            prevFragment = 0;
                        }
                        return true;

                    case R.id.navigation_your_library:
                        if (prevFragment != R.id.navigation_your_library) {
                            startFragment(new LibraryFragment(), item.getItemId());
                            prevFragment = R.id.navigation_your_library;
                        }
                        return true;

                    case R.id.navigation_premium:
                        if (prevFragment != R.id.navigation_premium) {
                            startFragment(new PremiumFragment(), item.getItemId());
                            prevFragment = R.id.navigation_premium;
                        }
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
     * @param fragment The fragment we want to load
     * @param id The id of the fragment to load
     */
    private void startFragment(Fragment fragment, int id) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment);

        // Do not add to back stack if the user clicks the same item again
        int selectedItemId = navView.getSelectedItemId();
        if (id != selectedItemId) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
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
