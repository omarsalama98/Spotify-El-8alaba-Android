package com.vnoders.spotify_el8alaba.models.overflowmenu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import java.util.List;

public class OverflowMenu extends BottomSheetDialogFragment {

    private List<OverflowMenuItem> actionItems;
    private String songName;
    private String authorName;
    private String imageUrl;

    public OverflowMenu(String songName, String authorName, String imageUrl,
            @NonNull List<OverflowMenuItem> actionItems) {
        this.songName = songName;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.actionItems = actionItems;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overflow_menu, container, false);

        LinearLayout linearLayout = root.findViewById(R.id.overflow_items_container);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // set the data displayed on screen with object
        updateBasicInfo(root);

        addActions(linearLayout);

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        // view hierarchy is inflated after dialog is shown
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                //this disables outside touch
                dialog.getWindow().findViewById(R.id.touch_outside).setOnClickListener(null);
                //this prevents dragging behavior
                View content = dialog.getWindow().findViewById(R.id.design_bottom_sheet);
                content.setLayoutParams(new CoordinatorLayout.LayoutParams(
                        CoordinatorLayout.LayoutParams.MATCH_PARENT,
                        CoordinatorLayout.LayoutParams.MATCH_PARENT));
                ((CoordinatorLayout.LayoutParams) content.getLayoutParams()).setBehavior(null);
            }
        });
        return dialog;
    }

    /**
     * updates data on screen
     */
    private void updateBasicInfo(View root) {

        // set the name of track
        TextView song = root.findViewById(R.id.overflow_song_name);
        song.setText(songName);

        // set the author's name
        TextView author = root.findViewById(R.id.overflow_author_name);
        author.setText(authorName);

        // set the image displayed in middle
        ImageView image = root.findViewById(R.id.overflow_image);

        Picasso.get().load(imageUrl).placeholder(R.drawable.track_image_default).into(image);
    }


    private void addActions(ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (OverflowMenuItem actionItem : actionItems) {
            View root = inflater.inflate(R.layout.overflow_menu_item, parent, false);

            TextView title = root.findViewById(R.id.overflow_menu_item_title);
            title.setText(actionItem.getTitle());

            ImageView icon = root.findViewById(R.id.overflow_menu_item_icon);
            icon.setImageResource(actionItem.getIconResourceId());

            root.setOnClickListener(actionItem.getAction());

            parent.addView(root);
        }
    }

}
