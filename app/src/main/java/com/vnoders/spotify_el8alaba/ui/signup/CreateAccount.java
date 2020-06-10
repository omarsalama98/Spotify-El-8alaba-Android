package com.vnoders.spotify_el8alaba.ui.signup;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.SignUpInfo;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Mohamed Samy
 */
public class CreateAccount extends Fragment {

    private String email_address;
    private String password;
    private String birth_date;
    private String gender;
    private String type;
    private Button create;
    private EditText name;
    private String name_holder;
    private ImageButton back;
    private FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;


    private TextWatcher create_account_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            name_holder = name.getText().toString();
            create.setEnabled(!name_holder.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        //first = view.findViewById(R.id.text1);
        sharedPreferences = getActivity()
                .getSharedPreferences(getResources().getString(R.string.access_token_preference),
                        MODE_PRIVATE);
        name = view.findViewById(R.id.name_create_account);
        create = view.findViewById(R.id.create_button);
        back = getActivity().findViewById(R.id.back_button);
        create.setEnabled(false);
        create.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                name_holder = name.getText().toString().trim();
                email_address = ((SignUpEmail) getActivity()).getEmail_address();
                password = ((SignUpEmail) getActivity()).getPassword();
                birth_date = ((SignUpEmail) getActivity()).getBirth_date();
                type = ((SignUpEmail) getActivity()).getType();
                gender = ((SignUpEmail) getActivity()).getGender();

                SignUpInfo SignUpInfo = new SignUpInfo(name_holder, email_address, password,
                        password, gender, birth_date, type);

                Call<ResponseBody> call = RetrofitClient.getInstance().getAPI(API.class).signup(
                        SignUpInfo);
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call,
                            Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                ConfirmationEmailDialog dialog = new ConfirmationEmailDialog();
                                dialog.show(getFragmentManager(), "CONFIRMATIONEMAILDIALOG");

                            } else {
                                Toast.makeText(getActivity(), "Email already Exists!",
                                        Toast.LENGTH_LONG).show();
                            }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        SignUpDialog dialog = new SignUpDialog();
                        dialog.show(getFragmentManager(), "signup_dialog");
                    }
                });


            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        name.addTextChangedListener(create_account_watcher);

        return view;
    }
}
