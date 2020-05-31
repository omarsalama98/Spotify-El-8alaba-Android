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
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.Artist;
import com.vnoders.spotify_el8alaba.ui.library.AddArtistAdapter.ArtistViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

class AddArtistAdapter extends RecyclerView.Adapter<ArtistViewHolder> {

    private List<Artist> artists;

    public AddArtistAdapter(ArrayList<Artist> artists) {
        this.artists = artists;
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
        holder.artistName.setText(artists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {

        View artistBody;
        CircleImageView artistImage;
        TextView artistName;
        ImageView checkedIcon;

        ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            artistBody = itemView.findViewById(R.id.add_artist_item_body);
            artistImage = itemView.findViewById(R.id.add_artist_image);
            artistName = itemView.findViewById(R.id.add_artist_name);
            checkedIcon = itemView.findViewById(R.id.add_artist_checked);

            artistBody.setOnClickListener(new OnClickListener() {
                boolean checked = false;

                @Override
                public void onClick(View v) {
                    checked = !checked;
                    if (checked) {
                        // TODO: actually add this artist to favorites
                        checkedIcon.setVisibility(View.VISIBLE);
                    } else {
                        checkedIcon.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }

    }

}
