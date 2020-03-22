package com.vnoders.spotify_el8alaba.ui.signup;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.firstScreen;

public class signup_email extends AppCompatActivity {

    private String email_address;
    private String password;
    private String birth_date;
    private String gender;
    private String type;

    private ImageButton back;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmail_address(String email){
        email_address=email;
    }

    public  String getEmail_address(){
        return email_address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);
        openFragment();



         back=(ImageButton) findViewById(R.id.back_button);
         back.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent=new Intent(signup_email.this, firstScreen.class);
                 startActivity(intent);
             }
         });

    }

    public void openFragment(){
        signup_email_fragment fragment= new signup_email_fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, "SIGNUP_EMAIL_FRAGMENT").commit();

    }




}
