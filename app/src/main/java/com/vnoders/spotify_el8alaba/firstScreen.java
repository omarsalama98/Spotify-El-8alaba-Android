package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class firstScreen extends AppCompatActivity {
private Button Login_button;
private Button sign_up_button;
private Button facebook_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        Login_button = findViewById(R.id.Login_button);
        Login_button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent Login_intent = new Intent(firstScreen.this, LoginActivity.class);
        startActivity(Login_intent);
    }
});


    }



}
