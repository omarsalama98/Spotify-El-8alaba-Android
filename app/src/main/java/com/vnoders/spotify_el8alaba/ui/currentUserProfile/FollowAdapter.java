package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.ui.currentUserProfile.FollowAdapter.FollowViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class FollowAdapter extends RecyclerView.Adapter<FollowViewHolder> {
    private ArrayList<FollowItem> followList;

    public static class FollowViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView userImage;
        private TextView followersNumber;
        private TextView userName;
        public FollowViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage=itemView.findViewById(R.id.follow_image);
            followersNumber=itemView.findViewById(R.id.follow_number);
            userName=itemView.findViewById(R.id.follow_name);
        }
    }

    public FollowAdapter(ArrayList<FollowItem> followItems) {
    followList=followItems;
    }
    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        FollowViewHolder followViewHolder=new FollowViewHolder(v);
        return followViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {
    FollowItem currentItem = followList.get(position);
    Log.d("FOLLOWER ADAPTER",currentItem.getmUserName());
    holder.userName.setText(currentItem.getmUserName());
    holder.followersNumber.setText(currentItem.getmFollowersNumber());
    Picasso.get().load(currentItem.getmImageURL()).into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return followList.size();
    }
}
