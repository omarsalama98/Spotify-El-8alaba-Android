package com.vnoders.spotify_el8alaba.models.overflowmenu;

import android.view.View.OnClickListener;
import androidx.annotation.DrawableRes;

public class OverflowMenuItem {

    @DrawableRes
    private int iconResourceId;
    private String title;
    private OnClickListener action;

    public OverflowMenuItem(@DrawableRes int iconResourceId, String title,
            OnClickListener action) {
        this.iconResourceId = iconResourceId;
        this.title = title;
        this.action = action;
    }


    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getTitle() {
        return title;
    }

    public OnClickListener getAction() {
        return action;
    }

}
