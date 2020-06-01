package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.Artist;
import java.util.ArrayList;

public class AddArtistActivity extends AppCompatActivity {

    private ArrayList<Artist> artists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist_to_library);

        artists = new ArrayList<>();

        artists.add(new Artist("Artist 1 Name"));
        artists.add(new Artist("Artist 2 Name"));
        artists.add(new Artist("Artist 3 Name"));
        artists.add(new Artist("Artist 4 Name"));
        artists.add(new Artist("Artist 5 Name"));
        artists.add(new Artist("Artist 6 Name"));
        artists.add(new Artist("Artist 7 Name"));
        artists.add(new Artist("Artist 8 Name"));
        artists.add(new Artist("Artist 9 Name"));
        artists.add(new Artist("Artist 10 Name"));
        artists.add(new Artist("Artist 11 Name"));
        artists.add(new Artist("Artist 12 Name"));
        artists.add(new Artist("Artist 13 Name"));
        artists.add(new Artist("Artist 14 Name"));
        artists.add(new Artist("Artist 15 Name"));

        AddArtistAdapter artistAdapter = new AddArtistAdapter(artists);

        RecyclerView recyclerView = findViewById(R.id.add_artist_recycler_view);

        recyclerView.setAdapter(artistAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        View search_layout = findViewById(R.id.search_text_layout);

        View done = findViewById(R.id.add_artist_done);
        done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
