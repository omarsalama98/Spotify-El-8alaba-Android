package com.vnoders.spotify_el8alaba.ui.signup;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;

/**
 *
 */
public class UserType extends Fragment {
    private Button user;
    private Button artist;
    private ImageButton back;
    private FragmentManager fragmentManager;
    private void open_fragment() {

        CreateAccount fragment = new CreateAccount();
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, fragment, "CREATE_ACCOUNT").commit();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_type, container, false);
        artist=view.findViewById(R.id.artist);
        user=view.findViewById(R.id.user);
        back=getActivity().findViewById(R.id.back_button);

        user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpEmail) getActivity()).setType("user");
                artist.setTextColor(getResources().getColor(R.color.white));
                artist.setBackground(getResources().getDrawable(R.drawable.gender_selected));
                artist.setBackground(getResources().getDrawable(R.drawable.gender_not_selected));
                artist.setTextColor(getResources().getColor(R.color.lightGray));
                open_fragment();
            }
        });

        artist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpEmail) getActivity()).setType("artist");
                user.setTextColor(getResources().getColor(R.color.white));
                user.setBackground(getResources().getDrawable(R.drawable.gender_selected));
                user.setBackground(getResources().getDrawable(R.drawable.gender_not_selected));
                user.setTextColor(getResources().getColor(R.color.lightGray));
                open_fragment();
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}
