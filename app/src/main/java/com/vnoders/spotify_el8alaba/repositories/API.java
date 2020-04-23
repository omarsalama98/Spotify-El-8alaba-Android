package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.CurrentlyPlayingTrack;
import com.vnoders.spotify_el8alaba.models.LoginInfo;
import com.vnoders.spotify_el8alaba.models.ForgotPasswordInfo;
import com.vnoders.spotify_el8alaba.models.SignUpInfo;
import com.vnoders.spotify_el8alaba.models.UpdateUserInfo;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import com.vnoders.spotify_el8alaba.response.signup.SignUpResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
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
     * @param loginInfo an object from login_info class which stores the login needed information
     *                   {email address, password}
     *
     * @return ResponseBody and it will be changed in the future to a login response
     */
    @Headers({"X-Forwarded-For: 197.52.255.130",
        //  "Content-Type: application/json"
    })
    @POST("authentication/login")
    Call<ResponseBody>userLogin(@Body LoginInfo loginInfo);

    /**
     * This method handles the registration request
     *
     * @param signUpInfo an object from signup_info class which contains all the registration
     *                    needed information {name,email,password,birth date,gender}
     *
     * @return singup response which contains the user's info
     */

    @Headers({"X-Forwarded-For: 197.52.255.130",
  //          "Content-Type: application/json"
    })
    @POST("authentication/signup")
    Call<ResponseBody> signup(@Body SignUpInfo signUpInfo);

    /**
     * This method handles forgot password action
     * @param forgotPasswordInfo an object from forgot password info holds the email address or username of the user
     * @return ResponseBody that will be changed in future to forgotpassword response body
     */
    @Headers({
            "X-Forwarded-For: 197.52.255.130",
 //           "Content-Type: application/json"
    })

    @POST("authentication/forgotPassword")
    Call<ResponseBody> forgot_password(@Body ForgotPasswordInfo forgotPasswordInfo);

    @GET("me/player")
    Call<CurrentlyPlayingTrack> getCurrentlyPlaying();

    @GET("users/me")
    Call<CurrentUserProfile> getCurrentUserProfile();

    @PATCH("users")
    Call<ResponseBody> updateUserInfo(@Body UpdateUserInfo updateUserInfo);
}
