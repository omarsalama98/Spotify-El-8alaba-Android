package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vnoders.spotify_el8alaba.models.Notifications.NotificationStatus;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.signup.SignUpDialog;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. Use the {@link NotificationSettings#newInstance} factory
 * method to create an instance of this fragment.
 */
public class NotificationSettings extends Fragment {
    private Button saveButton;
    private ImageButton backButton;
    protected boolean profileFollows;
    protected boolean playlistFollows;
    protected boolean newTrack;
    protected boolean newAlbum;
    protected Switch  switchProfileFollows;
    protected Switch  switchPlaylisFollows;
    protected Switch  switchNewTrack;
    protected Switch  switchNewAlbum;

    public NotificationSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment NotificationSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationSettings newInstance(String param1, String param2) {
        NotificationSettings fragment = new NotificationSettings();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_notification_settings, container, false);
         saveButton=view.findViewById(R.id.submit_changes);
         backButton=view.findViewById(R.id.back_button);
         switchNewAlbum=view.findViewById(R.id.switch_new_album);
         switchNewTrack=view.findViewById(R.id.switch_new_track);
         switchPlaylisFollows=view.findViewById(R.id.switch_playlist_follows);
         switchProfileFollows=view.findViewById(R.id.switch_profile_follows);
         backButton.setOnClickListener(v -> getFragmentManager().popBackStackImmediate());
         requestNotificationStatus();
         saveButton.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 sendNotificationStatus();
             }
         });
         return view;
    }

    public void requestNotificationStatus(){
        Call<ResponseBody> call= RetrofitClient.getInstance().getAPI(API.class).getNotificationStatus();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                String responseBody=null;
                    try {
                        responseBody=response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject notificationStatus=new JSONObject(responseBody);
                        profileFollows=(notificationStatus.getInt("userFollowed")==1);
                        playlistFollows=(notificationStatus.getInt("playlistFollowed")==1);
                        newTrack=(notificationStatus.getInt("newArtistTrack")==1);
                        newAlbum=(notificationStatus.getInt("newArtistAlbum")==1);
                        switchNewAlbum.setChecked(newAlbum);
                        switchNewTrack.setChecked(newTrack);
                        switchPlaylisFollows.setChecked(playlistFollows);
                        switchProfileFollows.setChecked(profileFollows);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(getActivity().getFragmentManager(), "connection_dialog");
            }


        });
    }
    public void sendNotificationStatus(){
        Integer setProileFollows=(switchProfileFollows.isChecked())? 1:0;
        Integer setPlaylistFollows=(switchPlaylisFollows.isChecked()) ? 1:0;
        Integer setTrack=(switchNewTrack.isChecked())? 1:0;
        Integer setAlbum=(switchNewAlbum.isChecked())? 1:0;
        NotificationStatus notificationStatus=new NotificationStatus(setProileFollows,setPlaylistFollows,setTrack,setAlbum);
        Call<ResponseBody> sendNotificationStatus=RetrofitClient.getInstance().getAPI(API.class).setNotificationStatus(notificationStatus);
        sendNotificationStatus.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(getActivity().getFragmentManager(), "connection_dialog");
            }
        });


    }
}