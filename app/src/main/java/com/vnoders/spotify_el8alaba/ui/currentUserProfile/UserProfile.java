package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.ConnectionDialog;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.RequestBodyIds;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.response.UserProfileData;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. Use the {@link UserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfile extends Fragment {

    private TextView userNameToolbar;
    private AppBarLayout appBarLayout;
    private CircleImageView userImage;
    private Toolbar toolbar;
    private TextView userName;
    private TextView playlistNumber;
    private TextView followingNumber;
    private TextView followerNumber;
    private ImageView bottomSheetButton;
    private Button followingButton;
    private LinearLayout followersLayout;
    private LinearLayout followingLayout;
    private View playListWrap;
    private ProgressBar progressBar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private String imageUrl = "https://i.pinimg.com/originals/94/ac/a9/94aca9b1ffb963a97e68ea11bcd188cb.jpg";
    private ImageView backArrowImage;
    boolean isFollowing = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_ID = "id";

    // TODO: Rename and change types of parameters
    private String mUserId;


    public UserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param userID Parameter 1.
     *
     * @return A new instance of fragment UserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfile newInstance(String userID) {
        UserProfile fragment = new UserProfile();
        Bundle args = new Bundle();
        args.putString(USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        getUserDate();
        followingLayout = view.findViewById(R.id.following_layout);
        followersLayout = view.findViewById(R.id.followers_layout);
        userNameToolbar = view.findViewById(R.id.user_name_toobar);
        userName = view.findViewById(R.id.user_name);
        backArrowImage = view.findViewById(R.id.back_button);
        toolbar = view.findViewById(R.id.toolbar);
        followingButton = view.findViewById(R.id.follow_button);
        appBarLayout = view.findViewById(R.id.appBar);
        userImage = view.findViewById(R.id.user_image);
        bottomSheetButton = view.findViewById(R.id.bottom_sheet_button);
        followerNumber = view.findViewById(R.id.followers_number);
        followingNumber = view.findViewById(R.id.following_numbers);
        GradientUtils.generate(imageUrl, appBarLayout, GradientUtils.GRADIENT_LINEAR_BLACK);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setBackgroundColor(Color.BLACK);
        appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                userNameToolbar
                        .setAlpha(-1.0f * verticalOffset / appBarLayout.getTotalScrollRange());
            }
        });
        followingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followingButton.getText().equals("Follow")) {
                    followUser();
                } else if (followingButton.getText().equals("Unfollow")) {
                    unFollowUser();
                }
            }
        });
        if(followerNumber.getText()=="0"){
            followersLayout.setEnabled(false);
        }
        if(followingNumber.getText()=="0"){
            followingLayout.setEnabled(false);
        }
        followersLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        followingLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }



    public void getUserDate() {
        Call<UserProfileData> userData = RetrofitClient.getInstance().getAPI(API.class)
                .getUserProfileData(mUserId);
        userData.enqueue(new Callback<UserProfileData>() {
            @Override
            public void onResponse(Call<UserProfileData> call, Response<UserProfileData> response) {
                if (response.code() == 200 && response.body() != null) {
                    checkFollowing();
                    userName.setText(response.body().getName());
                    followerNumber.setText(String.valueOf(response.body().getFollowers()));
                    followingNumber.setText(String.valueOf(response.body().getFollowing().size()));
                    userNameToolbar.setText(response.body().getName());
                    if (response.body().getImage() != null) {
                        if (response.body().getImage().size() != 0) {
                            imageUrl = response.body().getImage().get(1).getUrl();
                        }
                    }
                    Picasso.get().load(imageUrl).into(userImage);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserProfileData> call, Throwable t) {

            }
        });
    }

    public void checkFollowing() {
        Call<List<Boolean>> call = RetrofitClient.getInstance().getAPI(API.class)
                .checkFollowing(mUserId);
        call.enqueue(new Callback<List<Boolean>>() {
            @Override
            public void onResponse(Call<List<Boolean>> call, Response<List<Boolean>> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().get(0)) {
                        followingButton.setText("Unfollow");
                        isFollowing = true;
                    } else {
                        followingButton.setText("Follow");
                        isFollowing = false;
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Boolean>> call, Throwable t) {

            }
        });
    }

    public void followUser() {
        List<String> userId = new ArrayList<>();
        userId.add(mUserId);
        RequestBodyIds ids = new RequestBodyIds(userId);
        Call<Void> followUser = RetrofitClient.getInstance().getAPI(API.class).followUser(ids);
        followUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 204) {
                    Toast.makeText(getActivity(), "This user has been followed successfully",
                            Toast.LENGTH_LONG).show();
                    followingButton.setText("Unfollow");
                } else {
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ConnectionDialog connectionDialog = new ConnectionDialog();
                connectionDialog.show(getActivity().getFragmentManager(), "connection_dialog");

            }
        });
    }

    public void unFollowUser() {
        List<String> userId = new ArrayList<>();
        userId.add(mUserId);
        RequestBodyIds ids = new RequestBodyIds(userId);
        Call<Void> unFollowUser = RetrofitClient.getInstance().getAPI(API.class).unFollowUser(ids);
        unFollowUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 204) {
                    Toast.makeText(getActivity(), "This user has been un followed successfully",
                            Toast.LENGTH_LONG).show();
                    followingButton.setText("Follow");
                } else {
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ConnectionDialog connectionDialog = new ConnectionDialog();
                connectionDialog.show(getActivity().getFragmentManager(), "connection_dialog");
            }
        });
    }
}