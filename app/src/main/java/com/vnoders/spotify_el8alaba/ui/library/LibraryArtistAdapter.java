package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.ui.library.LibraryArtistAdapter.ArtistViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;

public class LibraryArtistAdapter extends ListAdapter<Artist, ArtistViewHolder> {

    private static final ItemCallback<Artist> DIFF_COMPARE_CALLBACK = new ItemCallback<Artist>() {
        @Override
        public boolean areItemsTheSame(@NonNull Artist oldItem, @NonNull Artist newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Artist oldItem, @NonNull Artist newItem) {
            return oldItem.equals(newItem);
        }
    };


    public LibraryArtistAdapter() {
        super(DIFF_COMPARE_CALLBACK);
    }


    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.artist_list_item, parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        if (getItem(position) != null) {
            holder.bind(getItem(position));
        }
    }


    static class ArtistViewHolder extends RecyclerView.ViewHolder {

        View artistBody;
        CircleImageView artistImage;
        TextView artistName;

        ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            artistBody = itemView.findViewById(R.id.library_artist_item_body);

            artistImage = itemView.findViewById(R.id.library_artist_image);

            artistName = itemView.findViewById(R.id.library_artist_name);

            artistBody.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(),
                            "Artist " + artistName.getText().toString() + " long click menu",
                            Toast.LENGTH_SHORT).show();
                    v.setPressed(false);
                    return true;
                }
            });

        }

        public void bind(Artist artist) {
            artistName.setText(artist.getName());

            if (artist.getImages() != null && !artist.getImages().isEmpty()) {
                String imageUrl = artist.getImages().get(0).getUrl();
                Picasso.get().load(imageUrl).placeholder(R.drawable.artist).into(artistImage);
            }

        }
    }

}
