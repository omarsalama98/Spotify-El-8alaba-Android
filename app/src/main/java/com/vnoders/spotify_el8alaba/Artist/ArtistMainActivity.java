package com.vnoders.spotify_el8alaba.Artist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.Album;
import com.vnoders.spotify_el8alaba.models.Artist.Artist;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistAlbums;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistTrack;
import com.vnoders.spotify_el8alaba.models.Artist.MyTrack;
import com.vnoders.spotify_el8alaba.models.Artist.TrackListens;
import com.vnoders.spotify_el8alaba.models.Artist.TrackListensRequestBody;
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

public class ArtistMainActivity extends AppCompatActivity {

    private static int prevFragment = R.id.navigation_artist_home;
    public static BottomNavigationView navView;
    static APIInterface apiService;
    static ArrayList<MyTrack> mTracks;
    static ArrayList<Album> mAlbums;
    private RelativeLayout goingArtistLayout;
    private TrackListensRequestBody trackListensRequestBody;
    private String artistId;
    private ArrayList<String> ids;
    private List<Track> tracks;
    private Artist mArtist;
    static String followers, songTopStreams, allSongsStreams, topSongName;

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

    private void getArtistAlbums() {

        Call<ArtistAlbums> call = apiService.getArtistAlbums(artistId);
        call.enqueue(new Callback<ArtistAlbums>() {
            @Override
            public void onResponse(Call<ArtistAlbums> call, Response<ArtistAlbums> response) {
                mAlbums.addAll(response.body().getAlbums());
                if (ArtistLibraryFragment.albumsListAdapter != null) {
                    ArtistLibraryFragment.albumsListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArtistAlbums> call, Throwable t) {

            }
        });
    }

    private void getArtistTopTracks() {
        Call<List<Track>> call = apiService.getArtistTopTracks(artistId);
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                tracks.addAll(response.body());
                for (int i = 0; i < tracks.size(); i++) {
                    ids.add(tracks.get(i).getId());
                }
                trackListensRequestBody.setIds(ids);
                getTrackListens(trackListensRequestBody);
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
            }
        });
    }

    private void getArtistsTracksNames(String tracksIds) {

        Call<List<ArtistTrack>> call = apiService.getTracks(tracksIds);
        call.enqueue(new Callback<List<ArtistTrack>>() {
            @Override
            public void onResponse(Call<List<ArtistTrack>> call,
                    Response<List<ArtistTrack>> response) {
                List<ArtistTrack> myTracks = response.body();
                for (int i = 0; i < mTracks.size(); i++) {
                    String f = String.valueOf(mTracks.get(i).getStreams());
                    Log.d("d", f);
                    for (int j = 0; j < myTracks.size(); j++) {
                        if (mTracks.get(i).getId().equals(myTracks.get(j).getId())) {
                            mTracks.get(i).setName(myTracks.get(j).getName());
                            Log.e("d", myTracks.get(j).getName());
                        }
                    }
                }
                if (ArtistLibraryFragment.songsListAdapter != null) {
                    ArtistLibraryFragment.songsListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ArtistTrack>> call, Throwable t) {

            }
        });
    }

    private void getTrackListens(TrackListensRequestBody trackListensRequestBody) {

        String endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        String startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(c.getTime());
        trackListensRequestBody.setStartDate(startDate);
        trackListensRequestBody.setEndDate(endDate);
        trackListensRequestBody.setPeriod("day");

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
            if (trackListens.get(i).getTrack().getId() == null) {
                continue;
            }
            for (int j = 0; j < trackListens.size(); j++) {
                if (trackListens.get(i).getTrack().getId()
                        .equals(trackListens.get(j).getTrack().getId())) {
                    int played = trackListens.get(j).getPlayed();
                    sum += played;
                }
            }
            MyTrack myTrack = new MyTrack(trackListens.get(i).getTrack().getId(), sum);
            myTrack.setName(trackListens.get(i).getTrack().getName());
            if (!mTracks.contains(myTrack)) {
                mTracks.add(myTrack);
            }
            totSum += trackListens.get(i).getPlayed();
            if (sum >= max) {
                max = sum;
                maxPos = i;
            }
        }
        final int played = max;
        final int sumStreams = totSum;
        String id = trackListens.get(maxPos).getTrack().getId();
        Call<ArtistTrack> call = apiService.getTrack(id);
        call.enqueue(new Callback<ArtistTrack>() {
            @Override
            public void onResponse(Call<ArtistTrack> call, Response<ArtistTrack> response) {
                topSongName = response.body().getName();
                songTopStreams = played + " streams";
                allSongsStreams = sumStreams + " streams";
                goingArtistLayout.setVisibility(View.GONE);
                navView.setVisibility(View.VISIBLE);
                ArtistHomeFragment.updateUI();
            }

            @Override
            public void onFailure(Call<ArtistTrack> call, Throwable t) {
                Log.d("d", "Failed" + t.getMessage());
            }
        });
    }

    private void getArtistInfo() {
        Call<Artist> call = apiService.getArtist(artistId);
        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                mArtist = response.body();
                followers = mArtist.getFollowers().getTotal() + " followers";
            }
            @Override
            public void onFailure(Call<Artist> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_main);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        navView = findViewById(R.id.artist_nav_view);
        goingArtistLayout = findViewById(R.id.going_artist_layout);

        SharedPreferences sharedPreferences = getSharedPreferences(
                getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
        artistId = sharedPreferences.getString("id", "");

        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        trackListensRequestBody = new TrackListensRequestBody();

        ids = new ArrayList<>();
        tracks = new ArrayList<>();
        mTracks = new ArrayList<>();
        mAlbums = new ArrayList<>();
        getArtistInfo();
        getArtistTopTracks();
        getArtistAlbums();

        StringBuilder tracksIds = new StringBuilder();
        for (int i = 0; i < mTracks.size(); i++) {
            tracksIds.append(mTracks.get(i).getId());
            if (i != mTracks.size() - 1) {
                tracksIds.append(",");
            }
        }

        getArtistsTracksNames(tracksIds.toString());

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_artist_home:
                    if (prevFragment != R.id.navigation_artist_home) {
                        startFragment(new ArtistHomeFragment(), item.getItemId());
                        prevFragment = R.id.navigation_artist_home;
                    }
                    return true;
                case R.id.navigation_artist_library:
                    if (prevFragment != R.id.navigation_artist_library) {
                        startFragment(new ArtistLibraryFragment(), item.getItemId());
                        prevFragment = R.id.navigation_artist_library;
                    }
                    return true;
            }
            return false;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
