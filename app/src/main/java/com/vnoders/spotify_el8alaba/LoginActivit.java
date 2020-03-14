package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivit extends AppCompatActivity {

        private static final Pattern email_username_pattern=
                Pattern.compile("^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=\\S+$)" +           //no white spaces
                        ".{8,20}" +               //at least 4 characters
                        "$");

private EditText email_edit_text;
private EditText password_edit_text;
private TextView invalid_email;
private Button login_button;
String email_address_holder;
String password_holder;
private API Api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        invalid_email =(TextView) findViewById(R.id.invalid_email);
        login_button=(Button) findViewById(R.id.Login_button);
        login_button.setEnabled(false);
        login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email_username_pattern.matcher(email_address_holder).matches()){
                    invalid_email.setText("Please enter a valid email or username");
                    return;
                }
                login();
            }
        });


        email_edit_text= (EditText) findViewById(R.id.email_edit_text);
        email_address_holder=email_edit_text.getText().toString();

        password_edit_text =(EditText) findViewById(R.id.password_edit_text);
        password_holder=password_edit_text.getText().toString();




        ImageButton back_button=(ImageButton) findViewById(R.id.back_button);
        back_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent first_screen_intent=new Intent(LoginActivit.this,firstScreen.class);
                startActivity(first_screen_intent);
            }
        });

     email_edit_text.addTextChangedListener(loginTextWatcher);
     password_edit_text.addTextChangedListener(loginTextWatcher);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.baseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api =retrofit.create(API.class);


    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(LoginActivit.this,firstScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    private TextWatcher loginTextWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        email_address_holder=email_edit_text.getText().toString().trim();
        password_holder=password_edit_text.getText().toString().trim();
        login_button.setEnabled(!email_address_holder.isEmpty()&& !password_holder.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void login(){
        Call<LoginResponse> call=Api.userLogin(email_address_holder,password_holder);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();

                if(!loginResponse.isError()){
                    Intent intent=new Intent(LoginActivit.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivit.this,loginResponse.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

                   @Override
                   public void onFailure(Call<LoginResponse> call, Throwable t) {
                      // Toast.makeText(LoginActivit.this,"check your internet connection",Toast.LENGTH_SHORT).show();
              connection_dialog dialog= new connection_dialog();
              dialog.show(getFragmentManager(),"connection_dialog");

            }
        });

    }
}
