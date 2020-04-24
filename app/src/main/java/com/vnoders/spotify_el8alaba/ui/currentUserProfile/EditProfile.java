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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.core.content.FileProvider;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {
    private static final int PERMISSION_GALLERY_CODE=1001;
    private static final int PERMISSION_CAMERA_CODE=1000;
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
    // TODO: Rename and change types of parameters
    private String imageUrl;
    private String name;

    public EditProfile() {
        // Required empty public constructor
    }
    private TextWatcher userNameTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment EditProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUserProfile=(CurrentUserProfile) getArguments().getSerializable("CURRENT_USER_PROFILE");
            imageUrl = getArguments().getString("image_url");
            name = getArguments().getString("user_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Bundle bundle=new Bundle();
        imageUri=Uri.EMPTY;
        save=view.findViewById(R.id.save);
        save.setEnabled(false);
        changeUserImage=view.findViewById(R.id.change_user_image);
        userName=view.findViewById(R.id.user_name);
        userImage=view.findViewById(R.id.user_image);
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
                UpdateUserInfo updateUserInfo=new UpdateUserInfo();
                updateUserInfo.setUserName(userName.getText().toString());
                Call<ResponseBody> updateUserInfoCall=RetrofitClient.getInstance().getAPI(API.class).updateUserInfo(updateUserInfo);
                updateUserInfoCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                            Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().string());
                            newName=jsonObject.getString("name");
                            if(!Uri.EMPTY.equals(imageUri)){
                               // bundle.putString("image_uri",imageUri);
                            }
                            currentUserProfile.setName(newName);
                            bundle.putSerializable("CURRENT_USER_PROFILE",currentUserProfile);
                            CurrentUserProfileFragment currentUserProfileFragment=new CurrentUserProfileFragment();
                            currentUserProfileFragment.setArguments(bundle);
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment,currentUserProfileFragment,"CURRENT_USER_PROFILE").addToBackStack(null).commit();

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
        });

        return view;

    }
public void pickImage(){
    AlertDialog.Builder changeImageDialog=new AlertDialog.Builder(getActivity());
    changeImageDialog.setTitle("change profile photo");
    changeImageDialog.setPositiveButton("Choose photo", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getContext(), permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {permission.CAMERA, permission.READ_EXTERNAL_STORAGE};
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

            if(VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(getContext(), permission.CAMERA)== PackageManager.PERMISSION_DENIED||ContextCompat.checkSelfPermission(getContext(),
                        permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    String[] permissions={permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions,PERMISSION_CAMERA_CODE);
                }
                else{
                    openCamera();
                }
            }
            else {
                openCamera();
            }
        }
    });
changeImageDialog.show();

}

public void openCamera(){
    ContentValues values=new ContentValues();
    values.put(Media.TITLE,"New Picture");
    values.put(Media.DESCRIPTION,"From the camera");
    imageUri= getActivity().getContentResolver().insert(Media.EXTERNAL_CONTENT_URI,values);
    Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    startActivityForResult(cameraIntent, CAMERA_REQUEST);

}

public void openGallery(){
    Intent galleryIntent=new Intent(Intent.ACTION_PICK);
    galleryIntent.setType("image/*");
    String[] mimeTypes = {"image/jpeg", "image/png"};
    galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
    startActivityForResult(galleryIntent,GALLERY_REQUEST);
}

/*public File createImageFile() throws IOException{

  SimpleDateFormat fomatter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
    Date date=new Date();
    //String timeStampDate= DateFormat.getDateInstance().format(date);
    String timeStampDate=fomatter.format(date);
    String imageFileName = "JPEG_" + timeStampDate + "_";
    File storageDir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM), "Camera");
    File image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
    );
    cameraFilePath = "file://" + image.getAbsolutePath();
    return image;


}*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==CAMERA_REQUEST){
            userImage.setImageURI(imageUri);
        }
        else if(resultCode==RESULT_OK&&requestCode==GALLERY_REQUEST){
            imageUri=data.getData();
            userImage.setImageURI(imageUri);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
    switch (requestCode){
        case PERMISSION_CAMERA_CODE:{
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){openCamera();}
        }
        case PERMISSION_GALLERY_CODE:{
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){openGallery();}
        }
    }
    }

}
