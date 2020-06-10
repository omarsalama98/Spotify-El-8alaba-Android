package com.vnoders.spotify_el8alaba.ui.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.ui.library.RelatedArtistsAdapter.RelatedArtistViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

class RelatedArtistsAdapter extends RecyclerView.Adapter<RelatedArtistViewHolder> {

    private List<Artist> relatedArtists;
    private FragmentManager fragmentManager;

    public RelatedArtistsAdapter(FragmentManager fragmentManager) {
        this.relatedArtists = new ArrayList<>();
        this.fragmentManager = fragmentManager;
    }

    /**
     * @param relatedArtists Sets the list of related artists to an artist.
     */
    public void setRelatedArtists(List<Artist> relatedArtists) {
        this.relatedArtists = relatedArtists;
    }

    /**
     * Create a new view and fill its data by {@link RelatedArtistViewHolder}
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 specific position.
     * @param viewType The type of the created view holder in case the list has multiple types of
     *                 views. In our case it is not used because we have only one type of views
     *
     * @return A new {@link RelatedArtistViewHolder}
     * that holds a View of the given view type.
     */
    @NonNull
    @Override
    public RelatedArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.related_artists_list_item, parent, false);

        return new RelatedArtistViewHolder(view);
    }

    /**
     * Instead of creating a new {@link RelatedArtistViewHolder} we use an existing one that does
     * not appear on the screen but update its displaying data.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set ({@link
     *                 #relatedArtists}).
     */
    @Override
    public void onBindViewHolder(@NonNull RelatedArtistViewHolder holder, int position) {
        Artist artist = relatedArtists.get(position);
        if (artist != null) {
            holder.bind(artist);
        }
    }

    /**
     * @return The total number of items in this adapter. i.e. the number of related artists.
     */
    @Override
    public int getItemCount() {
        return relatedArtists.size();
    }


    /**
     * A ViewHolder describes an item in the list of the adapter and metadata about its place within
     * the RecyclerView.
     */
    class RelatedArtistViewHolder extends RecyclerView.ViewHolder {

        private String artistId;

        private TextView artistName;
        private CircleImageView artistImage;

        /**
         * @param itemView The view of the new created item
         */
        RelatedArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            View artistBody = itemView.findViewById(R.id.related_artist_body);
            artistName = itemView.findViewById(R.id.related_artist_name);
            artistImage = itemView.findViewById(R.id.related_artist_image);

            artistBody.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, ArtistFragment.newInstance(artistId))
                            .addToBackStack(null)
                            .commit();
                }
            });

        }

        void bind(Artist artist) {
            artistId = artist.getId();
            artistName.setText(artist.getName());

            String imageUrl = null;
            if (artist.getImages() != null && !artist.getImages().isEmpty()) {
                imageUrl = artist.getImages().get(0).getUrl();
            }

            Picasso.get().load(imageUrl).placeholder(R.drawable.artist).into(artistImage);
        }

    }

}
