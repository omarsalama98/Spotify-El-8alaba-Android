package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.vnoders.spotify_el8alaba.ui.home.HomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.LibraryFragment;
import com.vnoders.spotify_el8alaba.ui.premium.PremiumFragment;

public class MainActivity extends AppCompatActivity {

    String FRAGMENT_HOME = "fragment_home";
    String FRAGMENT_OTHER = "fragment_other";

    BottomNavigationView navView;

    private void viewFragment(Fragment fragment, String name) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fT = fragmentManager.beginTransaction();
        fT.replace(R.id.nav_host_fragment, fragment);
        final int count = fragmentManager.getBackStackEntryCount();
        if (name.equals(FRAGMENT_OTHER)) {
            fT.addToBackStack(name);
        }
        fT.commit();

        fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager
                            .popBackStack(FRAGMENT_OTHER, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    navView.getMenu().getItem(0).setChecked(true);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewFragment(new HomeFragment(), FRAGMENT_HOME);
                        return true;
                    case R.id.navigation_search:
                        viewFragment(new SearchGenresFragment(), FRAGMENT_OTHER);
                        return true;
                    case R.id.navigation_your_library:
                        viewFragment(new LibraryFragment(), FRAGMENT_OTHER);
                        return true;
                    case R.id.navigation_premium:
                        viewFragment(new PremiumFragment(), FRAGMENT_OTHER);
                        return true;
                }
                return false;
            }
        });

        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_your_library,
                R.id.navigation_premium)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener(new OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                    @NonNull NavDestination destination, @Nullable Bundle arguments) {
                getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        if (navView.getSelectedItemId() == R.id.navigation_home) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else {
            super.onBackPressed();
        }
    }
}
