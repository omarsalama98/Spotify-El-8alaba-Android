package com.vnoders.spotify_el8alaba.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.HomeMainListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.RecentlyPlayedListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeMainListItem;
import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private RecyclerView recentlyPlayedRecyclerView;
    private RecyclerView mainListRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mainListRecyclerView = root.findViewById(R.id.home_main_list_recycler_view);
        recentlyPlayedRecyclerView = root.findViewById(R.id.home_recently_played_recycler_view);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<HomeInnerListItem> innerListItems = new ArrayList<>();
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f0000000265af49474d91827160b56b27"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));

        ArrayList<HomeMainListItem> mainListItems = Mock.getMainHomeList();

        /*  TODO: Will be used when backend is populated
        APIInterface apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        final ArrayList[] homeCategories = new ArrayList[]{new ArrayList<>()};

        Call<List<Category>> call = apiService.getHomeCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, response.body().get(0).getName());
                homeCategories[0] = (ArrayList<Category>) response.body();
                // homeCategories[0] will be put in the adapter
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Categories");
            }
        });
        */
        //mainListRecyclerView.setAdapter(new HomeMainListAdapter(getContext(), this, homeCategories[0]));

        mainListRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mainListRecyclerView.setHasFixedSize(true);
        mainListRecyclerView.setAdapter(new HomeMainListAdapter(mainListItems, getContext(), this));

        recentlyPlayedRecyclerView
                .setAdapter(new RecentlyPlayedListAdapter(innerListItems, this));
        recentlyPlayedRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}

//      Testaya bas



