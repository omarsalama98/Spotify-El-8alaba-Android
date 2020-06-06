package com.vnoders.spotify_el8alaba.ui.library;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.library.RequestBodyIds;
import com.vnoders.spotify_el8alaba.repositories.LibraryApi;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.library.SearchArtistsListAdapter.SearchArtistViewHolder;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchArtistsListAdapter extends RecyclerView.Adapter<SearchArtistViewHolder> {

    private static ArrayList<SearchArtist> mDataset;
    private static LibraryApi apiService;
    private static String artistId = "";
    private static Activity activity;

    public SearchArtistsListAdapter(ArrayList<SearchArtist> myDataset, Activity activity) {
        mDataset = myDataset;
        SearchArtistsListAdapter.activity = activity;
        apiService = RetrofitClient.getInstance().getAPI(LibraryApi.class);
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public SearchArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_artists_list_item, parent, false);
        return new SearchArtistViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SearchArtistViewHolder holder, final int position) {

        SearchArtist result = mDataset.get(position);
        String artistName = result.getName();
        holder.name.setText(artistName);
        String artistImageUrl = "";
        if (result.getImages() != null && !result.getImages().isEmpty()) {
            artistImageUrl = result.getImages().get(0).getUrl();
        } else {
            artistImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
        }
        Picasso.get().load(artistImageUrl).placeholder(R.drawable.spotify).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class SearchArtistViewHolder extends RecyclerView.ViewHolder {

        public View searchItemBody;
        public TextView name;
        public ImageView image;

        SearchArtistViewHolder(View v) {
            super(v);
            searchItemBody = v.findViewById(R.id.search_list_item_layout);
            name = v.findViewById(R.id.search_item_name_text_view);
            image = v.findViewById(R.id.search_item_image_view);

            searchItemBody.setOnClickListener(v12 -> {
                artistId = mDataset.get(getAdapterPosition()).getId();
                List<String> artistIds = new ArrayList<>();
                artistIds.add(artistId);
                RequestBodyIds requestBody = new RequestBodyIds(artistIds);
                Call<Void> call = apiService.followArtists(requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(activity, mDataset.get(getAdapterPosition()).getName()
                                    + " added to your artists", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            });
        }
    }

}