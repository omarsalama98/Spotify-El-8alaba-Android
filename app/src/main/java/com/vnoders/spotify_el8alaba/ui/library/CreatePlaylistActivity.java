package com.vnoders.spotify_el8alaba.ui.library;

import static com.vnoders.spotify_el8alaba.ui.library.LibraryPlaylistFragment.REQUEST_DATA_CREATED_PLAYLIST_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;

public class CreatePlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        EditText playlistName = findViewById(R.id.create_playlist_name);
        Button createPlaylist = findViewById(R.id.create_playlist_create);
        Button cancel = findViewById(R.id.create_playlist_cancel);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        MutableLiveData<String> createdPlaylistId = new MediatorLiveData<>();

        createdPlaylistId.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String playlistId) {
                Intent intent = new Intent();
                intent.putExtra(REQUEST_DATA_CREATED_PLAYLIST_ID , playlistId);
                setResult(RESULT_OK,intent);

                finish();
            }
        });

        createPlaylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistNameString = playlistName.getText().toString().trim();
                if (!playlistNameString.isEmpty()) {
                    LibraryRepository.createPlaylist(playlistNameString, createdPlaylistId);
                }
            }
        });

    }
}
