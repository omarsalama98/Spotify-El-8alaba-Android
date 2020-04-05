package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.Login_info;
import com.vnoders.spotify_el8alaba.models.forgot_password_info;
import com.vnoders.spotify_el8alaba.models.signup_info;
import com.vnoders.spotify_el8alaba.response.signup.signup_response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * This interface will include all the back-end functions which will handle the API requests
 *
 * @author Mohamed Samy
 */
public interface API {

    /**
     * This method handles the login request
     *
     * @param login_info an object from login_info class which stores the login needed information
     *                   {email address, password}
     *
     * @return ResponseBody and it will be changed in the future to a login response
     */
    @Headers({"X-Forwarded-For: 197.52.255.130",
            "Content-Type: application/json"
    })

    @POST("authentication/login")
    Call<signup_response> userLogin(@Body Login_info login_info);

    /**
     * This method handles the registration request
     *
     * @param signup_info an object from signup_info class which contains all the registration
     *                    needed information {name,email,password,birth date,gender}
     *
     * @return ResponesBody which will be changed in the next sprint to a singup response
     */

    @Headers({"X-Forwarded-For: 197.52.255.130",
            "Content-Type: application/json"
    })
    @POST("authentication/signup")
    Call<signup_response> signup(@Body signup_info signup_info);

    /**
     * This method handles forgot password action
     * @param forgot_password_info an object from forgot password info holds the email address or username of the user
     * @return ResponseBody that will be changed in future to forgotpassword response body
     */
    @Headers({
            "X-Forwarded-For: 197.52.255.130",
            "Content-Type: application/json"
    })

    @POST("authentication/forgotPassword")
    Call<ResponseBody> forgot_password(@Body forgot_password_info forgot_password_info);

}
