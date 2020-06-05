package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import java.util.List;

public class AddArtistActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist_to_library);

        AddArtistsViewModel viewModel = new ViewModelProvider(this).get(AddArtistsViewModel.class);

        AddArtistAdapter artistAdapter = new AddArtistAdapter(viewModel);

        viewModel.getArtists().observe(this, new Observer<List<SearchArtist>>() {
            @Override
            public void onChanged(List<SearchArtist> artists) {
                artistAdapter.submitList(artists);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.add_artist_recycler_view);

        recyclerView.setAdapter(artistAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        View searchBar = findViewById(R.id.search_text_layout);

        searchBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddArtistActivity.this , SearchArtistsActivity.class);
                startActivity(intent);
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
}
