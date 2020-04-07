package com.vnoders.spotify_el8alaba.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.HomeMainListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeMainListItem;
import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.ui.currentUserProfile.CurrentUserProfileFragment;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView settingsButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        settingsButton=root.findViewById(R.id.settings_image_view);
        ArrayList<HomeMainListItem> mainListItems = Mock.getMainHomeList();

        RecyclerView mainListRecyclerView = root.findViewById(R.id.home_main_list_recycler_view);
        mainListRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mainListRecyclerView.setHasFixedSize(true);
        mainListRecyclerView.setAdapter(new HomeMainListAdapter(mainListItems, getContext(), this));

        settingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUserProfileFragment currentUserProfileFragment=new CurrentUserProfileFragment();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,currentUserProfileFragment,"CURRENT_USER_PROFILE").commit();
            }
        });

        return root;


    }

}

//      Testaya bas

/*
        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);

        Call<List<Artist>> call = apiService.getArtistsofSearch("Hamo Bika","artist");
        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                Log.d(TAG,"haha"+response.body().get(0).getName());
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {

            }
        });
        */