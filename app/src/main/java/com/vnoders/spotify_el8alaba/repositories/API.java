package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.Login_info;
import com.vnoders.spotify_el8alaba.models.signup_info;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @Headers({"X-Forwarded-For: 197.52.255.130",
            "Content-Type: application/json"
    })

    @POST("authentication/login")
    Call<ResponseBody> userLogin(@Body Login_info login_info);



     @Headers({"X-Forwarded-For: 197.52.255.130",
         "Content-Type: application/json"
    })
    @POST("authentication/signup")
     Call<ResponseBody> signup(@Body signup_info signup_info);

}
