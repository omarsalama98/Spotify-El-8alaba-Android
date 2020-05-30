package com.vnoders.spotify_el8alaba.repositories;

import android.print.PrintAttributes.Resolution;
import com.vnoders.spotify_el8alaba.models.FacebookToken;

import com.vnoders.spotify_el8alaba.models.LoginInfo;
import com.vnoders.spotify_el8alaba.models.ForgotPasswordInfo;
import com.vnoders.spotify_el8alaba.models.SignUpInfo;
import com.vnoders.spotify_el8alaba.models.UpdateUserInfo;
import com.vnoders.spotify_el8alaba.models.userProfile.GetUsersPlaylists;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

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


    @GET("users/me")
    Call<CurrentUserProfile> getCurrentUserProfile();

    @PATCH("users")
    Call<ResponseBody> updateUserInfo(@Body UpdateUserInfo updateUserInfo);

    @POST("authentication/facebook-token")
    Call <ResponseBody> loginFB(@Body FacebookToken facebookToken);


    @POST("users/notification-token")
    Call<ResponseBody> addNotificationToken(@Body String token);

    @Multipart
    @POST("users/update-avatar")
    Call<ResponseBody> changeProfilePicture(@Part MultipartBody.Part image);

    /**
     * @return This function gets the users followed or created playlists
     */
    @GET("me/playlists?limit=50")
    Call<GetUsersPlaylists> getCurrentUsersPlaylists();
}