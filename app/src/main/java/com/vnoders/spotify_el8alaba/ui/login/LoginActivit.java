package com.vnoders.spotify_el8alaba.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.connection_dialog;
import com.vnoders.spotify_el8alaba.firstScreen;
import com.vnoders.spotify_el8alaba.models.Login_info;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.response.signup.signup_response;
import okhttp3.ResponseBody;
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
        Login_info login_info = new Login_info(email_address_holder, password_holder);
    Call<signup_response> call=RetrofitClient.getInstance().getAPI().userLogin(login_info);
    call.enqueue(new Callback<signup_response>() {
        @Override
        public void onResponse(Call<signup_response> call, Response<signup_response> response) {
            if(response.code()==200){
                signup_response signup_response=response.body();
                String token=signup_response.getToken();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("token",token);
                editor.commit();
                Intent intent = new Intent(LoginActivit.this, MainActivity.class);
                startActivity(intent);
            }
            else{
                login_status.setText(getResources().getString(R.string.login_text1));
            }
        }

        @Override
        public void onFailure(Call<signup_response> call, Throwable t) {
            connection_dialog dialog = new connection_dialog();
            dialog.show(getFragmentManager(), "connection_dialog");
            login_status.setText("");

        }
    });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivit.this, firstScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_status=(TextView)findViewById(R.id.login_status);
        sharedPreferences=getSharedPreferences(getResources().getString(R.string.access_token_preference),MODE_PRIVATE);
        invalid_email = findViewById(R.id.invalid_email);
        login_button = findViewById(R.id.Login_button);
        forget_password=findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivit.this,forget_password.class);
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

                }
                else{
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
                Intent first_screen_intent = new Intent(LoginActivit.this, firstScreen.class);
                startActivity(first_screen_intent);
            }
        });

        email_edit_text.addTextChangedListener(loginTextWatcher);
        password_edit_text.addTextChangedListener(loginTextWatcher);


    }


}
