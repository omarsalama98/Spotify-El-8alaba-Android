package com.vnoders.spotify_el8alaba.ui.signup;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;

/**
 *
 */
public class SignUpGender extends Fragment {

    private Button male;
    private Button female;
    private ImageButton back;
    private FragmentManager fragmentManager;
    /**
     * This method will be called when the user choose if his gender is male or female and it takes the user to next fragment which is the
     * <br>
     * the user_type fragment
     */
    private void open_fragment() {

        UserType fragment = new UserType();
         fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);

        transaction.replace(R.id.fragment_container, fragment, "USER_TYPE").commit();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_gender, container, false);
        male = view.findViewById(R.id.gender_male);
        female = view.findViewById(R.id.gender_female);
        back=getActivity().findViewById(R.id.back_button);

        /*
          if the user choose the gender as male the button of the male will be changed and also of the female
           and open the create account fragment
         */
        male.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpEmail) getActivity()).setGender("m");
                male.setTextColor(getResources().getColor(R.color.white));
                male.setBackground(getResources().getDrawable(R.drawable.gender_selected));
                female.setBackground(getResources().getDrawable(R.drawable.gender_not_selected));
                female.setTextColor(getResources().getColor(R.color.lightGray));
                open_fragment();
            }
        });

        /*
          if the user choose the gender as male the button of the male will be changed and also of the female
           and open the create account fragment
         */

        female.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpEmail) getActivity()).setGender("f");
                female.setTextColor(getResources().getColor(R.color.white));
                female.setBackground(getResources().getDrawable(R.drawable.gender_selected));
                male.setBackground(getResources().getDrawable(R.drawable.gender_not_selected));
                male.setTextColor(getResources().getColor(R.color.lightGray));
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
