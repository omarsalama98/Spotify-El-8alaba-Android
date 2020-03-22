package com.vnoders.spotify_el8alaba.ui.signup;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;


public class signup_gender extends Fragment {
private  Button male;
private Button female;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signup_gender, container, false);
        male= view.findViewById(R.id.gender_male);
        female=view.findViewById(R.id.gender_female);


        male.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((signup_email)getActivity()).setGender("m");
                male.setTextColor(getResources().getColor(R.color.white));
                male.setBackground(getResources().getDrawable(R.drawable.gender_selected));
                female.setBackground(getResources().getDrawable(R.drawable.gender_not_selected));
                female.setTextColor(getResources().getColor(R.color.lightGray));
                open_fragment();
            }
        });

        female.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((signup_email)getActivity()).setGender("f");
                female.setTextColor(getResources().getColor(R.color.white));
                female.setBackground(getResources().getDrawable(R.drawable.gender_selected));
                male.setBackground(getResources().getDrawable(R.drawable.gender_not_selected));
                male.setTextColor(getResources().getColor(R.color.lightGray));
                open_fragment();
            }
        });
        return view;
    }

    private void open_fragment(){

        create_account fragment= new create_account();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container,fragment,"CREATE_ACCOUNT").commit();

    }
}
