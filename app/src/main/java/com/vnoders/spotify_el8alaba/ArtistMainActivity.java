package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.ui.artistHome.ArtistHomeFragment;

public class ArtistMainActivity extends AppCompatActivity {

    private static int prevFragment = R.id.navigation_artist_home;
    private BottomNavigationView navView;

    /**
     * @param fragment The fragment we want to load
     * @param id       The id of the fragment to load
     */
    private void startFragment(Fragment fragment, int id) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.artist_nav_host_fragment, fragment);

        // Do not add to back stack if the user clicks the same item again
        int selectedItemId = navView.getSelectedItemId();
        if (id != selectedItemId) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_main);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView = findViewById(R.id.artist_nav_view);

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_artist_home:
                    if (prevFragment != R.id.navigation_artist_home) {
                        startFragment(new ArtistHomeFragment(), item.getItemId());
                        prevFragment = R.id.navigation_artist_home;
                    }
                    return true;

            }
            return false;
        });

    }


}
