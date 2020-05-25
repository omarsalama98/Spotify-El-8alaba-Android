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
import com.vnoders.spotify_el8alaba.App;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;
import java.util.List;

public class OverflowMenu extends BottomSheetDialogFragment {

    protected List<OverflowMenuItem> actionItems;
    private String songName;
    private String authorName;
    private String imageUrl;

    /**
     * Constructor for early creating and then setting the value with setters to get early reference
     */
    public OverflowMenu() {};

    public OverflowMenu(String songName, String authorName, String imageUrl,
            @NonNull List<OverflowMenuItem> actionItems) {
        this.songName = songName;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.actionItems = actionItems;
    }

    protected OverflowMenu(String songName, String authorName, String imageUrl) {
        this.songName = songName;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.actionItems = new ArrayList<>();
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

        LinearLayout overflowMenu = root.findViewById(R.id.overflow_menu);
        overflowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // set the data displayed on screen with object
        updateBasicInfo(root);

        addActions(overflowMenu);

        fixLayoutPosition(overflowMenu);

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

    /**
     * Get the View's height because {@link View#getHeight()} returns zero as the view is not fully
     * instantiated yet.
     *
     * @param view The view to calculate its height
     *
     * @return The real view's height
     */
    private int getViewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }


    /**
     * The menu was centered in the screen. This fixes to be at the bottom by adding padding based
     * on its size.
     *
     * @param overflowMenu The view to fix
     */
    private void fixLayoutPosition(View overflowMenu) {
        int displayHeight = App.getDisplayHeight();
        int overflowMenuHeight = getViewHeight(overflowMenu);

        if (displayHeight > overflowMenuHeight) {
            int topPadding = displayHeight - overflowMenuHeight;
            int bottomPadding = getResources()
                    .getDimensionPixelSize(R.dimen.overflow_bottom_padding);
            overflowMenu.setPadding(0, topPadding, 0, bottomPadding);
        }
    }

    /**
     * Setter for the song name
     * @param songName name of song to display
     */
    public void setSongName(String songName) {
        this.songName = songName;
    }

    /**
     * Setter for the author/artist name
     * @param authorName name of artist to display after song
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Setter for image url to display
     * @param imageUrl to display at top
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Setter for action items list to display
     * @param actionItems to display
     */
    public void setActionItems(@NonNull List<OverflowMenuItem> actionItems) {
        this.actionItems = actionItems;
    }
}
