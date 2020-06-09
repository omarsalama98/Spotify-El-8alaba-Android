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
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity;
import com.vnoders.spotify_el8alaba.ConstantsHelper.RecentlyPlayedConstantsHelper;
import com.vnoders.spotify_el8alaba.Lists_Adapters.HomeMainListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.RecentlyPlayedListAdapter;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SettingsList;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.Home.PlayContext;
import com.vnoders.spotify_el8alaba.models.Home.RecentlyPlayed;
import com.vnoders.spotify_el8alaba.models.Notifications.NotificationToken;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchPlaylist;
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
    private APIInterface apiService;
    private View loadingView;
    private ProgressBar loadingIcon;
    private RecentlyPlayedListAdapter recentlyPlayedListAdapter;
    private ArrayList<Object> recentlyPlayedList;
    private int REQUESTS_TBD = 2;
    private int requestsDone = 0;

    private void populateRecentlyPlayed(RecentlyPlayed recentlyPlayed) {
        ArrayList<PlayContext> mContexts = (ArrayList<PlayContext>) recentlyPlayed
                .getPlayContexts();
        //recentlyPlayedList.clear();
        REQUESTS_TBD += mContexts.size();
        for (int i = 0; i < mContexts.size(); i++) {
            String type = mContexts.get(i).getUri().split(":")[1];
            String id = mContexts.get(i).getUri().split(":")[2];

            switch (type) {
                case RecentlyPlayedConstantsHelper
                        .ALBUM:
                    Call<SearchAlbum> call = apiService.getSimpleAlbum(id);
                    call.enqueue(new Callback<SearchAlbum>() {
                        @Override
                        public void onResponse(Call<SearchAlbum> call,
                                Response<SearchAlbum> response) {
                            requestsDone += 1;
                            if (response.body() != null) {
                                recentlyPlayedList.add(response.body());
                                recentlyPlayedListAdapter.notifyDataSetChanged();
                            }
                            if (requestsDone == REQUESTS_TBD) {
                                loadingView.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onFailure(Call<SearchAlbum> call, Throwable t) {
                        }
                    });
                    break;
                case RecentlyPlayedConstantsHelper
                        .ARTIST:
                    Call<List<SearchArtist>> call2 = apiService.getSimpleArtist(id);
                    call2.enqueue(new Callback<List<SearchArtist>>() {
                        @Override
                        public void onResponse(Call<List<SearchArtist>> call,
                                Response<List<SearchArtist>> response) {
                            requestsDone += 1;
                            if (response.body() != null) {
                                recentlyPlayedList.add(response.body());
                                recentlyPlayedListAdapter.notifyDataSetChanged();
                            }
                            if (requestsDone == REQUESTS_TBD) {
                                loadingView.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<SearchArtist>> call, Throwable t) {
                        }
                    });
                    break;
                case RecentlyPlayedConstantsHelper
                        .PLAYLIST:
                    Call<SearchPlaylist> call3 = apiService.getSimplePlaylist(id);
                    call3.enqueue(new Callback<SearchPlaylist>() {
                        @Override
                        public void onResponse(Call<SearchPlaylist> call,
                                Response<SearchPlaylist> response) {
                            requestsDone += 1;
                            if (response.body() != null) {
                                recentlyPlayedList.add(response.body());
                                recentlyPlayedListAdapter.notifyDataSetChanged();
                            }
                            if (requestsDone == REQUESTS_TBD) {
                                loadingView.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<SearchPlaylist> call, Throwable t) {
                        }
                    });
                    break;
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        notificationTokenShared = getActivity()
                .getSharedPreferences("NOTIFICATION_TOKEN", Context.MODE_PRIVATE);
        sharedPreferences = getActivity().getSharedPreferences(
                getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
        accType = sharedPreferences.getString("type", "");

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        settingsButton = root.findViewById(R.id.settings_image_view);
        spotifyArtistButton = root.findViewById(R.id.spotify_artist_image_view);
        loadingView = root.findViewById(R.id.loading_view);
        loadingIcon = root.findViewById(R.id.spin_kit);

        Sprite circle = new FadingCircle();
        circle.setAnimationDelay(100);
        loadingIcon.setIndeterminateDrawable(circle);

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
        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

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
                requestsDone += 1;
                if (response.body() != null) {
                    myDataList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                if (requestsDone == REQUESTS_TBD) {
                    loadingView.setVisibility(View.GONE);
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

        Call<RecentlyPlayed> call2 = apiService
                .getRecentlyPlayed();

        recentlyPlayedRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recentlyPlayedList = new ArrayList<>();
        recentlyPlayedListAdapter = new RecentlyPlayedListAdapter(
                HomeFragment.this, recentlyPlayedList);
        recentlyPlayedRecyclerView.setAdapter(recentlyPlayedListAdapter);

        call2.enqueue(new Callback<RecentlyPlayed>() {
            @Override
            public void onResponse(Call<RecentlyPlayed> call,
                    Response<RecentlyPlayed> response) {
                requestsDone += 1;
                if (response.body() != null) {
                    populateRecentlyPlayed(response.body());
                }
                if (requestsDone == REQUESTS_TBD) {
                    loadingView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RecentlyPlayed> call, Throwable t) {
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








