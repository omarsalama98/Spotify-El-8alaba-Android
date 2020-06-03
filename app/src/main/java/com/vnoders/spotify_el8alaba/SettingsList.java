package com.vnoders.spotify_el8alaba;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import com.vnoders.spotify_el8alaba.ui.currentUserProfile.CurrentUserProfileFragment;
import com.vnoders.spotify_el8alaba.ui.login.FirstScreen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsList extends Fragment {
    private TextView mCurrentUserProfile;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private Bundle bundle;
    private TextView logout;
    SharedPreferences sharedPreferences;
    private TextView notificationSettings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle=new Bundle();
        View view= inflater.inflate(R.layout.fragment_settings_list, container, false);
        mCurrentUserProfile=view.findViewById(R.id.current_user_profile);
        logout=view.findViewById(R.id.logout);
        notificationSettings=view.findViewById(R.id.notification_settings);
        notificationSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationSettings notificationSettings=new NotificationSettings();
                mFragmentManager=getActivity().getSupportFragmentManager();
                mFragmentTransaction=mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.nav_host_fragment,notificationSettings,"Notification_Settings").addToBackStack(null).commit();
            }
        });
        mCurrentUserProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<CurrentUserProfile> currentUserProfileCall= RetrofitClient.getInstance().getAPI(
                        API.class).getCurrentUserProfile();
                currentUserProfileCall.enqueue(new Callback<CurrentUserProfile>() {
                    @Override
                    public void onResponse(Call<CurrentUserProfile> call, Response<CurrentUserProfile> response) {
                        CurrentUserProfile currentUserProfile=response.body();
                        bundle.putSerializable("CURRENT_USER_PROFILE",currentUserProfile);
                        CurrentUserProfileFragment currentUserProfileFragment=new CurrentUserProfileFragment();
                        mFragmentManager=getActivity().getSupportFragmentManager();
                        currentUserProfileFragment.setArguments(bundle);
                        mFragmentTransaction=mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.nav_host_fragment,currentUserProfileFragment,"CURRENT_USER_PROFILE").addToBackStack(null).commit();

                    }

                    @Override
                    public void onFailure(Call<CurrentUserProfile> call, Throwable t) {
                        ConnectionDialog dialog = new ConnectionDialog();
                        dialog.show(getActivity().getFragmentManager(), "connection_dialog");
                    }
                });

            }
        });
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences(
                        getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", "");
                editor.putString("id","");
                editor.commit();
                Intent intent=new Intent(getActivity(), FirstScreen.class);
                // stop playing songs
                ((MainActivity) getActivity()).getService().pause();
                startActivity(intent);
            }
        });
        return view;
    }
}
