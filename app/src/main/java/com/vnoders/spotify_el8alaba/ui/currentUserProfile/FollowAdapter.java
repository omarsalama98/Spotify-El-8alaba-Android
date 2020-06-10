package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.ui.currentUserProfile.FollowAdapter.FollowViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

/**
 * @author Mohamed Samy
 * This class is used to handle the followers and following list and rendering the data in the recycler view correctly.
 */
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder> {
    private ArrayList<FollowItem> followList;
    private onFollowClickListener mlistener;
    private ArrayList<String> followingIds=null;
    private boolean followers;

    public interface onFollowClickListener{
        void onItemClick(int position);
    }

    public void setOnFollowClickListener(onFollowClickListener listener){
        mlistener=listener;
    }
    public static class FollowViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView userImage;
        private TextView followersNumber;
        private TextView userName;
        private CircleImageView followSign;
        public FollowViewHolder(@NonNull View itemView,final onFollowClickListener listener) {
            super(itemView);
            userImage=itemView.findViewById(R.id.follow_image);
            followersNumber=itemView.findViewById(R.id.follow_number);
            userName=itemView.findViewById(R.id.follow_name);
            followSign=itemView.findViewById(R.id.follow_button);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                if(listener!=null){
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
                }
            });

        }


    }

    public FollowAdapter(ArrayList<FollowItem> followItems,ArrayList<String> following) {
        followList=followItems;
        if(following!=null){
        if(following.size()>0){
        followingIds=following;
        }
        else {followingIds=null;}
        }
        else {followingIds=null;}
        followers=true;
    }
    public FollowAdapter(ArrayList<FollowItem> followItems) {
    followList=followItems;
    followingIds=null;
    followers=false;

    }
    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        FollowViewHolder followViewHolder=new FollowViewHolder(v,mlistener);
        return followViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {
    FollowItem currentItem = followList.get(position);
    String id=currentItem.getMid();
    if(followingIds!=null) {
        for (int i = 0; i < followingIds.size(); i++) {
            if (id.equals(followingIds.get(i))) {
                holder.followSign.setImageResource(R.drawable.ic_baseline_check_24);
                break;
            }
        }
    }
    else if(!followers){holder.followSign.setImageResource(R.drawable.ic_baseline_check_24);}
    holder.userName.setText(currentItem.getmUserName());
    holder.followersNumber.setText(currentItem.getmFollowersNumber());
    Picasso.get().load(currentItem.getmImageURL()).into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return followList.size();
    }
}
