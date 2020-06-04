package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.ConnectionDialog;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import retrofit2.Call;
import com.vnoders.spotify_el8alaba.response.UserProfile;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. Use the {@link Following#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Following extends Fragment {
    private RecyclerView followingRecyclerView;
    private ProgressBar progressBar;
    private RecyclerView.Adapter followingAdapter;
    private RecyclerView.LayoutManager followingLayoutManager;
    private ImageButton backButton;
    private ArrayList<FollowItem> followingList;
    private ArrayList<String> usersIDs;
    private String type;
    private String id;
    private String followers;
    private String name;
    private  String imageUrl ="https://i.pinimg.com/originals/94/ac/a9/94aca9b1ffb963a97e68ea11bcd188cb.jpg";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Following() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Following newInstance(String param1, String param2) {
        Following fragment = new Following();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usersIDs=getArguments().getStringArrayList("FOLLOWING_IDS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_following, container, false);
        backButton=view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());
        progressBar=view.findViewById(R.id.following_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        followingList=new ArrayList<FollowItem>();
        for (int i=0;i<usersIDs.size();i++){
            Call<UserProfile> requestUserData= RetrofitClient.getInstance().getAPI(API.class).getUserProfileData(usersIDs.get(i));
            requestUserData.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    progressBar.setVisibility(View.GONE);
                    if(response.code()==200){
                    type=response.body().getType();
                    id=response.body().getId();
                    name=response.body().getName();
                    followers=response.body().getFollowers().toString();
                    followingList.add(new FollowItem(name,followers,imageUrl,id,type));
                    RecyclerView.Adapter adapter = new FollowAdapter(followingList);
                    followingRecyclerView.swapAdapter(adapter, false);
                    }
                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {
                    ConnectionDialog dialog = new ConnectionDialog();
                    dialog.show(getActivity().getFragmentManager(), "connection_dialog");
                }
            });
        }


        followingAdapter=new FollowAdapter(followingList);
        followingRecyclerView =view.findViewById(R.id.following_recycle_view);
        followingLayoutManager=new LinearLayoutManager(getContext());
        followingRecyclerView.setLayoutManager(followingLayoutManager);
        followingRecyclerView.setAdapter(followingAdapter);
        return view;
    }
}