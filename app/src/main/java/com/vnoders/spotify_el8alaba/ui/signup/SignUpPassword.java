package com.vnoders.spotify_el8alaba.ui.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpPassword extends Fragment {
    private FragmentManager fragmentManager;
    private TextView password_status;
    private TextView password_status2;
    private Button next;
    private ImageButton back_button;
    private EditText password_text;
    String password_holder;
    private TextWatcher signup_password_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            password_holder = password_text.getText().toString().trim();
            if (password_holder.length() < 8) {
                next.setEnabled(false);
                password_status.setText(getResources().getString(R.string.password_text));
                password_status2.setText("");
            } else {
                if (isValidPassword(password_holder)) {
                    password_status.setText("");
                    password_status2.setText("");
                    next.setEnabled(true);
                } else {
                    next.setEnabled(false);
                    password_status.setText(getResources().getString(R.string.password_weak));
                    password_status2.setText(getResources().getString(R.string.password_weak2));

                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentManager = getActivity().getSupportFragmentManager();

        View view = inflater.inflate(R.layout.fragment_signup_password, container, false);
        back_button =getActivity().findViewById(R.id.back_button);
        next = view.findViewById(R.id.next_button);
        next.setEnabled(false);
        password_status = view.findViewById(R.id.password_status);
        password_status2 = view.findViewById(R.id.password_status2);
        password_status2.setText("");

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpEmail) getActivity()).setPassword(password_holder);
                SignUpBirthDate fragment = new SignUpBirthDate();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fragment, "SIGNUP_BIRTHDATE").commit();
            }
        });


           back_button.setOnClickListener(new OnClickListener() {
           @Override

           public void onClick(View v) {
           fragmentManager.popBackStack();
           }
       });
        password_text = view.findViewById(R.id.password_edit_text);

        password_text.addTextChangedListener(signup_password_watcher);

        return view;

    }


}
