package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vnoders.spotify_el8alaba.ui.signup.signup_email;

public class firstScreen extends AppCompatActivity {

    private Button Login_button;
    private Button sign_up_button;
    private LoginButton facebook_button;
    private CallbackManager callbackManager;
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                AccessToken currentAccessToken) {

            if (currentAccessToken != null) {

                Intent intent = new Intent(firstScreen.this, MainActivity.class);
                startActivity(intent);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);


        callbackManager = CallbackManager.Factory.create();
        facebook_button = findViewById(R.id.facebook_button);
        facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        sign_up_button = findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstScreen.this, signup_email.class);
                startActivity(intent);
            }
        });
        Login_button = findViewById(R.id.Login_button);
        Login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Login_intent = new Intent(firstScreen.this, LoginActivit.class);
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