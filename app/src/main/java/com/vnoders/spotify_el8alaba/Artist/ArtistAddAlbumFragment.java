package com.vnoders.spotify_el8alaba.Artist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.CreateAnAlbumRequestBody;
import com.vnoders.spotify_el8alaba.models.Search.Album;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.FileUtils;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.io.File;
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
public class ArtistAddAlbumFragment extends Fragment implements OnCheckedChangeListener {

    private static int REQUEST_CODE = 9;
    private TextView enterAlbumNameError, enterAlbumLabelError;
    private EditText albumNameEditText, albumLabelEditText;
    private Button addAlbumBtn, cancelBtn, chooseAlbumPicBtn;
    private ImageView albumImage;
    private Uri imageUri = null;
    private APIInterface apiService;
    private File file;
    private Boolean imgFileExists = false;
    private CheckBox popCB, rockCB, electronicCB, hipHopCB, metalCB, classicalCB, folkCB, orientalCB, reggaeCB;

    public ArtistAddAlbumFragment() {
        // Required empty public constructor
    }

    private void uploadAlbumImage(String albumId, MultipartBody.Part body) {
        Log.d("LOL", "XD");
        Call<ResponseBody> call1 = apiService.uploadAlbumImage(albumId, body);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "Created Album Successfully", Toast.LENGTH_LONG)
                        .show();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong with your image.",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private static boolean hasPermissions(Context context, String... permissions) {
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
        View root = inflater.inflate(R.layout.fragment_artist_add_album, container, false);

        enterAlbumNameError = root.findViewById(R.id.enter_album_name_error_text_view);
        enterAlbumLabelError = root.findViewById(R.id.enter_album_label_error_text_view);
        albumNameEditText = root.findViewById(R.id.album_name_edit_text);
        albumLabelEditText = root.findViewById(R.id.album_label_edit_text);
        addAlbumBtn = root.findViewById(R.id.add_album_confirm_button);
        cancelBtn = root.findViewById(R.id.add_album_cancel_button);
        chooseAlbumPicBtn = root.findViewById(R.id.choose_album_picture_button);
        albumImage = root.findViewById(R.id.album_selected_image_image_view);
        hipHopCB = root.findViewById(R.id.hiphop_genre_checkbox);
        rockCB = root.findViewById(R.id.rock_genre_checkbox);
        orientalCB = root.findViewById(R.id.oriental_genre_checkbox);
        popCB = root.findViewById(R.id.pop_genre_checkbox);
        electronicCB = root.findViewById(R.id.electronic_genre_checkbox);
        reggaeCB = root.findViewById(R.id.reggae_genre_checkbox);
        metalCB = root.findViewById(R.id.metal_genre_checkbox);
        classicalCB = root.findViewById(R.id.classical_genre_checkbox);
        folkCB = root.findViewById(R.id.folk_genre_checkbox);

        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chooseAlbumPicBtn.setOnClickListener(v -> {
            if (hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            }
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE);
        });

        popCB.setOnCheckedChangeListener(this);
        hipHopCB.setOnCheckedChangeListener(this);
        rockCB.setOnCheckedChangeListener(this);
        electronicCB.setOnCheckedChangeListener(this);
        orientalCB.setOnCheckedChangeListener(this);
        metalCB.setOnCheckedChangeListener(this);
        classicalCB.setOnCheckedChangeListener(this);
        reggaeCB.setOnCheckedChangeListener(this);
        folkCB.setOnCheckedChangeListener(this);
        popCB.setChecked(true);

        addAlbumBtn.setOnClickListener(v -> {
            String albumName = albumNameEditText.getText().toString();
            String albumLabel = albumLabelEditText.getText().toString();

            if (albumName.isEmpty()) {
                enterAlbumNameError.setVisibility(View.VISIBLE);
                return;
            } else {
                enterAlbumNameError.setVisibility(View.GONE);
            }
            if (albumLabel.isEmpty()) {
                enterAlbumLabelError.setVisibility(View.VISIBLE);
                return;
            } else {
                enterAlbumLabelError.setVisibility(View.GONE);
            }

            CreateAnAlbumRequestBody requestBody = new CreateAnAlbumRequestBody();
            requestBody.setAlbumName(albumName);
            requestBody.setLabel(albumLabel);
            if (rockCB.isChecked()) {
                requestBody.addGenre("rock");
            }
            if (popCB.isChecked()) {
                requestBody.addGenre("pop");
            }
            if (hipHopCB.isChecked()) {
                requestBody.addGenre("hip hop");
            }
            if (folkCB.isChecked()) {
                requestBody.addGenre("folk");
            }
            if (metalCB.isChecked()) {
                requestBody.addGenre("metal");
            }
            if (orientalCB.isChecked()) {
                requestBody.addGenre("oriental");
            }
            if (classicalCB.isChecked()) {
                requestBody.addGenre("classical");
            }
            if (reggaeCB.isChecked()) {
                requestBody.addGenre("reggae");
            }
            if (electronicCB.isChecked()) {
                requestBody.addGenre("electronic");
            }
            Call<Album> call = apiService.createAlbum(requestBody);
            call.enqueue(new Callback<Album>() {
                @Override
                public void onResponse(Call<Album> call, Response<Album> response) {
                    Log.d("d", response.body().getName() + " -- " + response.body().getId());
                    if (imgFileExists) {
                        file = new File(FileUtils.getPath(getContext(), imageUri));
                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse(getActivity().getContentResolver()
                                                .getType(imageUri)),
                                        file
                                );
                        MultipartBody.Part body =
                                MultipartBody.Part
                                        .createFormData("image", file.getName(), requestFile);
                        Log.d("d", response.body().getName() + " -- " + response.body().getId());
                        uploadAlbumImage(response.body().getId(), body);
                    } else {
                        Toast.makeText(getContext(), "Created Album Successfully",
                                Toast.LENGTH_LONG)
                                .show();
                        getActivity().onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<Album> call, Throwable t) {
                }
            });
        });

        cancelBtn.setOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData(); //declared above Uri audio;
            Log.d("media", "onActivityResult: " + imageUri);
            File imgFile = new File(FileUtils.getPath(getContext(), imageUri));
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                albumImage.setImageBitmap(myBitmap);
                albumImage.setVisibility(View.VISIBLE);
                imgFileExists = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!rockCB.isChecked() && !popCB.isChecked() && !hipHopCB.isChecked() &&
                !electronicCB.isChecked() && !metalCB.isChecked() && !orientalCB.isChecked() &&
                !reggaeCB.isChecked() && !classicalCB.isChecked() && !folkCB.isChecked()) {
            buttonView.setChecked(true);
        }
    }
}
