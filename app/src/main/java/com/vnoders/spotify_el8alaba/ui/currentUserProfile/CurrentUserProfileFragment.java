package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SettingsList;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass. Use the {@link CurrentUserProfileFragment#newInstance}
 * factory method to create an instance of this fragment.
 */
public class CurrentUserProfileFragment extends Fragment {
    private CurrentUserProfile currentUserProfile;
    private TextView userNameToolbar;
    private AppBarLayout appBarLayout;
    private CircleImageView userImage;
    private Toolbar toolbar;
    private TextView userName;
    private TextView playlistNumber;
    private TextView followingNumber;
    private TextView followerNumber;
    private ImageView bottomSheetButton;
    private Button editProfileButton;
    private Bundle bundle;
    private LinearLayout followersLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String IMAGE_URL ="https://i.pinimg.com/originals/94/ac/a9/94aca9b1ffb963a97e68ea11bcd188cb.jpg";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView backArrowImage;

    public CurrentUserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment CurrentUserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentUserProfileFragment newInstance(String param1, String param2) {
        CurrentUserProfileFragment fragment = new CurrentUserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUserProfile=(CurrentUserProfile) getArguments().getSerializable("CURRENT_USER_PROFILE");
            //userName=getArguments().getString("user_name");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_current_user_profile, container, false);
        followersLayout=root.findViewById(R.id.followers_layout);
        userNameToolbar=root.findViewById(R.id.user_name_toobar);
        userName=root.findViewById(R.id.user_name);
        backArrowImage = root.findViewById(R.id.back_button);
        toolbar=root.findViewById(R.id.toolbar);
        appBarLayout=root.findViewById(R.id.appBar);
        userImage=root.findViewById(R.id.user_image);
        bottomSheetButton=root.findViewById(R.id.bottom_sheet_button);
        followerNumber=root.findViewById(R.id.followers_number);
        followingNumber=root.findViewById(R.id.following_numbers);
        playlistNumber=root.findViewById(R.id.playlist_number);
        editProfileButton=root.findViewById(R.id.edit_profile_button);
        //this will be replaced by a user image from currentUserProfile later
        Picasso.get().load(IMAGE_URL).into(userImage);
        GradientUtils.generate(IMAGE_URL,appBarLayout,GradientUtils.GRADIENT_LINEAR_BLACK);
        GradientUtils.generate(IMAGE_URL,toolbar,GradientUtils.SOLID_DOMINANT_COLOR);
        followerNumber.setText(currentUserProfile.getFollowers().toString());
        userName.setText(currentUserProfile.getName());
        userNameToolbar.setText(currentUserProfile.getName());
        playlistNumber.setText(String.valueOf(currentUserProfile.getFollowedPlaylists().size()));
        followingNumber.setText(String.valueOf(currentUserProfile.getFollowing().size()));
        appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                userNameToolbar.setAlpha(-1.0f * verticalOffset / appBarLayout.getTotalScrollRange());


            }
        });
        followersLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Followers followers=new Followers();
                fragmentManager=getActivity().getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,followers,"FOLLOWERS").addToBackStack(null).commit();
            }
        });
        editProfileButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle=new Bundle();
                //will be uncommented when a user with image is created
               // bundle.putString("image_url",currentUserProfile.getImage().getUrl());
                bundle.putString("image_url",IMAGE_URL);
                bundle.putString("user_name",currentUserProfile.getName());
                bundle.putSerializable("CURRENT_USER_PROFILE",currentUserProfile);
                EditProfile editProfile=new EditProfile();
                editProfile.setArguments(bundle);
                fragmentManager=getActivity().getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,editProfile,"EDIT_PROFILE").addToBackStack(null).commit();
            }
        });
        bottomSheetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUserProfileBottomSheet currentUserProfileBottomSheet=new CurrentUserProfileBottomSheet();
                currentUserProfileBottomSheet.show(getActivity().getSupportFragmentManager(),"CURRENT_USER_PROFILE_BOTTOM_SHEET");

            }
        });
        backArrowImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsList settingsList =new SettingsList();
                fragmentManager=getActivity().getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,settingsList,"SETTING_LIST").addToBackStack(null).commit();
            }
        });

        return root;
    }

}
