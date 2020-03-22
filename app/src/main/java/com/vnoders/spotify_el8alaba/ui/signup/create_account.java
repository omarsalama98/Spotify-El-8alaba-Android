package com.vnoders.spotify_el8alaba.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.signup_info;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.signup.signup_dialog;
import com.vnoders.spotify_el8alaba.ui.signup.signup_email;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class create_account extends Fragment {

    private String email_address;
    private String password;
    private String birth_date;
    private String gender;
    private String type;

    private Button create;
    private EditText name;
    private String name_holder;
    private TextView first;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        first = view.findViewById(R.id.text1);
        name = view.findViewById(R.id.name_create_account);
        create = view.findViewById(R.id.create_button);
        create.setEnabled(false);
        create.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                name_holder = name.getText().toString().trim();
                email_address = ((signup_email) getActivity()).getEmail_address();
                password = ((signup_email) getActivity()).getPassword();
                birth_date = ((signup_email) getActivity()).getBirth_date();
                type = "artist";
                gender = ((signup_email) getActivity()).getGender();

                signup_info signup_info = new signup_info(name_holder, email_address, password,
                        password, gender,"1999-03-25T00:00:00.000Z" , type);

                Call<ResponseBody> call = RetrofitClient.getInstance().getAPI().signup(signup_info);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                            Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(),"Email already Exists!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        signup_dialog dialog = new signup_dialog();
                        dialog.show(getFragmentManager(), "signup_dialog");
                    }
                });


            }
        });
        name.addTextChangedListener(create_account_watcher);
        return view;
    }

    private TextWatcher create_account_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            create.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
