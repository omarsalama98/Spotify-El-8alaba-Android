package com.vnoders.spotify_el8alaba.Artist;

import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.apiService;
import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.getArtistTopTracks;
import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.mAlbums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.Artist.EditSongAlbumsListAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistAlbum;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistTrack;
import com.vnoders.spotify_el8alaba.models.Artist.MyAlbum;
import com.vnoders.spotify_el8alaba.models.Artist.UpdateSongNameAndAlbumRequestBody;
import com.vnoders.spotify_el8alaba.models.Artist.UpdateSongNameRequestBody;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A {@link Fragment} class for editing a song.
 */
public class ArtistEditSongFragment extends Fragment {

    public static String selectedAlbumId = null;
    private Button saveSongBtn;
    private Button deleteSongBtn;
    private EditText songNameEditText;
    private String songName;
    private String songId;
    private RecyclerView albumsRecyclerView;

    public ArtistEditSongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_artist_edit_song, container, false);

        songName = getArguments().getString(ArtistConstantsHelper.SONG_NAME);
        songId = getArguments().getString(ArtistConstantsHelper.SONG_ID);
        deleteSongBtn = root.findViewById(R.id.edit_song_delete_button);
        saveSongBtn = root.findViewById(R.id.edit_song_save_button);
        songNameEditText = root.findViewById(R.id.edit_song_edit_text);
        albumsRecyclerView = root.findViewById(R.id.edit_song_albums_recycler_view);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songNameEditText.setText(songName);
        songNameEditText.setHint(songName);

        ArrayList<MyAlbum> albums = new ArrayList<>();
        for (int i = 0; i < mAlbums.size(); i++) {
            ArtistAlbum album = mAlbums.get(i);
            MyAlbum myAlbum = new MyAlbum(album.getId());
            myAlbum.setSelected(false);
            myAlbum.setName(album.getName());
            if (!album.getImages().isEmpty()) {
                myAlbum.setImgUrl(album.getImages().get(0).getUrl());
            }
            albums.add(myAlbum);
        }

        EditSongAlbumsListAdapter albumsListAdapter = new EditSongAlbumsListAdapter(this, albums);
        albumsRecyclerView.setAdapter(albumsListAdapter);
        albumsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        saveSongBtn.setOnClickListener(v -> {
            if (selectedAlbumId != null) {
                UpdateSongNameAndAlbumRequestBody updateSongNameAndAlbumRequestBody = new UpdateSongNameAndAlbumRequestBody();
                updateSongNameAndAlbumRequestBody.setAlbumId(selectedAlbumId);
                String editTextSongName = songNameEditText.getText().toString();
                if (editTextSongName.equals("")) {
                    editTextSongName = songName;
                }
                updateSongNameAndAlbumRequestBody.setSongName(editTextSongName);
                Call<SearchTrack> call = apiService
                        .updateTrack(songId, updateSongNameAndAlbumRequestBody);
                call.enqueue(new Callback<SearchTrack>() {
                    @Override
                    public void onResponse(Call<SearchTrack> call,
                            Response<SearchTrack> response) {
                        Toast.makeText(getContext(), "Song Edited successfully", Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(Call<SearchTrack> call, Throwable t) {
                    }
                });
            } else {
                UpdateSongNameRequestBody updateSongNameRequestBody = new UpdateSongNameRequestBody();
                String editTextSongName = songNameEditText.getText().toString();
                if (editTextSongName.equals("")) {
                    editTextSongName = songName;
                }
                updateSongNameRequestBody.setSongName(editTextSongName);
                Call<ArtistTrack> call = apiService
                        .updateTrackName(songId, updateSongNameRequestBody);
                call.enqueue(new Callback<ArtistTrack>() {
                    @Override
                    public void onResponse(Call<ArtistTrack> call,
                            Response<ArtistTrack> response) {
                        Toast.makeText(getContext(), "Song Edited successfully", Toast.LENGTH_LONG)
                                .show();
                        getArtistTopTracks();
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ArtistTrack> call, Throwable t) {
                    }
                });
            }
        });

        deleteSongBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = apiService.deleteTrack(songId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getContext(), "Song Deleted successfully", Toast.LENGTH_LONG)
                                .show();
                        getArtistTopTracks();
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });


    }
}
