package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.userProfile.GetUsersPlaylists;
import com.vnoders.spotify_el8alaba.models.userProfile.UserPlaylistItem;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Ali Adel
 *
 * Class to implement fragment for displaying the created playlists by user
 */
public class OwnedPlaylistsFragment extends Fragment implements OwnedPlaylistsAdapter.OwnedPlaylistItemClick {

    // recycler view used that contains data
    private RecyclerView mOwnedPlaylistsRecyclerView;
    // list of playlists to display
    private ArrayList<UserPlaylistItem> mOwnedPlaylists;
    // id of current user
    private String mUserId;
    // progress bar to display the loading
    private View mProgressBar;
    // // scroll view that contains the recycler view
    private View mScrollView;
    // text message that informs user that there is not data
    private View mEmptyView;

    /**
     * Private constructor to inti the user id
     * @param userId of user in app
     */
    private OwnedPlaylistsFragment(String userId) {
        this.mUserId = userId;
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment owned playlists.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnedPlaylistsFragment newInstance(String userId) {
        return new OwnedPlaylistsFragment(userId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called when fragment is created so init variables here
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_owned_playlists, container, false);

        // init the playlist with an empty one
        mOwnedPlaylists = new ArrayList<>();

        // create adapter
        OwnedPlaylistsAdapter mOwnedPlaylistsAdapter = new OwnedPlaylistsAdapter(mOwnedPlaylists, this);

        // get the recycler view
        mOwnedPlaylistsRecyclerView = view.findViewById(R.id.owned_playlist_recycler_view);

        // use a linear layout
        mOwnedPlaylistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // set the adapter to recycler view
        mOwnedPlaylistsRecyclerView.setAdapter(mOwnedPlaylistsAdapter);

        // find back button and set it to operation
        View mBackButton = view.findViewById(R.id.owned_playlist_back_button);
        mBackButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());

        // show progress bar as first thing is loading data
        mProgressBar = view.findViewById(R.id.owned_playlists_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        // hide data as there is none to display
        mScrollView = view.findViewById(R.id.owned_playlists_scroll_view);
        mScrollView.setVisibility(View.GONE);

        // hide empty view as still loading
        mEmptyView = view.findViewById(R.id.owned_playlists_empty);
        mEmptyView.setVisibility(View.GONE);

        // fetch data from back end
        getData();

        return view;
    }

    /**
     * Gets the data from backend and displays it
     */
    private void getData() {
        // make request
        Call<GetUsersPlaylists> request = RetrofitClient.getInstance().getAPI(API.class).getCurrentUsersPlaylists();

        // launch the request
        request.enqueue(new Callback<GetUsersPlaylists>() {
            @Override
            public void onResponse(Call<GetUsersPlaylists> call, Response<GetUsersPlaylists> response) {

                // first request is over so if whatever happens just display empty view in case
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);

                // if there is any error return from function
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                // get list of items
                List<UserPlaylistItem> items = response.body().getItems();

                if (items == null)
                    return;

                // init a new playlist
                mOwnedPlaylists = new ArrayList<>();

                // iterate over list of playlists and see it its owner is the current user
                // then add it to list
                for (int i = 0; i < items.size(); ++i) {
                    if (mUserId.equals(items.get(i).getOwner().getId())) {
                        mOwnedPlaylists.add(items.get(i));
                    }
                }

                // init new adapter that holds the new data
                OwnedPlaylistsAdapter adapter = new OwnedPlaylistsAdapter(mOwnedPlaylists, OwnedPlaylistsFragment.this);

                // swap adapter of recycler view
                mOwnedPlaylistsRecyclerView.swapAdapter(adapter, false);

                // if there is data then show the data and hide other views
                if (mOwnedPlaylists.size() > 0) {
                    mProgressBar.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
                else {
                    // if there is not data then show the empty view
                    mProgressBar.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetUsersPlaylists> call, Throwable t) {
                // request failed to display empty view
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * This is called by the recycler view when an item is clicked
     * launches the playlist fragment to display playlist to user
     * @param position index of playlist in list
     */
    @Override
    public void onItemClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment,
                        PlaylistHomeFragment.newInstance(mOwnedPlaylists.get(position).getId()))
                .addToBackStack(null)
                .commit();
    }
}
