package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
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
    private RecyclerView.Adapter followersAdapter;
    private RecyclerView.LayoutManager followersLayoutManager;
    private ProgressBar progressBar;
    private  String imageUrl ="https://i.pinimg.com/originals/94/ac/a9/94aca9b1ffb963a97e68ea11bcd188cb.jpg";
    private ImageButton backButton;
    private ArrayList<FollowItem> followersList;
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
        progressBar=view.findViewById(R.id.followers_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        backButton=view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());
        followersList=new ArrayList<FollowItem>();

        Call<List<CurrentUserProfile>> call= RetrofitClient.getInstance().getAPI(API.class).getFollowers();
        call.enqueue(new Callback<List<CurrentUserProfile>>() {
            @Override
            public void onResponse(Call<List<CurrentUserProfile>> call,
                    Response<List<CurrentUserProfile>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.code()==200&&response.body()!=null) {
                    followersList=new ArrayList<FollowItem>();
                    String URL=null;
                    for (int i = 0; i < response.body().size(); i++) {
                        String type=response.body().get(i).getType();
                        if(response.body().get(i).getImage()!=null) {
                            if(response.body().get(i).getImage().size()!=0) {
                                Log.d("FINDING CRASHING", String.valueOf(i));
                                URL = response.body().get(i).getImage().get(0).getUrl();
                            }
                            else{
                                URL=imageUrl;
                            }
                            }
                        else{
                            URL=imageUrl;}
                        String id=response.body().get(i).getId();
                        String name=response.body().get(i).getName();
                        String followers=response.body().get(i).getFollowers().toString();
                        followersList.add(new FollowItem(name,followers,URL,id,type));
                    }
                    RecyclerView.Adapter adapter= new FollowAdapter(followersList);
                    followersRecyclerView.swapAdapter(adapter,false);

                }
            }

            @Override
            public void onFailure(Call<List<CurrentUserProfile>> call, Throwable t) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(getActivity().getFragmentManager(), "connection_dialog");
            }
        });
        followersAdapter=new FollowAdapter(followersList);
        followersRecyclerView=view.findViewById(R.id.followers_recycle_view);
        followersLayoutManager=new LinearLayoutManager(getContext());
        followersRecyclerView.setLayoutManager(followersLayoutManager);
        followersRecyclerView.setAdapter(followersAdapter);
        return view;
    }
}
