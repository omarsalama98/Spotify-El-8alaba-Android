package com.vnoders.spotify_el8alaba.ui.home;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity;
import com.vnoders.spotify_el8alaba.Lists_Adapters.HomeMainListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.RecentlyPlayedListAdapter;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SettingsList;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.HomePlaylist;
import com.vnoders.spotify_el8alaba.models.Notifications.NotificationToken;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private SharedPreferences notificationTokenShared;
    private HomeViewModel homeViewModel;
    private ImageView settingsButton;
    private ImageView spotifyArtistButton;

    private RecyclerView recentlyPlayedRecyclerView;
    private RecyclerView mainListRecyclerView;
    private SharedPreferences sharedPreferences;
    private String accType;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        notificationTokenShared=getActivity().getSharedPreferences("NOTIFICATION_TOKEN", Context.MODE_PRIVATE);
        sharedPreferences = getActivity().getSharedPreferences(
                getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
        accType = sharedPreferences.getString("type", "");

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        settingsButton=root.findViewById(R.id.settings_image_view);
        spotifyArtistButton = root.findViewById(R.id.spotify_artist_image_view);

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

        if (accType.equals("artist")) {
            spotifyArtistButton.setVisibility(View.VISIBLE);
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
                if (response.body() != null) {
                    myDataList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Categories" + t.getMessage());
            }
        });

        //spotifyArtistButton.setVisibility(View.VISIBLE);
        spotifyArtistButton.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getService().pause();
            startActivity(new Intent(getActivity(), ArtistMainActivity.class));
        });

        settingsButton.setOnClickListener(v -> {

            SettingsList settingsList = new SettingsList();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, settingsList, "SETTINGS_LIST")
                    .addToBackStack(null).commit();
        });

        Call<List<HomePlaylist>> call2 = apiService
                .getCategoryPlaylists("5ec455f01bf12b31fcfc18c2");

        recentlyPlayedRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<HomePlaylist> recentlyPlayedList = new ArrayList<>();
        RecentlyPlayedListAdapter recentlyPlayedListAdapter = new RecentlyPlayedListAdapter(
                HomeFragment.this, recentlyPlayedList);
        recentlyPlayedRecyclerView.setAdapter(recentlyPlayedListAdapter);

        call2.enqueue(new Callback<List<HomePlaylist>>() {
            @Override
            public void onResponse(Call<List<HomePlaylist>> call,
                    Response<List<HomePlaylist>> response) {

                if (response.body() != null) {
                    recentlyPlayedList.addAll(response.body());
                    recentlyPlayedListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<HomePlaylist>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Playlists" + t.getLocalizedMessage());
            }
        });
        String token = notificationTokenShared.getString("notification_token", "token_not_found");
        //Toast.makeText(getActivity(), token, Toast.LENGTH_LONG).show();
        String notSendFlag = notificationTokenShared.getString("notSent", "");
        if (notSendFlag.equals("true")) {
            NotificationToken notificationToken = new NotificationToken(token);
            Call<ResponseBody> notificationRequest = RetrofitClient.getInstance().getAPI(API.class)
                    .addNotificationToken(notificationToken);
            //Toast.makeText(getActivity(),notificationToken.getToken(),Toast.LENGTH_LONG).show();
            notificationRequest.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> notificationRequest,
                        Response<ResponseBody> response) {
                    Log.d("RESPONSE_ADD_NOTIF", response.toString());
                    if (response.code() == 201) {
                        Editor editor = notificationTokenShared.edit();
                        editor.putString("notSent", "false");
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> notificationRequest, Throwable t) {
                    Log.d("RESPONSE_ADD_NOTIF", "FAILED");
                }
            });

        }
    }
}
