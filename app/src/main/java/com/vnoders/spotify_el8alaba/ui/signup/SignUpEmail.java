package com.vnoders.spotify_el8alaba.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.R;

/**
 * @author Mohamed Samy
 */
public class SignUpEmail extends AppCompatActivity {

    private String email_address;
    private String password;
    private String birth_date;
    private String gender;
    private String type;
    private ImageButton back;


    public String getPassword() {
        return password;
    }

    /**
     * user's password setter method
     *
     * @param password user's password
     */

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth_date() {
        return birth_date;
    }

    /**
     * user's birth date setter method
     *
     * @param birth_date user's birth date
     */
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    /**
     * user's gender setter method
     *
     * @param gender user's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    /**
     * user's type setter method
     *
     * @param type user's type which specifies if he is an artist or ordinary user
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * user's email address setter method
     *
     * @param email user's email address
     */
    public void setEmail_address(String email) {
        email_address = email;
    }

    public String getEmail_address() {
        return email_address;
    }

    /**
     * this method opens the first fragment which is signup_email fragment
     */
    public void openFragment() {
        SignUpEmailFragment fragment = new SignUpEmailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "SIGNUP_EMAIL_FRAGMENT").commit();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        openFragment();

        back = findViewById(R.id.back_button);
        /*
         *this method take us to the first screen whatever the fragment working now in this activity
         * it will be changed to go back to the correct fragment or to the first screen if the working fragment is the email address fragment
         */
        /*back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(signup_email.this, firstScreen.class);
                startActivity(intent);
            }
        });*/

    }


}
