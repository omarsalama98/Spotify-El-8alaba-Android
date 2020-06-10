package com.vnoders.spotify_el8alaba.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.vnoders.spotify_el8alaba.ConnectionDialog;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.FacebookToken;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.response.signup.CurrentlyPlaying;
import com.vnoders.spotify_el8alaba.ui.signup.SignUpEmail;
import com.vnoders.spotify_el8alaba.ui.trackplayer.TrackViewModel;
import java.io.IOException;
import java.util.Arrays;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FirstScreen extends AppCompatActivity {

    private Button Login_button;
    private Button sign_up_button;
    private LoginButton facebook_button;
    private CallbackManager callbackManager;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(
                getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_first_screen);
        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        // initialize status of init again
        TrackViewModel.getInstance().updateInitRequired(true);

        callbackManager = CallbackManager.Factory.create();
        facebook_button = findViewById(R.id.facebook_button);
        facebook_button.setReadPermissions(Arrays.asList("email", "user_birthday"));
        //send a request to facebook API to get a token for the user.
        facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = loginResult.getAccessToken();
                FacebookToken facebookToken = new FacebookToken(token.getToken());
                Log.v("TAG", "Token::" + token.getToken());
                Call<ResponseBody> call = RetrofitClient.getInstance().getAPI(API.class)
                        .loginFB(facebookToken);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                            Response<ResponseBody> response) {
                        Log.d("RESPONSE FACEBOOK", response.toString());
                        String jsonRespone = null;
                        try {
                            if (response.code() == 200) {
                                String[] header=response.headers().get("Set-Cookie").split("Expires");
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss");
                                String acessTokenDate=header[1].substring(1,26);
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
                                editor.putString("loginDate",acessTokenDate);
                                editor.commit();
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");
                                JSONObject jsonCurrentlyPlayed = user
                                        .getJSONObject("currentlyPlaying");
                                String id = user.getString("id");
                                editor.putString("id", id).commit();
                                CurrentlyPlaying currentlyPlaying = gson.fromJson(
                                        jsonCurrentlyPlayed.toString(), CurrentlyPlaying.class);
                                RetrofitClient.getInstance().setToken(token);
                                Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ConnectionDialog dialog = new ConnectionDialog();
                        dialog.show(getFragmentManager(), "connection_dialog");
                    }
                });


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                ConnectionDialog dialog = new ConnectionDialog();
                dialog.show(getFragmentManager(), "connection_dialog");
            }
        });

        sign_up_button = findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstScreen.this, SignUpEmail.class);
                startActivity(intent);
            }
        });
        Login_button = findViewById(R.id.Login_button);
        Login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Login_intent = new Intent(FirstScreen.this, LoginActivit.class);
                startActivity(Login_intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}