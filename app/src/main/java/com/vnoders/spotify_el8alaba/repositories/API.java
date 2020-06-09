package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.ChangePasswordData;
import com.vnoders.spotify_el8alaba.models.FacebookToken;
import com.vnoders.spotify_el8alaba.models.ForgotPasswordInfo;
import com.vnoders.spotify_el8alaba.models.Notifications.NotificationStatus;
import com.vnoders.spotify_el8alaba.models.Notifications.NotificationToken;
import com.vnoders.spotify_el8alaba.models.LoginInfo;
import com.vnoders.spotify_el8alaba.models.SignUpInfo;
import com.vnoders.spotify_el8alaba.models.UpdateUserInfo;
import com.vnoders.spotify_el8alaba.models.library.RequestBodyIds;
import com.vnoders.spotify_el8alaba.models.userProfile.GetUsersPlaylists;
import com.vnoders.spotify_el8alaba.response.CurrentUserProfile.CurrentUserProfile;
import com.vnoders.spotify_el8alaba.response.Notifications.RecentActivities;
import com.vnoders.spotify_el8alaba.response.UserProfileData;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<ResponseBody> addNotificationToken(@Body NotificationToken token);

    @Multipart
    @POST("users/update-avatar")
    Call<ResponseBody> changeProfilePicture(@Part MultipartBody.Part image);

    /**
     * @return This function gets the users followed or created playlists
     */
    @GET("me/playlists?limit=50")
    Call<GetUsersPlaylists> getCurrentUsersPlaylists();

    @GET("me/following?limit=20")
    Call<List<CurrentUserProfile>> getFollowing();

    @GET("users/notification-status")
    Call<ResponseBody> getNotificationStatus();

    @POST("users/notification-toggle")
    Call<ResponseBody> setNotificationStatus(@Body NotificationStatus notificationStatus);

    @DELETE("users/notification-token/{token}")
    Call<ResponseBody> deleteNotificationToken(@Path("token") String token);

    @GET("users/{id}")
    Call<UserProfileData> getUserProfileData(@Path("id") String id);

    @GET("me/followers?limit=20&offset=0")
    Call<List<CurrentUserProfile>> getFollowers();

    @GET("users/notifications")
    Call<RecentActivities> getRecentActivities();

    @GET("me/following/contains")
    Call<List<Boolean>> checkFollowing(@Query("ids") String id);

    @PUT("me/following")
    Call<Void> followUser(@Body RequestBodyIds requestBodyIds);

    @HTTP(method = "DELETE",path = "me/following?type=user",hasBody = true)
    Call<Void> unFollowUser(@Body RequestBodyIds requestBodyIds);

    @GET("users/{id}/followers?limit=20&offset=0")
    Call<List<CurrentUserProfile>> getUserFollowers(@Path("id") String id);

    @PATCH("authentication/updatePassword")
    Call<ResponseBody> changePassword(@Body ChangePasswordData changePasswordData);

    @GET("users/{id}/following?limit=20&offset")
    Call<List<CurrentUserProfile>> getUserFollowing(@Path("id") String id);

}