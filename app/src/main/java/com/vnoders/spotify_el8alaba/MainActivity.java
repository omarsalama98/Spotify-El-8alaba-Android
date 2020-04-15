package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.vnoders.spotify_el8alaba.ui.home.HomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.LibraryFragment;
import com.vnoders.spotify_el8alaba.ui.premium.PremiumFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchGenresFragment;

public class MainActivity extends AppCompatActivity {

    private LibraryFragment libraryFragment;
    private SearchGenresFragment searchGenresFragment;
    private HomeFragment homeFragment;
    private PremiumFragment premiumFragment;
    private static int prevFragment = R.id.navigation_home;
    private SearchFragment searchFragment;
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
                        if (prevFragment != R.id.navigation_home) {
                            startFragment(homeFragment);
                            prevFragment = R.id.navigation_home;
                        }
                        return true;

                    case R.id.navigation_search:

                        if (searchGenresFragment == null) {
                            searchGenresFragment = new SearchGenresFragment();
                        }
                        if (prevFragment != R.id.navigation_search) {
                            startFragment(searchGenresFragment);
                            prevFragment = R.id.navigation_search;
                        } else {
                            if (searchFragment == null) {
                                searchFragment = new SearchFragment();
                            }
                            startFragment(searchFragment);
                            prevFragment = 0;
                        }
                        return true;

                    case R.id.navigation_your_library:

                        if (libraryFragment == null) {
                            libraryFragment = new LibraryFragment();
                        }
                        if (prevFragment != R.id.navigation_your_library) {
                            startFragment(libraryFragment);
                            prevFragment = R.id.navigation_your_library;
                        }
                        return true;

                    case R.id.navigation_premium:

                        if (premiumFragment == null) {
                            premiumFragment = new PremiumFragment();
                        }
                        if (prevFragment != R.id.navigation_premium) {
                            startFragment(premiumFragment);
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
     * @param fragment the fragment we want to load
     */
    private void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

}
