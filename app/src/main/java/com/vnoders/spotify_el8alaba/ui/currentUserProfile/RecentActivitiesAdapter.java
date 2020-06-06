package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.response.Notifications.Notification;
import com.vnoders.spotify_el8alaba.response.Notifications.RecentActivities;
import java.util.ArrayList;

public class RecentActivitiesAdapter extends RecyclerView.Adapter<RecentActivitiesAdapter.RecentActivitiesViewHolder> {
    private ArrayList<NotificationItem> mRecentActivitiesArrayList;

    public static class RecentActivitiesViewHolder extends RecyclerView.ViewHolder{
        public TextView mRecentActivityTitle;
        public TextView mRecentActivityBody;
        public TextView getmRecentActivityDate;
        public RecentActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecentActivityBody=itemView.findViewById(R.id.notification_body);
            mRecentActivityTitle=itemView.findViewById(R.id.notification_title);
            getmRecentActivityDate=itemView.findViewById(R.id.notification_date);
        }
    }

    public RecentActivitiesAdapter(ArrayList<NotificationItem>recentActivitiesArrayList){
    mRecentActivitiesArrayList=recentActivitiesArrayList;
    }

    @NonNull
    @Override
    public RecentActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        RecentActivitiesViewHolder evh =new RecentActivitiesViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivitiesViewHolder holder, int position) {
    NotificationItem notificationItem=mRecentActivitiesArrayList.get(position);
    holder.mRecentActivityBody.setText(notificationItem.getmBody());
    holder.mRecentActivityTitle.setText(notificationItem.getmTitle());
    holder.getmRecentActivityDate.setText(notificationItem.getmDate());
    }

    @Override
    public int getItemCount() {
        return mRecentActivitiesArrayList.size();
    }
}
