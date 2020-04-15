package com.vnoders.spotify_el8alaba.Lists_Adapters;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeMainListItem;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMainListAdapter extends RecyclerView.Adapter<HomeMainListAdapter.MyViewHolder> {

    private ArrayList<HomeMainListItem> mockDataset;
    private ArrayList<Category> backDataset;
    private Context context;
    private Fragment fragment;


    /**
     * @param myDataset List of Categories to show in Home
     * @param context   The context where this list will exist
     * @param fragment  The fragment where the list will be created
     */
    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeMainListAdapter(ArrayList<HomeMainListItem> myDataset, Context context,
            Fragment fragment) {
        mockDataset = myDataset;
        backDataset = new ArrayList<>();
        this.context = context;
        this.fragment = fragment;
    }

    //       **    The difference between these two constructors is that one uses mock data and the other    **
    //       **      uses data retrieved from the server and the mock data one will be removed later on.     **

    public HomeMainListAdapter(Context context, Fragment fragment,
            ArrayList<Category> backDataset) {
        mockDataset = new ArrayList<>();
        this.backDataset = backDataset;
        this.context = context;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeMainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_main_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        /*holder.title.setText(mockDataset.get(position).getTitle());
        holder.innerList.setAdapter(
                new HomeInnerListAdapter(mockDataset.get(position).getInnerListItems(), fragment));
        holder.innerList.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        */

        // TODO: Replace the former with the latter code when backend is completed
        holder.title.setText(backDataset.get(position).getName());

        APIInterface apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        Call<List<Playlist>> call = apiService
                .getCategoryPlaylists(backDataset.get(position).getId());
        Log.d(TAG, backDataset.get(position).getId());

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                Log.d(TAG, response.body().get(0).getDescription());
                holder.innerList.setAdapter(
                        new HomeInnerListAdapter(fragment, (ArrayList<Playlist>) response.body()));
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Playlists" + t.getLocalizedMessage());
            }
        });

        holder.innerList.setLayoutManager(
                 new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mockDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View v;
        public TextView title;
        RecyclerView innerList;

        MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.home_main_list_item_title);
            innerList = v.findViewById(R.id.home_inner_list_recycler_view);
        }

    }
}
