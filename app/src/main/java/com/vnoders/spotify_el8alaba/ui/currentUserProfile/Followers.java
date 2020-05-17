package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass. Use the {@link Followers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Followers extends Fragment {
    private RecyclerView followersRecyclerView;
    private RecyclerView.Adapter followersAdapter;
    private RecyclerView.LayoutManager followersLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Followers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment followers.
     */
    // TODO: Rename and change types and number of parameters
    public static Followers newInstance(String param1, String param2) {
        Followers fragment = new Followers();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_followers, container, false);
        ArrayList<FollowItem> followersList=new ArrayList<FollowItem>();
        followersList.add(new FollowItem("smsm","5",R.drawable.artist_mock));
        followersList.add(new FollowItem("smasm","14",R.drawable.artist_mock));
        followersList.add(new FollowItem("samy","10",R.drawable.artist_mock));
        followersAdapter=new FollowAdapter(followersList);
        followersRecyclerView=view.findViewById(R.id.followers_recycle_view);
        followersLayoutManager=new LinearLayoutManager(getContext());
        followersRecyclerView.setLayoutManager(followersLayoutManager);
        followersRecyclerView.setAdapter(followersAdapter);


        return view;
    }
}
