package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.Manifest.permission;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.telephony.mbms.MbmsErrors;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.BuildConfig;
import com.vnoders.spotify_el8alaba.ConnectionDialog;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.UpdateUserInfo;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.core.content.FileProvider;
import java.util.Random;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

/**
 * A simple {@link Fragment} subclass. Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {

    private static final int PERMISSION_GALLERY_CODE = 1001;
    private static final int PERMISSION_CAMERA_CODE = 1000;
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_REQUEST = 1;
    private TextView save;
    private CircleImageView userImage;
    private EditText userName;
    private TextView changeUserImage;
    private String cameraFilePath;
    private Uri imageUri;
    private String newName;
    private CurrentUserProfile currentUserProfile;
    public boolean changeAvatar = false;
    public boolean changeUserName = false;
    private String imageUrl;
    private String name;
    private String absolutePath;


    private TextWatcher userNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save.setEnabled(true);
            changeUserName = true;

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUserProfile = (CurrentUserProfile) getArguments()
                    .getSerializable("CURRENT_USER_PROFILE");
            imageUrl = getArguments().getString("image_url");
            name = getArguments().getString("user_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Bundle bundle = new Bundle();
        imageUri = Uri.EMPTY;
        save = view.findViewById(R.id.save);
        save.setEnabled(false);
        changeUserImage = view.findViewById(R.id.change_user_image);
        userName = view.findViewById(R.id.user_name);
        userImage = view.findViewById(R.id.user_image);
        userName.setText(name);
        Picasso.get().load(imageUrl).into(userImage);
        changeUserImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        userName.addTextChangedListener(userNameTextWatcher);
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeUserName) {
                    UpdateUserInfo updateUserInfo = new UpdateUserInfo();
                    updateUserInfo.setUserName(userName.getText().toString());
                    Call<ResponseBody> updateUserInfoCall = RetrofitClient.getInstance()
                            .getAPI(API.class).updateUserInfo(updateUserInfo);
                    updateUserInfoCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                Response<ResponseBody> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                newName = jsonObject.getString("name");
                                currentUserProfile.setName(newName);
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            //dialog here will be created
                            ConnectionDialog dialog = new ConnectionDialog();
                            dialog.show(getActivity().getFragmentManager(), "connection_dialog");
                        }
                    });
                }
                if (changeAvatar) {
                    sendImage(absolutePath);
                }
                bundle.putSerializable("CURRENT_USER_PROFILE", currentUserProfile);
                CurrentUserProfileFragment currentUserProfileFragment = new CurrentUserProfileFragment();
                currentUserProfileFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity()
                        .getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction
                        .replace(R.id.nav_host_fragment, currentUserProfileFragment,
                                "CURRENT_USER_PROFILE").addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    public void pickImage() {
        AlertDialog.Builder changeImageDialog = new AlertDialog.Builder(getActivity());
        changeImageDialog.setTitle("change profile photo");
        changeImageDialog.setPositiveButton("Choose photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat
                            .checkSelfPermission(getContext(), permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {permission.CAMERA,
                                permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_GALLERY_CODE);
                    } else {
                        openGallery();
                    }
                } else {
                    openGallery();
                }
            }
        });

        changeImageDialog.setNegativeButton("Take photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(), permission.CAMERA)
                            == PackageManager.PERMISSION_DENIED
                            || ContextCompat.checkSelfPermission(getContext(),
                            permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {permission.CAMERA,
                                permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CAMERA_CODE);
                    } else {
                        try {
                            openCamera();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        openCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        changeImageDialog.show();

    }

    /**
     * this function is used to open the device's camera to capture image
     */
    public void openCamera() throws IOException {
        try {
            ContentValues values = new ContentValues();
            values.put(Media.TITLE, "New Picture");
            values.put(Media.DESCRIPTION, "From the camera");
            //imageUri= getActivity().getContentResolver().insert(Media.EXTERNAL_CONTENT_URI,values);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider
                    .getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider",
                            createImageFile()));
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * function used to open gallery to select new avatar and filter the images to jpg,jpeg
     * extensions
     */
    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        String[] mimeTypes = {"image/jpg"};
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    /**
     * this function is used to create file for captured images by camera to get the absolute path
     * which will be sent to the API
     *
     * @return image file
     */

    public File createImageFile() throws IOException {
        byte[] array = new byte[10]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String imageFileName = "SPOTIFY" + generatedString + ".jpg";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .toString());
        Log.d("DIRECTORY", storageDir.toString());
        File image = new File(storageDir, imageFileName);
        cameraFilePath = "file://" + image.getAbsolutePath();
        absolutePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            imageUri=Uri.parse(cameraFilePath);
            userImage.setImageURI(Uri.parse(cameraFilePath));
            changeAvatar = true;
            save.setEnabled(true);
            //sendImage(absolutePath);
        } else if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
            imageUri = data.getData();
            String[] filePathColumn = {Media.DATA};
            Cursor cursor = getActivity().getContentResolver()
                    .query(imageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            absolutePath = cursor.getString(columnIndex);
            cursor.close();
            userImage.setImageURI(imageUri);
            changeAvatar = true;
            save.setEnabled(true);
            //sendImage(absolutePath);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        openCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            case PERMISSION_GALLERY_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
            }
        }
    }

    public void sendImage(String filePath) {
        File file = new File(filePath);
        RequestBody imageUploadRequest = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part
                .createFormData("image", file.getName(), imageUploadRequest);
        Call<ResponseBody> call = RetrofitClient.getInstance().getAPI(API.class)
                .changeProfilePicture(part);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

}
