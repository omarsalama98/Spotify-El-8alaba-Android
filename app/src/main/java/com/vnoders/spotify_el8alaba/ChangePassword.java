package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vnoders.spotify_el8alaba.models.ChangePasswordData;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. Use the {@link ChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePassword extends Fragment {

        private EditText currentPassword;
        private EditText newPassword;
        private EditText confirmNewPassword;
        private Button saveChanges;
        private TextView changePasswordAlert;
        private ImageButton backButton;


    public ChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment ChangePassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePassword newInstance(String param1, String param2) {
        ChangePassword fragment = new ChangePassword();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        View view=inflater.inflate(R.layout.fragment_change_password, container, false);
        currentPassword=view.findViewById(R.id.current_password);
        newPassword=view.findViewById(R.id.new_password);
        confirmNewPassword=view.findViewById(R.id.confirm_new_password);
        saveChanges=view.findViewById(R.id.save_change_password);
        changePasswordAlert=view.findViewById(R.id.change_password_status);
        changePasswordAlert.setVisibility(View.INVISIBLE);
        backButton=view.findViewById(R.id.back_button_password);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());
        saveChanges.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        return view;
    }
    public void changePassword(){

        if(currentPassword.getText().toString().matches("")){
            changePasswordAlert.setText("Enter your current password");
            changePasswordAlert.setVisibility(View.VISIBLE);
            return;
        }
        if(newPassword.getText().toString().matches("")){
            changePasswordAlert.setText("Enter your new password");
            changePasswordAlert.setVisibility(View.VISIBLE);
            return;
        }
        if(confirmNewPassword.getText().toString().matches("")){
            changePasswordAlert.setText("Confirm your new password");
            changePasswordAlert.setVisibility(View.VISIBLE);
            return;
        }
        String currentPasswordText=currentPassword.getText().toString();
        String newPasswordText=newPassword.getText().toString();
        String confirmNewPasswordText=confirmNewPassword.getText().toString();
        if(!newPasswordText.equals(confirmNewPasswordText)){
            changePasswordAlert.setText("your new password and password confirmation don't match!");
            changePasswordAlert.setVisibility(View.VISIBLE);
            return;
        }
        if(!isValidPassword(newPasswordText)){
            changePasswordAlert.setText("This password is too weak. Try including numbers, symbols or uppercase letters.");
            return;
        }
        ChangePasswordData changePasswordData=new ChangePasswordData(currentPasswordText,newPasswordText,confirmNewPasswordText);
        Call<ResponseBody> call= RetrofitClient.getInstance().getAPI(API.class).changePassword(changePasswordData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Toast.makeText(getActivity(),"your password is updated successfully",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG).show();

                }
                changePasswordAlert.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(getActivity().getFragmentManager(), "connection_dialog");
            }
        });

    }
        private boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}