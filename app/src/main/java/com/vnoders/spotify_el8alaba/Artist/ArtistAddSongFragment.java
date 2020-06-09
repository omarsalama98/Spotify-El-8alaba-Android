package com.vnoders.spotify_el8alaba.Artist;

import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.mAlbums;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.Artist.AddSongAlbumsListAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistAlbum;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistTrack;
import com.vnoders.spotify_el8alaba.models.Artist.CreateATrackRequestBody;
import com.vnoders.spotify_el8alaba.models.Artist.MyAlbum;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.FileUtils;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.io.File;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistAddSongFragment extends Fragment {

    public static String selectedAlbumId;
    private static int REQUEST_CODE = 7;
    private TextView enterSongNameError, chooseSongError, chooseAlbumError, songPathTextView;
    private EditText songNameEditText;
    private Button addSongBtn, cancelBtn, chooseSongBtn;
    private RecyclerView albumsListRecyclerView;
    private CheckBox explicit;
    private Uri audioUri;
    private APIInterface apiService;
    private String songId;
    private ProgressBar uploadingProgress;
    private TextView uploadingText;
    private boolean isUploading = false;

    public ArtistAddSongFragment() {
        // Required empty public constructor
    }

    private void uploadTrack(RequestBody description, MultipartBody.Part body) {
        isUploading = true;
        uploadingProgress.setVisibility(View.VISIBLE);
        uploadingText.setVisibility(View.VISIBLE);

        Call<ResponseBody> call = apiService.uploadTrack(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Uploaded Song Successfully", Toast.LENGTH_LONG)
                            .show();
                    ArtistMainActivity.getArtistTopTracks();
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getContext(), "Something went wrong. Try again later",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong. Try again later",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private static boolean hasNoPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null
                && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_artist_add_song, container, false);

        enterSongNameError = root.findViewById(R.id.enter_song_name_error_text_view);
        chooseSongError = root.findViewById(R.id.choose_song_error_text_view);
        chooseAlbumError = root.findViewById(R.id.choose_album_error_text_view);
        songPathTextView = root.findViewById(R.id.song_path_text_view);
        songNameEditText = root.findViewById(R.id.song_name_edit_text);
        addSongBtn = root.findViewById(R.id.add_song_confirm_button);
        cancelBtn = root.findViewById(R.id.add_song_cancel_button);
        chooseSongBtn = root.findViewById(R.id.choose_song_button);
        albumsListRecyclerView = root.findViewById(R.id.add_song_albums_recycler_view);
        explicit = root.findViewById(R.id.explicit_check);
        uploadingProgress = root.findViewById(R.id.uploading_progress_bar);
        uploadingText = root.findViewById(R.id.uploading_text);
        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        AddSongAlbumsListAdapter adapter = new AddSongAlbumsListAdapter(this, albums);
        albumsListRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        albumsListRecyclerView.setAdapter(adapter);

        chooseSongBtn.setOnClickListener(v -> {
            if (hasNoPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Permission ask
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            }
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            startActivityForResult(intent, REQUEST_CODE);
        });

        cancelBtn.setOnClickListener(v -> getActivity().onBackPressed());

        addSongBtn.setOnClickListener(v -> {
            if (isUploading) {
                return;
            }
            String songName = songNameEditText.getText().toString();
            String songPath = songPathTextView.getText().toString();

            if (songName.isEmpty()) {
                enterSongNameError.setVisibility(View.VISIBLE);
                return;
            } else {
                enterSongNameError.setVisibility(View.GONE);
            }
            if (selectedAlbumId == null) {
                chooseAlbumError.setVisibility(View.VISIBLE);
                return;
            } else {
                chooseAlbumError.setVisibility(View.GONE);
            }
            if (songPath.isEmpty()) {
                chooseSongError.setVisibility(View.VISIBLE);
                return;
            } else {
                chooseSongError.setVisibility(View.GONE);
            }
            File file = new File(FileUtils.getPath(getContext(), audioUri));

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(getActivity().getContentResolver().getType(audioUri)),
                            file);
            CreateATrackRequestBody requestBody = new CreateATrackRequestBody(songName,
                    selectedAlbumId, explicit.isChecked());

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(getContext(), audioUri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int millSecond = Integer.parseInt(durationStr);
            requestBody.setDuration(millSecond);

            Call<ArtistTrack> call = apiService.createTrack(requestBody);
            call.enqueue(new Callback<ArtistTrack>() {
                @Override
                public void onResponse(Call<ArtistTrack> call, Response<ArtistTrack> response) {
                    songId = response.body().getId();
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("track", file.getName(), requestFile);
                    RequestBody description =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, songId);
                    if (hasNoPermissions(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
                    } else {
                        uploadTrack(description, body);
                    }
                }
                @Override
                public void onFailure(Call<ArtistTrack> call, Throwable t) {
                }
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            audioUri = data.getData(); //declared above Uri audio;
            Log.d("media", "onActivityResult: " + audioUri);
            songPathTextView.setText(audioUri.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
