package com.vnoders.spotify_el8alaba.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.ForgotPasswordInfo;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forget_password extends AppCompatActivity {
    private ImageButton back;
    private Button get_link;
    private EditText email_address;
    private String email_address_holder;
    private TextView forgot_password_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        get_link=findViewById(R.id.get_link);
        get_link.setEnabled(false);
        back = (ImageButton)findViewById(R.id.back_button);
        email_address=findViewById(R.id.email_edit_text);
        email_address.addTextChangedListener(email_address_text_watcher);
        forgot_password_status=findViewById(R.id.forgot_password_status);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(forget_password.this,LoginActivit.class);
                startActivity(intent);
            }
        });
        get_link.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordInfo forgotPasswordInfo=new ForgotPasswordInfo(email_address_holder);
                Call<ResponseBody> call= RetrofitClient.getInstance().getAPI(API.class).forgot_password(forgotPasswordInfo);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                            Response<ResponseBody> response) {
                        if(response.code()==200){
                            Intent intent =new Intent(forget_password.this,check_email.class);
                            intent.putExtra("EMAIL_ADDRESS",email_address_holder);
                            startActivity(intent);

                        }
                        else{
                            forgot_password_status.setText(getResources().getString(R.string.forget_password_text2));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        forgot_password_status.setText(getResources().getString(R.string.forget_password_text3));
                    }
                });
            }
        });
    }

    TextWatcher email_address_text_watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        email_address_holder=email_address.getText().toString().trim();
        get_link.setEnabled(!email_address_holder.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}
