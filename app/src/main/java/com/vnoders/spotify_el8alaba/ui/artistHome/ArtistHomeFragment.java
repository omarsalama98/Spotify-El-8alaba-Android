package com.vnoders.spotify_el8alaba.ui.artistHome;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SettingsList;
import com.vnoders.spotify_el8alaba.models.Artist.TrackListens;
import com.vnoders.spotify_el8alaba.models.Artist.TrackListensRequestBody;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArtistHomeFragment extends Fragment {

    private ImageView artistSettingsImageView, artistTopSongImageView, spotifyUserImageView;
    private TextView artistFollowersTextView, artistStreamsTextView,
            artistListenersTextView, artistTopSongStreamsTextView, artistTopSongTextView;
    private TrackListensRequestBody trackListensRequestBody;
    private String artistId;
    private SharedPreferences sharedPreferences;
    private APIInterface apiService;
    private RelativeLayout goingArtistLayout;

    private void getTrackListens() {
        Call<List<TrackListens>> call = apiService.getTrackListens(trackListensRequestBody);
        call.enqueue(new Callback<List<TrackListens>>() {
            @Override
            public void onResponse(Call<List<TrackListens>> call,
                    Response<List<TrackListens>> response) {
                setTracksStatisticsInAPeriod((ArrayList<TrackListens>) response.body());
            }

            @Override
            public void onFailure(Call<List<TrackListens>> call, Throwable t) {

            }
        });
    }

    private void setTracksStatisticsInAPeriod(ArrayList<TrackListens> trackListens) {
        int max = 0, maxPos = 0, totSum = 0, sum;
        for (int i = 0; i < trackListens.size(); i++) {
            sum = 0;
            for (int j = 0; j < trackListens.size(); j++) {
                if (trackListens.get(i).getId() == trackListens.get(j).getId()) {
                    int played = trackListens.get(j).getPlayed();
                    sum += played;
                }
            }
            totSum += trackListens.get(i).getPlayed();
            if (sum >= max) {
                max = sum;
                maxPos = i;
            }
        }
        final int played = max;
        final int sumStreams = totSum;
        String id = trackListens.get(maxPos).getId().getTrackId();
        Call<SearchTrack> call = apiService.getTrack(id);
        call.enqueue(new Callback<SearchTrack>() {
            @Override
            public void onResponse(Call<SearchTrack> call, Response<SearchTrack> response) {
                artistTopSongTextView.setText(response.body().getName());
                String streams = played + " streams";
                artistTopSongStreamsTextView.setText(streams);
                String allStreams = sumStreams + " streams";
                artistStreamsTextView.setText(allStreams);
                goingArtistLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SearchTrack> call, Throwable t) {

            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_artist_home, container, false);
        sharedPreferences = getActivity().getSharedPreferences(
                getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
        artistId = sharedPreferences.getString("id", "");

        artistSettingsImageView = root.findViewById(R.id.artist_settings_image_view);
        artistFollowersTextView = root.findViewById(R.id.artist_followers_text_view);
        artistListenersTextView = root.findViewById(R.id.artist_listeners_text_view);
        artistStreamsTextView = root.findViewById(R.id.artist_streams_text_view);
        artistTopSongStreamsTextView = root.findViewById(R.id.artist_top_song_streams_text_view);
        artistTopSongTextView = root.findViewById(R.id.artist_top_song_text_view);
        spotifyUserImageView = root.findViewById(R.id.spotify_user_image_view);
        goingArtistLayout = root.findViewById(R.id.going_artist_layout);

        trackListensRequestBody = new TrackListensRequestBody();
        String endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        String startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(c.getTime());
        trackListensRequestBody.setStartDate(startDate);
        trackListensRequestBody.setEndDate(endDate);
        trackListensRequestBody.setPeriod("day");
        Log.d("s", startDate);

        TrackListensRequestBody currentTrackListensRequestBody = new TrackListensRequestBody();
        currentTrackListensRequestBody.setPeriod("day");

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = getActivity().findViewById(R.id.artist_nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_artist_home) {
            navView.setSelectedItemId(R.id.navigation_artist_home);
        }

        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);
        ArrayList<String> ids = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();

        Call<List<Track>> call = apiService.getArtistTopTracks(artistId);
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                tracks.addAll(response.body());
                for (int i = 0; i < tracks.size(); i++) {
                    ids.add(tracks.get(0).getId());
                }
                trackListensRequestBody.setIds(ids);
                getTrackListens();
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {

            }
        });

        spotifyUserImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        artistSettingsImageView.setOnClickListener(v -> {
            SettingsList settingsList = new SettingsList();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.artist_nav_host_fragment, settingsList, "SETTINGS_LIST")
                    .addToBackStack(null).commit();
        });

    }

}
