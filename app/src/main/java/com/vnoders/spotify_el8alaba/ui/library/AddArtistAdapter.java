package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.ui.library.AddArtistAdapter.ArtistViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;

class AddArtistAdapter extends RecyclerView.Adapter<ArtistViewHolder> {

    private AddArtistsViewModel viewModel;
    private List<SearchArtist> artists;
    private HashSet<String> selectedArtistsIds;

    public AddArtistAdapter(AddArtistsViewModel addArtistsViewModel) {
        this.viewModel = addArtistsViewModel;
        this.artists = new ArrayList<>();
        this.selectedArtistsIds = new HashSet<>();
    }

    public void setArtists(List<SearchArtist> artists) {
        this.artists = artists;
    }

    public void insertAtBeginning(SearchArtist artist) {
        for (int i = 0; i < artists.size(); i++) {
            if (artist.equals(artists.get(i))) {
                artists.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
        artists.add(0, artist);
        notifyItemInserted(0);
    }

    public ArrayList<String> getSelectedArtistsIds() {
        return new ArrayList<>(selectedArtistsIds);
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.add_artist_list_item, parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        holder.bind(artists.get(position));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        View artistBody;
        CircleImageView artistImage;
        TextView artistName;
        ImageView checkedIcon;
        String artistId;

        ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            artistBody = itemView.findViewById(R.id.add_artist_item_body);
            artistImage = itemView.findViewById(R.id.add_artist_image);
            artistName = itemView.findViewById(R.id.add_artist_name);
            checkedIcon = itemView.findViewById(R.id.add_artist_checked);

            artistBody.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchArtist artist = artists.get(getAdapterPosition());
                    artist.toggleSelection();

                    if (artist.isSelected()) {
                        selectedArtistsIds.add(artistId);
                        viewModel.requestRelatedArtists(artistId, new RelatedArtistsCallback());
                        checkedIcon.setVisibility(View.VISIBLE);
                    } else {
                        selectedArtistsIds.remove(artistId);
                        checkedIcon.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }

        public void bind(@NotNull SearchArtist artist) {
            artistName.setText(artist.getName());

            artistId = artist.getId();

            String imageUrl = null;
            if (artist.getImages() != null && !artist.getImages().isEmpty()) {
                imageUrl = artist.getImages().get(0).getUrl();
            }

            Picasso.get().load(imageUrl).placeholder(R.drawable.artist).into(artistImage);

            if (artist.isSelected()) {
                selectedArtistsIds.add(artistId);
                checkedIcon.setVisibility(View.VISIBLE);
            } else {
                selectedArtistsIds.remove(artistId);
                checkedIcon.setVisibility(View.INVISIBLE);
            }
        }
    }


    class RelatedArtistsCallback {

        void onResponse(String artistId, List<SearchArtist> relatedArtists) {

            int indexOfArtist = 0;
            for (int i = 0; i < artists.size(); i++) {
                if (artists.get(i).getId().equals(artistId)) {
                    indexOfArtist = i;
                    break;
                }
            }

            for (SearchArtist artist : artists) {
                int index = relatedArtists.indexOf(artist);
                if (index != -1) {
                    relatedArtists.remove(index);
                }
            }

            int numberOfRelatedArtists = 5;
            if (relatedArtists.size() > numberOfRelatedArtists) {
                relatedArtists = relatedArtists.subList(0, numberOfRelatedArtists);
            }

            artists.addAll(indexOfArtist, relatedArtists);
            notifyItemRangeInserted(indexOfArtist, relatedArtists.size());
        }

    }

}
