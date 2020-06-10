package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.ConnectionDialog;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import com.vnoders.spotify_el8alaba.ui.currentUserProfile.FollowAdapter.onFollowClickListener;
import com.vnoders.spotify_el8alaba.ui.library.ArtistFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Mohamed Samy
 * A simple {@link Fragment} subclass. Use the {@link Following#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Following extends Fragment {

    private RecyclerView followingRecyclerView;
    private ProgressBar progressBar;
    private FollowAdapter followingAdapter;
    private RecyclerView.LayoutManager followingLayoutManager;
    private ImageButton backButton;
    private ArrayList<FollowItem> followingList;
    private ArrayList<String> usersIDs;
    private String type;
    private String id;
    private String followers;
    private String name;
    private String imageUrl = "https://i.pinimg.com/originals/94/ac/a9/94aca9b1ffb963a97e68ea11bcd188cb.jpg";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean isUser;
    private String userID;
    private static final String IS_USER ="is_user";
    private static final String ID ="id";


    public Following() {
        // Required empty public constructor
    }

    public static Following newInstance(boolean flagUser, String id) {
        Following fragment = new Following();
        Bundle args = new Bundle();
        args.putString(ID,id);
        args.putBoolean(IS_USER, flagUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usersIDs = getArguments().getStringArrayList("FOLLOWING_IDS");
            isUser=getArguments().getBoolean(IS_USER,false);
            userID=getArguments().getString(ID,"noID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());
        progressBar = view.findViewById(R.id.following_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        followingList = new ArrayList<FollowItem>();
    if(!isUser) {
        Call<List<CurrentUserProfile>> call = RetrofitClient.getInstance().getAPI(API.class)
                .getFollowing();
        call.enqueue(new Callback<List<CurrentUserProfile>>() {
            @Override
            public void onResponse(Call<List<CurrentUserProfile>> call,
                    Response<List<CurrentUserProfile>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null) {
                    followingList = new ArrayList<FollowItem>();
                    String URL = null;
                    for (int i = 0; i < response.body().size(); i++) {
                        String type = response.body().get(i).getType();
                        if (response.body().get(i).getImage() != null) {
                            if (response.body().get(i).getImage().size() != 0) {
                                URL = response.body().get(i).getImage().get(0).getUrl();
                            } else {
                                URL = imageUrl;
                            }
                        } else {
                            URL = imageUrl;
                        }
                        String id = response.body().get(i).getId();
                        String name = response.body().get(i).getName();
                        String followers = response.body().get(i).getFollowers().toString();
                        followingList.add(new FollowItem(name, followers, URL, id, type));
                    }
                    FollowAdapter adapter = new FollowAdapter(followingList);
                    followingRecyclerView.swapAdapter(adapter, false);
                    adapter.setOnFollowClickListener(new onFollowClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String type = followingList.get(position).getmType();
                            if (type.equals("artist")) {
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, ArtistFragment
                                        .newInstance(followingList.get(position).getMid()))
                                        .addToBackStack(null).commit();
                            } else {
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, UserProfile
                                        .newInstance(followingList.get(position).getMid()))
                                        .addToBackStack(null).commit();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<CurrentUserProfile>> call, Throwable t) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(requireActivity().getFragmentManager(), "connection_dialog");
            }
        });

    }
    else{
        getUserFollowing();
    }
        followingAdapter = new FollowAdapter(followingList);
        followingRecyclerView = view.findViewById(R.id.following_recycle_view);
        followingLayoutManager = new LinearLayoutManager(getContext());
        followingRecyclerView.setLayoutManager(followingLayoutManager);
        followingRecyclerView.setAdapter(followingAdapter);
        return view;
    }

    /**
     * this function is called to get a user's followed users to list them in the following list.
     */
    public void getUserFollowing(){
        Call<List<CurrentUserProfile>> call=RetrofitClient.getInstance().getAPI(API.class).getUserFollowing(userID);
        call.enqueue(new Callback<List<CurrentUserProfile>>() {
            @Override
            public void onResponse(Call<List<CurrentUserProfile>> call,
                    Response<List<CurrentUserProfile>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null) {
                    followingList = new ArrayList<FollowItem>();
                    String URL = null;
                    for (int i = 0; i < response.body().size(); i++) {
                        String type = response.body().get(i).getType();
                        if (response.body().get(i).getImage() != null) {
                            if (response.body().get(i).getImage().size() != 0) {
                                URL = response.body().get(i).getImage().get(0).getUrl();
                            } else {
                                URL = imageUrl;
                            }
                        } else {
                            URL = imageUrl;
                        }
                        String id = response.body().get(i).getId();
                        String name = response.body().get(i).getName();
                        String followers = response.body().get(i).getFollowers().toString();
                        followingList.add(new FollowItem(name, followers, URL, id, type));

                    }
                    FollowAdapter adapter = new FollowAdapter(followingList);
                    followingRecyclerView.swapAdapter(adapter, false);
                    adapter.setOnFollowClickListener(new onFollowClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String type = followingList.get(position).getmType();
                            if (type.equals("artist")) {
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, ArtistFragment
                                        .newInstance(followingList.get(position).getMid()))
                                        .addToBackStack(null).commit();
                            } else {
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, UserProfile
                                        .newInstance(followingList.get(position).getMid()))
                                        .addToBackStack(null).commit();
                            }
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call<List<CurrentUserProfile>> call, Throwable t) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(requireActivity().getFragmentManager(), "connection_dialog");
            }
        });

    }
}