package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.ui.library.LibraryArtistAdapter.ArtistViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class LibraryArtistAdapter extends RecyclerView.Adapter<ArtistViewHolder> {

    private List<Artist> artists;

    public LibraryArtistAdapter() {
        this.artists = new ArrayList<>();
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
        holder.artistName.setText(artists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
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

        }

    }

}
