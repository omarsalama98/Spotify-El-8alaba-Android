package com.vnoders.spotify_el8alaba.ui.library;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.vnoders.spotify_el8alaba.R;

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
                finish();
            }
        });


    }
}
