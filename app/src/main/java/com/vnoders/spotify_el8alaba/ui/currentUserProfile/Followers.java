package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass. Use the {@link Followers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Followers extends Fragment {

    private RecyclerView followersRecyclerView;
    private FollowAdapter followersAdapter;
    private RecyclerView.LayoutManager followersLayoutManager;
    private ProgressBar progressBar;
    private String imageUrl = "https://i.pinimg.com/originals/94/ac/a9/94aca9b1ffb963a97e68ea11bcd188cb.jpg";
    private ImageButton backButton;
    private ArrayList<FollowItem> followersList;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<String>followingList;
    private boolean isUser=false;
    private static final String IS_USER ="is_user";
    private static final String ID ="id";
    private String userID;
    public Followers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     *
     * @return A new instance of fragment followers.
     */
    // TODO: Rename and change types and number of parameters
    public static Followers newInstance(boolean flagUser,String id) {
        Followers fragment = new Followers();
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
            followingList=getArguments().getStringArrayList("FOLLOWING_IDS");
            isUser=getArguments().getBoolean(IS_USER,false);
            userID=getArguments().getString(ID,"noID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        progressBar = view.findViewById(R.id.followers_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        backButton = view.findViewById(R.id.back_button_followers);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());
        followersList = new ArrayList<FollowItem>();
        if(!isUser) {
            Call<List<CurrentUserProfile>> call = RetrofitClient.getInstance().getAPI(API.class)
                    .getFollowers();
            call.enqueue(new Callback<List<CurrentUserProfile>>() {
                @Override
                public void onResponse(Call<List<CurrentUserProfile>> call,
                        Response<List<CurrentUserProfile>> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.code() == 200 && response.body() != null) {
                        followersList = new ArrayList<FollowItem>();
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
                            followersList.add(new FollowItem(name, followers, URL, id, type));
                        }
                        FollowAdapter adapter = new FollowAdapter(followersList, followingList);
                        followersRecyclerView.swapAdapter(adapter, false);
                        adapter.setOnFollowClickListener(new onFollowClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                String type = followersList.get(position).getmType();
                                if (type.equals("artist")) {
                                    fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction
                                            .replace(R.id.nav_host_fragment, ArtistFragment
                                                    .newInstance(
                                                            followersList.get(position).getMid()))
                                            .addToBackStack(null).commit();
                                } else {
                                    fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.nav_host_fragment, UserProfile
                                            .newInstance(followersList.get(position).getMid()))
                                            .addToBackStack(null).commit();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<CurrentUserProfile>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    ConnectionDialog dialog = new ConnectionDialog();
                    dialog.show(getActivity().getFragmentManager(), "connection_dialog");
                }
            });
        }
        else{
            getUserFollowers();
        }
        followersAdapter = new FollowAdapter(followersList,followingList);
        followersRecyclerView = view.findViewById(R.id.followers_recycle_view);
        followersLayoutManager = new LinearLayoutManager(getContext());
        followersRecyclerView.setLayoutManager(followersLayoutManager);
        followersRecyclerView.setAdapter(followersAdapter);

        return view;
    }
    public void getUserFollowers(){
        Call<List<CurrentUserProfile>> call =RetrofitClient.getInstance().getAPI(API.class).getUserFollowers(userID);
        call.enqueue(new Callback<List<CurrentUserProfile>>() {
            @Override
            public void onResponse(Call<List<CurrentUserProfile>> call,
                    Response<List<CurrentUserProfile>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null) {
                    followersList = new ArrayList<FollowItem>();
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
                        followersList.add(new FollowItem(name, followers, URL, id, type));
                    }
                    FollowAdapter adapter = new FollowAdapter(followersList, followingList);
                    followersRecyclerView.swapAdapter(adapter, false);
                    adapter.setOnFollowClickListener(new onFollowClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String type = followersList.get(position).getmType();
                            if (type.equals("artist")) {
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction
                                        .replace(R.id.nav_host_fragment, ArtistFragment
                                                .newInstance(
                                                        followersList.get(position).getMid()))
                                        .addToBackStack(null).commit();
                            } else {
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, UserProfile
                                        .newInstance(followersList.get(position).getMid()))
                                        .addToBackStack(null).commit();
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<CurrentUserProfile>> call, Throwable t) {

            }
        });

    }


}
