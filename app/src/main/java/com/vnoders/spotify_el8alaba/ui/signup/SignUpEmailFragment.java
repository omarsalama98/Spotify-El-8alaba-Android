package com.vnoders.spotify_el8alaba.ui.signup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
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
import com.vnoders.spotify_el8alaba.ui.login.FirstScreen;

/**
 * @author Mohamed Samy
 */
public class SignUpEmailFragment extends Fragment {

    private TextView signup_email_status;
    private TextView signup_email_status2;
    private EditText signup_email;
    private static Button next;
    private ImageButton back;
    private ConnectivityManager connectivityManager;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private WifiManager wifiManager;
    String email_address_holder;
    boolean isConnected;


    private TextWatcher signup_email_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           CheckConnection();
            if (!isConnected) {
                signup_email_status
                        .setText(getResources().getString(R.string.check_connection_signup));
                signup_email_status2.setText("");
            } else {
                email_address_holder = signup_email.getText().toString().trim();
                next.setEnabled(!email_address_holder.isEmpty() && isvalid(email_address_holder));
                if (!isvalid(email_address_holder)) {
                    signup_email_status
                            .setText(getResources().getString(R.string.invalid_email_signup));
                    signup_email_status2
                            .setText(getResources().getString(R.string.invalid_email_signup2));
                } else {
                    signup_email_status
                            .setText(getResources().getString(R.string.confirmation_signup));
                    signup_email_status2.setText("");


                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
     private BroadcastReceiver network_reciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckConnection();
            if (!isConnected) {
                signup_email_status
                        .setText(getResources().getString(R.string.check_connection_signup));
                signup_email_status2.setText("");
                next.setEnabled(false);
            }
            else{
                signup_email_status.setText(getResources().getString(R.string.confirmation_signup));
            }

        }
    };

    /**
     * This method check if there is internet connection or not via wifi or mobile data
     */

    private void CheckConnection() {
        connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        isConnected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                == State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
                == State.CONNECTED;
    }

    /**
     * This method checks if the email address matches the format of a right email address or not
     * @param email_address user's email address
     * @return true if the email address matches , false otherwise
     */
    private boolean isvalid(String email_address) {
        return (Patterns.EMAIL_ADDRESS.matcher(email_address).matches());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_email_fragment, container, false);
        signup_email = view.findViewById(R.id.email_edit_text);
        signup_email_status = view.findViewById(R.id.sign_up_email_status);
        signup_email_status2 = view.findViewById(R.id.sign_up_email_status2);
        signup_email.addTextChangedListener(signup_email_watcher);
        back = getActivity().findViewById(R.id.back_button);
        next = view.findViewById(R.id.next_button);
        next.setEnabled(false);
        CheckConnection();
        if (isConnected) {
            signup_email_status.setText(getResources().getString(R.string.confirmation_signup));
            signup_email_status2.setText("");
        } else {

            signup_email_status.setText(getResources().getString(R.string.check_connection_signup));
            signup_email_status2.setText("");
        }

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
          Intent intent=new Intent(getActivity(), FirstScreen.class);
          startActivity(intent);
            }
        });
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpEmail) getActivity()).setEmail_address(email_address_holder);
                SignUpPassword fragment = new SignUpPassword();
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fragment, "SIGNUP_PASSWORD").commit();

            }
        });

        return view;
    }

   @Override
   public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        getActivity().registerReceiver(network_reciever, intentFilter);
    }
}
