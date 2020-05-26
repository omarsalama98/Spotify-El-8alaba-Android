package com.vnoders.spotify_el8alaba.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.rtt.ResponderLocation;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vnoders.spotify_el8alaba.ConnectionDialog;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.FacebookToken;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.signup.SignUpEmail;
import java.util.Arrays;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstScreen extends AppCompatActivity {

    private Button Login_button;
    private Button sign_up_button;
    private LoginButton facebook_button;
    private CallbackManager callbackManager;


   /* AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                AccessToken currentAccessToken) {

            if (currentAccessToken != null) {

                Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                RetrofitClient.getInstance().setToken(currentAccessToken.getToken());
                finish();
            }

        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_first_screen);
        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);
        callbackManager = CallbackManager.Factory.create();
        facebook_button = findViewById(R.id.facebook_button);
        facebook_button.setReadPermissions(Arrays.asList("email","user_birthday"));
        facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            AccessToken token=loginResult.getAccessToken();
                FacebookToken facebookToken=new FacebookToken(token.getToken());
                Log.v("TAG", "Token::" + token.getToken());
                Toast.makeText(FirstScreen.this,token.getToken(),Toast.LENGTH_SHORT).show();
                Call<ResponseBody> call=RetrofitClient.getInstance().getAPI(API.class).loginFB(facebookToken);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                            Response<ResponseBody> response) {
                        Toast.makeText(FirstScreen.this,"SUCCESS",Toast.LENGTH_LONG).show();
                        Log.d("RESPONSE FACEBOOK",response.toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(FirstScreen.this,"FAILURE",Toast.LENGTH_LONG).show();

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