package com.vnoders.spotify_el8alaba.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;
import java.util.Calendar;

/**
 *
 */
public class SignUpBirthDate extends Fragment {

    private DatePicker datePicker;
    private DatePicker.OnDateChangedListener mDateSetListner;
    private Button next;
    private TextView bDate_status;
    private ImageButton back;
    private FragmentManager fragmentManager;
    String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_birthdate, container, false);
        back=getActivity().findViewById(R.id.back_button);
        next = view.findViewById(R.id.next_button);
        next.setEnabled(false);
        bDate_status = view.findViewById(R.id.sign_up_Bdate_status);
        bDate_status.setText("");
        datePicker = view.findViewById(R.id.user_birthdate);
        datePicker.setBackgroundColor(getResources().getColor(R.color.white));
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);



        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpEmail)getActivity()).setBirth_date(date);
                SignUpGender fragment = new SignUpGender();
                 fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fragment, "SIGNUP_GENDER").commit();


            }
        });

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                if (year > 2000) {
                    next.setEnabled(false);
                    bDate_status.setText(getResources().getString(R.string.birthday_text));
                } else {
                    date=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                    next.setEnabled(true);
                    bDate_status.setText("");
                }
            }

        });

        return view;
    }
}
