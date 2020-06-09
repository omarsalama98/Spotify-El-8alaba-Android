package com.vnoders.spotify_el8alaba.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.vnoders.spotify_el8alaba.ConnectionDialog;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.LoginInfo;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.response.signup.CurrentlyPlaying;
import java.io.IOException;
import org.threeten.bp.LocalDateTime;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivit extends AppCompatActivity {


    private TextView login_status;
    private EditText email_edit_text;
    private EditText password_edit_text;
    private TextView invalid_email;
    private Button login_button;
    private Button forget_password;
    String email_address_holder;
    String password_holder;
    SharedPreferences sharedPreferences;

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            email_address_holder = email_edit_text.getText().toString().trim();
            password_holder = password_edit_text.getText().toString().trim();
            login_button.setEnabled(!email_address_holder.isEmpty() && !password_holder.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void login() {
        LoginInfo loginInfo = new LoginInfo(email_address_holder, password_holder);
        Call<ResponseBody> call = RetrofitClient.getInstance().getAPI(API.class)
                .userLogin(loginInfo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonRespone = null;
                try {
                    if (response.code() == 200) {
                        AndroidThreeTen.init(getApplication());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime now = LocalDateTime.now();
                        String loginDate=now.format(formatter);
                        Log.d("LOGINDATE",loginDate);
                        Gson gson = new Gson();
                        try {
                            jsonRespone = response.body().string();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObject = new JSONObject(jsonRespone);
                        String token = jsonObject.getString("token");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putString("loginDate",loginDate);
                        editor.commit();
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");
                        if (user.has("userInfo")) {
                            JSONObject userInfo = user.getJSONObject("userInfo");
                            JSONObject jsonCurrentlyPlayed = userInfo
                                    .getJSONObject("currentlyPlaying");
                            String id=userInfo.getString("id");
                            editor.putString("id", id).commit();
                            editor.putString("type", userInfo.getString("type")).commit();
                            CurrentlyPlaying currentlyPlaying = gson.fromJson(
                                    jsonCurrentlyPlayed.toString(), CurrentlyPlaying.class);
                        } else {
                            JSONObject jsonCurrentlyPlayed = user
                                    .getJSONObject("currentlyPlaying");
                            String id=user.getString("id");
                            editor.putString("id", id).commit();
                            editor.putString("type", user.getString("type")).commit();
                            CurrentlyPlaying currentlyPlaying = gson.fromJson(
                                    jsonCurrentlyPlayed.toString(), CurrentlyPlaying.class);
                        }
                        RetrofitClient.getInstance().setToken(token);
                        Intent intent = new Intent(LoginActivit.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        login_status.setText(getResources().getString(R.string.login_text1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(getFragmentManager(), "connection_dialog");
                login_status.setText("");

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivit.this, FirstScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_status = findViewById(R.id.login_status);
        sharedPreferences = getSharedPreferences(
                getResources().getString(R.string.access_token_preference), MODE_PRIVATE);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);
        invalid_email = findViewById(R.id.invalid_email);
        login_button = findViewById(R.id.Login_button);
        forget_password = findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivit.this, forget_password.class);
                startActivity(intent);
            }
        });
        login_button.setEnabled(false);
        login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login_status.setText("");
                if (!Patterns.EMAIL_ADDRESS.matcher(email_address_holder).matches()) {
                    invalid_email.setText("Please enter a valid email or username");

                } else {
                    invalid_email.setText("");
                    login();
                }

            }
        });

        email_edit_text = findViewById(R.id.email_edit_text);
        email_address_holder = email_edit_text.getText().toString();

        password_edit_text = findViewById(R.id.password_edit_text);
        password_holder = password_edit_text.getText().toString();

        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent first_screen_intent = new Intent(LoginActivit.this, FirstScreen.class);
                startActivity(first_screen_intent);

            }
        });

        email_edit_text.addTextChangedListener(loginTextWatcher);
        password_edit_text.addTextChangedListener(loginTextWatcher);


    }


}
