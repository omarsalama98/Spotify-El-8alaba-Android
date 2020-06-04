package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.ui.library.AddArtistAdapter.ArtistViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

class AddArtistAdapter extends ListAdapter<SearchArtist, ArtistViewHolder> {

    private AddArtistsViewModel viewModel;

    private static final ItemCallback<SearchArtist> DIFF_COMPARE_CALLBACK = new ItemCallback<SearchArtist>() {
        @Override
        public boolean areItemsTheSame(@NonNull SearchArtist oldItem,
                @NonNull SearchArtist newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchArtist oldItem,
                @NonNull SearchArtist newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    public AddArtistAdapter(AddArtistsViewModel addArtistsViewModel) {
        super(DIFF_COMPARE_CALLBACK);
        this.viewModel = addArtistsViewModel;
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
        holder.bind(getItem(position));
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
                    SearchArtist artist = getItem(getAdapterPosition());
                    artist.toggleSelection();

                    if (artist.isSelected()) {
                        // TODO: actually add this artist to favorites
                        viewModel.requestRelatedArtists(artistId);
                        checkedIcon.setVisibility(View.VISIBLE);
                    } else {
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
                checkedIcon.setVisibility(View.VISIBLE);
            } else {
                checkedIcon.setVisibility(View.INVISIBLE);
            }
        }
    }

}
