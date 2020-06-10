package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;

/**
 * @author Mohamed Samy
 *
 * this class is to implement share profile bottom sheet containing options of sharing your profile
 * via whatsapp,facebook,messanger and other applications.
 */

public class CurrentUserProfileShareSheet extends BottomSheetDialogFragment {
    private ImageView userImage;
    private TextView userName;
    private String imageUrl;
    private String name;
    private CurrentUserProfile currentUserProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            currentUserProfile = (CurrentUserProfile) getArguments()
                    .getSerializable("BOTTOM_SHEET");
            imageUrl = getArguments().getString("image_url");
            name = getArguments().getString("user_name");

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_user_profile_share_sheet, container, false);
        userImage=view.findViewById(R.id.user_image);
        userName=view.findViewById(R.id.user_name);
        userName.setText(name);
        Picasso.get().load(imageUrl).into(userImage);
        return view;
    }
}
