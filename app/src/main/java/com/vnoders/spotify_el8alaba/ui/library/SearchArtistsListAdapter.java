package com.vnoders.spotify_el8alaba.ui.library;

import static com.vnoders.spotify_el8alaba.ui.library.AddArtistActivity.INTENT_DATA_ARTIST;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.repositories.LibraryApi;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.library.SearchArtistsListAdapter.SearchArtistViewHolder;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

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

            searchItemBody.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = RetrofitClient.getInstance().getGson();

                    SearchArtist artist = mDataset.get(getAdapterPosition());

                    Intent intent = new Intent();
                    intent.putExtra(INTENT_DATA_ARTIST, gson.toJson(artist));

                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                }
            });
        }
    }

}