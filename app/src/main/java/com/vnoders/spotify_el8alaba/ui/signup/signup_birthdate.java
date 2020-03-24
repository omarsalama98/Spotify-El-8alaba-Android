package com.vnoders.spotify_el8alaba.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;
import java.util.Calendar;


public class signup_birthdate extends Fragment {

    private DatePicker datePicker;
    private DatePicker.OnDateChangedListener mDateSetListner;
    private Button next;
    private TextView bDate_status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_birthdate, container, false);
        next = view.findViewById(R.id.next_button);
        next.setEnabled(false);
        bDate_status = view.findViewById(R.id.sign_up_Bdate_status);
        bDate_status.setText("");
        datePicker = view.findViewById(R.id.user_birthdate);
        datePicker.setBackgroundColor(getResources().getColor(R.color.white));
        Calendar cal = Calendar.getInstance();
        // cal.setTimeInMillis((Long)System.currentTimeMillis()/1000L);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((signup_email) getActivity()).setBirth_date(datePicker.toString());
                signup_gender fragment = new signup_gender();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, fragment, "SIGNUP_BIRTHDATE").commit();

            }
        });

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                if (year > 2000) {
                    next.setEnabled(false);
                    bDate_status.setText(getResources().getString(R.string.birthday_text));
                } else {
                    next.setEnabled(true);
                    bDate_status.setText("");
                }
            }

        });

        return view;
    }
}
