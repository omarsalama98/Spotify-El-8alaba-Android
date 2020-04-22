package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.home.HomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.LibraryFragment;
import com.vnoders.spotify_el8alaba.ui.premium.PremiumFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchGenresFragment;

public class MainActivity extends AppCompatActivity {

    private static int prevFragment = R.id.navigation_home;
    private BottomNavigationView navView;


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

        RetrofitClient.getInstance().setToken(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlOGYzYTZiYWM5ZWI0MmFiNWFlMDUxNCIsImlhdCI6MTU4NzU4NzIwNywiZXhwIjoxNTk1MzYzMjA3fQ.6Vkhj1i4EEmzJDLuDGfThM6HndWXtNh8ksUOkU2tx1k");
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

}
