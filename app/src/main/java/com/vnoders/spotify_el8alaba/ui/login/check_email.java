package com.vnoders.spotify_el8alaba.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.vnoders.spotify_el8alaba.R;

/**
 *
 */

public class check_email extends AppCompatActivity {

    private String email_address_holder;
    private TextView email_address;
    private Button open_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);
        email_address_holder=getIntent().getStringExtra("EMAIL_ADDRESS");
        email_address=findViewById(R.id.email_address_holder);
        email_address.setText(email_address_holder);
        open_email=findViewById(R.id.open_email);
        open_email.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_MAIN);
                email.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(Intent.createChooser(email, "Open email app"));
            }
        });
    }

}
