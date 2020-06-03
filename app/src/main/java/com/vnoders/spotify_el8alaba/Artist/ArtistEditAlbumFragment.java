package com.vnoders.spotify_el8alaba.Artist;

import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.getArtistAlbums;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.Lists_Adapters.Artist.EditAlbumSongsListAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.AlbumTracks;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistReTrack;
import com.vnoders.spotify_el8alaba.models.Artist.MyTrack;
import com.vnoders.spotify_el8alaba.models.Artist.UpdateAlbumNameRequestBody;
import com.vnoders.spotify_el8alaba.models.Search.Album;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistEditAlbumFragment extends Fragment {

    public static ArrayList<String> selectedSongsIds;
    private Button saveAlbumBtn;
    private Button deleteAlbumBtn;
    private EditText albumNameEditText;
    private ImageView albumImage;
    private String albumName;
    private String albumId;
    private String albumImageUrl;
    private APIInterface apiService;
    private ArrayList<MyTrack> myTracks;
    private RecyclerView songsRecyclerView;

    public ArtistEditAlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_artist_edit_album, container, false);
        albumName = getArguments().getString(ArtistConstantsHelper.ALBUM_NAME);
        albumId = getArguments().getString(ArtistConstantsHelper.ALBUM_ID);
        albumImageUrl = getArguments().getString(ArtistConstantsHelper.ALBUM_IMAGE_URL);
        deleteAlbumBtn = root.findViewById(R.id.edit_album_delete_button);
        saveAlbumBtn = root.findViewById(R.id.edit_album_save_button);
        albumNameEditText = root.findViewById(R.id.edit_album_edit_text);
        albumImage = root.findViewById(R.id.edit_album_image_view);
        selectedSongsIds = new ArrayList<>();
        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);
        songsRecyclerView = root.findViewById(R.id.edit_album_songs_recycler_view);
        myTracks = new ArrayList<>();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        albumNameEditText.setText(albumName);
        albumNameEditText.setHint(albumName);

        Picasso.get().load(albumImageUrl).placeholder(R.drawable.spotify).into(albumImage);

        EditAlbumSongsListAdapter adapter = new EditAlbumSongsListAdapter(this, myTracks);
        songsRecyclerView.setAdapter(adapter);
        songsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        Call<AlbumTracks> call = apiService.getAlbumTracks(albumId);
        call.enqueue(new Callback<AlbumTracks>() {
            @Override
            public void onResponse(Call<AlbumTracks> call, Response<AlbumTracks> response) {
                List<ArtistReTrack> tracks = response.body().getTracks();
                for (int i = 0; i < tracks.size(); i++) {
                    ArtistReTrack track = tracks.get(i);
                    MyTrack myTrack = new MyTrack(track.getId(), track.getPlayed());
                    myTrack.setName(track.getName());
                    myTracks.add(myTrack);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AlbumTracks> call, Throwable t) {

            }
        });

        saveAlbumBtn.setOnClickListener(v -> {
            for (int i = 0; i < selectedSongsIds.size(); i++) {
                Call<Void> call1 = apiService.deleteTrack(selectedSongsIds.get(i));
                call1.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("df", "Failed" + t.getMessage());
                    }
                });
            }
            Call<Album> calls =
                    apiService.updateAlbumName(albumId,
                            new UpdateAlbumNameRequestBody(albumNameEditText.getText().toString()));
            calls.enqueue(new Callback<Album>() {
                @Override
                public void onResponse(Call<Album> call, Response<Album> response) {
                    Toast.makeText(getContext(), "Album Edited successfully", Toast.LENGTH_LONG)
                            .show();
                    getArtistAlbums();
                }

                @Override
                public void onFailure(Call<Album> call, Throwable t) {
                }
            });
        });

        deleteAlbumBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < myTracks.size(); i++) {
                    Call<Void> call1 = apiService.deleteTrack(myTracks.get(i).getId());
                    call1.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
                Call<Void> call1 = apiService.deleteAlbum(albumId);
                call1.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getContext(), "Album Deleted successfully",
                                Toast.LENGTH_LONG).show();
                        getArtistAlbums();
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
