package com.vnoders.spotify_el8alaba.ui.home;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.HomeMainListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.RecentlyPlayedListAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SettingsList;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.currentUserProfile.CurrentUserProfileFragment;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView settingsButton;

    private RecyclerView recentlyPlayedRecyclerView;
    private RecyclerView mainListRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        settingsButton=root.findViewById(R.id.settings_image_view);

        mainListRecyclerView = root.findViewById(R.id.home_main_list_recycler_view);
        recentlyPlayedRecyclerView = root.findViewById(R.id.home_recently_played_recycler_view);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_home) {
            navView.setSelectedItemId(R.id.navigation_home);
        }

        APIInterface apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        ArrayList<Category> myDataList = new ArrayList<>();
        HomeMainListAdapter adapter = new HomeMainListAdapter(getContext(), HomeFragment.this,
                myDataList);

        mainListRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mainListRecyclerView.setHasFixedSize(true);
        mainListRecyclerView.setAdapter(adapter);

        Call<List<Category>> call = apiService.getHomeCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                myDataList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Categories" + t.getMessage());
            }
        });

        settingsButton.setOnClickListener(v -> {

            SettingsList settingsList =new SettingsList();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment,settingsList,"SETTINGS_LIST").addToBackStack(null).commit();
        });

        Call<List<Playlist>> call2 = apiService
                .getCategoryPlaylists("5e8f3a325c504a25a711ce25");

        recentlyPlayedRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<Playlist> recentlyPlayedList = new ArrayList<>();
        RecentlyPlayedListAdapter recentlyPlayedListAdapter = new RecentlyPlayedListAdapter(
                HomeFragment.this, recentlyPlayedList);
        recentlyPlayedRecyclerView.setAdapter(recentlyPlayedListAdapter);

        call2.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                recentlyPlayedList.addAll(response.body());
                recentlyPlayedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Playlists" + t.getLocalizedMessage());
            }
        });

    }

}
