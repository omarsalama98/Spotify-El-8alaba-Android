package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy;
import com.vnoders.spotify_el8alaba.R;

public class LibraryFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_your_library) {
            navView.setSelectedItemId(R.id.navigation_your_library);
        }
//        LibraryPagerAdapter libraryPagerAdapter = new LibraryPagerAdapter(this);
        LibraryPagerAdapter libraryPagerAdapter = new LibraryPagerAdapter(getActivity() );
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(libraryPagerAdapter);



        TabLayout tabs = view.findViewById(R.id.tabs);

        new TabLayoutMediator(tabs, viewPager, new TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull Tab tab, int position) {
                tab.setText(LibraryPagerAdapter.TAB_TITLES[position]);
            }
        }).attach();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

}
