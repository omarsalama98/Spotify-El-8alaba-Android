package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 * This class implements a bottom sheet containing options of find friends and share your profile.
 */

public class CurrentUserProfileBottomSheet extends BottomSheetDialogFragment {
    private LinearLayout shareLinearLayout;
    private CurrentUserProfile currentUserProfile;
    private CircleImageView userImage;
    private TextView userName;
    private String imageUrl;
    private String name;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUserProfile = (CurrentUserProfile) getArguments()
                    .getSerializable("CURRENT_USER_PROFILE");
            imageUrl = getArguments().getString("image_url");
            name = getArguments().getString("user_name");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.current_user_profile_bottom_sheet,container,false);
    shareLinearLayout=view.findViewById(R.id.share);
    userImage=view.findViewById(R.id.user_image);
    userName=view.findViewById(R.id.user_name);
    userName.setText(name);
    Picasso.get().load(imageUrl).into(userImage);
        shareLinearLayout.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle=new Bundle();
            bundle.putString("image_url",imageUrl);
            bundle.putString("user_name",name);
            bundle.putSerializable("BOTTOM_SHEET",currentUserProfile);
            CurrentUserProfileShareSheet shareSheet=new CurrentUserProfileShareSheet();
            shareSheet.setArguments(bundle);
            dismiss();
            shareSheet.show(getActivity().getSupportFragmentManager(),"CURRENT_USER_PROFILE_SHARE_SHEET");

        }
    });
    return view;
    }
}
