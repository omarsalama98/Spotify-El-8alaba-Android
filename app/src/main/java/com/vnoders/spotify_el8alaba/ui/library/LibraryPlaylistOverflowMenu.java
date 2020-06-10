package com.vnoders.spotify_el8alaba.ui.library;

import android.view.View;
import android.view.View.OnClickListener;
import androidx.lifecycle.ViewModelProvider;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.library.LibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.models.library.Owner;
import com.vnoders.spotify_el8alaba.models.overflowmenu.OverflowMenu;
import com.vnoders.spotify_el8alaba.models.overflowmenu.OverflowMenuItem;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class LibraryPlaylistOverflowMenu extends OverflowMenu {

    private String playlistId;

    private LibraryPlaylistOverflowMenu(String playlistId, String playlistName,
            String ownerName, String imageUrl) {
        super(playlistName, ownerName, imageUrl);
        this.playlistId = playlistId;
        createActions();
    }

    public static LibraryPlaylistOverflowMenu newInstance(LibraryPlaylistItem playlist) {
        String ownerName = "Temp Owner Name";
        Owner owner = playlist.getOwner();
        if (owner != null) {
            ownerName = owner.getName();
        }

        List<Image> images = playlist.getImages();
        String imageUrl = null;

        if (images != null && images.size() > 0) {
            imageUrl = images.get(0).getUrl();
        }
        return new LibraryPlaylistOverflowMenu(playlist.getId(), playlist.getName(), ownerName,
                imageUrl);
    }


    private void createActions() {
        super.actionItems.add(createUnfollowAction());

        super.actionItems.add(createShareAction());

        super.actionItems.add(createAddToHomeAction());
    }


    @NotNull
    private OverflowMenuItem createUnfollowAction() {
        OnClickListener unfollowAction = new OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryPlaylistViewModel playlistViewModel = new ViewModelProvider(
                        requireActivity()).get(LibraryPlaylistViewModel.class);
                playlistViewModel.unfollowPlaylist(playlistId);
                dismiss();
            }
        };

        return new OverflowMenuItem(R.drawable.ic_stop_following_grey_36dp,
                "Stop Following", unfollowAction);
    }


    @NotNull
    private OverflowMenuItem createShareAction() {
        return new OverflowMenuItem(R.drawable.ic_share_grey_36dp, "Share", null);
    }


    @NotNull
    private OverflowMenuItem createAddToHomeAction() {
        return new OverflowMenuItem(R.drawable.ic_add_to_home_grey_36dp,
                "Add to Home Screen", null);
    }

}