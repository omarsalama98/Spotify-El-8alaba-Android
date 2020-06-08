package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.List;

public class AddArtistActivity extends AppCompatActivity {

    public static final int REQUEST_SEARCH_ARTIST = 1;
    public static final String INTENT_DATA_ARTIST = "artist";

    private AddArtistAdapter artistAdapter;
    private AddArtistsViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist_to_library);

        viewModel = new ViewModelProvider(this).get(AddArtistsViewModel.class);

        artistAdapter = new AddArtistAdapter(viewModel);

        viewModel.getArtists().observe(this, new Observer<List<SearchArtist>>() {
            @Override
            public void onChanged(List<SearchArtist> artists) {
                artistAdapter.setArtists(artists);
                artistAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = findViewById(R.id.add_artist_recycler_view);

        recyclerView.setAdapter(artistAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        View searchBar = findViewById(R.id.search_text_layout);

        searchBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddArtistActivity.this , SearchArtistsActivity.class);
                startActivityForResult(intent, REQUEST_SEARCH_ARTIST);
            }
        });

        View done = findViewById(R.id.add_artist_done);
        done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel.requestRandomArtists();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SEARCH_ARTIST && resultCode == RESULT_OK && data != null) {

            Gson gson = RetrofitClient.getInstance().getGson();

            String artistJson = data.getStringExtra(INTENT_DATA_ARTIST);
            SearchArtist artist = gson.fromJson(artistJson, SearchArtist.class);
            artist.setSelected(true);

            artistAdapter.insertAtBeginning(artist);
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
