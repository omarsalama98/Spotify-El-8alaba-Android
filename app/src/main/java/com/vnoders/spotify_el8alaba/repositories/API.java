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
 *
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

    /**
     * This method returns all info about the current user which is needed to load the current user profile.
     * @return CurrentUserProfile object containing all information about the current user.
     */
    @GET("users/me")
    Call<CurrentUserProfile> getCurrentUserProfile();

    /**
     * This method is used to update current user information, in the application it's used to update
     * user's name.
     * @param updateUserInfo an object containing the new information needed to be updated.
     * @return Response body indicate whether the request is successful or failed.
     */

    @PATCH("users")
    Call<ResponseBody> updateUserInfo(@Body UpdateUserInfo updateUserInfo);

    /**
     * This method used to handle login with Facebook as I received a Facebook token it's sent to our
     * API to generate account to this user.
     * @param facebookToken Facebook token received from Facebook API.
     * @return Response body containing info about the request.
     */
    @POST("authentication/facebook-token")
    Call <ResponseBody> loginFB(@Body FacebookToken facebookToken);

    /**
     * This method is called when a user is logged into the application to add the token received
     * by Firebase to the notification tokens which belong to this user.
     * @param token Firebase notification token.
     * @return Response body containing info about the request.
     */

    @POST("users/notification-token")
    Call<ResponseBody> addNotificationToken(@Body NotificationToken token);

    /**
     * This method is used to changer the current user profile picture
     * @param image an image file sent to the API in parts.
     * @return Response body containing info about the request.
     */
    @Multipart
    @POST("users/update-avatar")
    Call<ResponseBody> changeProfilePicture(@Part MultipartBody.Part image);

    /**
     * @return This function gets the users followed or created playlists
     */
    @GET("me/playlists?limit=50")
    Call<GetUsersPlaylists> getCurrentUsersPlaylists();

    /**
     * This method is called to get the current user followed users to list them in the following list.
     * @return List of followed users.
     */
    @GET("me/following?limit=20")
    Call<List<CurrentUserProfile>> getFollowing();

    /**
     * This method is called to retrieve all notification settings belongs to the current user.
     * @return Response body containing list of booleans ,each boolean indicates the status
     * of a specific notification preference
     */
    @GET("users/notification-status")
    Call<ResponseBody> getNotificationStatus();

    /**
     * This method is called when a user wants to change his notification settings.
     * @param notificationStatus the new notification settings set by the user.
     * @return Response body holding information about the request.
     */
    @POST("users/notification-toggle")
    Call<ResponseBody> setNotificationStatus(@Body NotificationStatus notificationStatus);

    /**
     * This method is called when a user preform a logout to delete his notification token , so he won't
     * receive any more notifications.
     * @param token Firebase token stored in shared preference.
     * @return Response body holding information about the request.
     */
    @DELETE("users/notification-token/{token}")
    Call<ResponseBody> deleteNotificationToken(@Path("token") String token);

    /**
     * This method is called when the current user wants to navigate to another user profile and see his information.
     * @param id the id of the target user.
     * @return User's data wrapped in {@link UserProfileData}.
     */
    @GET("users/{id}")
    Call<UserProfileData> getUserProfileData(@Path("id") String id);

    /**
     * This method is called to retrieve current user's followers to list them in the followers list.
     * @return List of users containing some info like image,name and followers number.
     */
    @GET("me/followers?limit=20&offset=0")
    Call<List<CurrentUserProfile>> getFollowers();

    /**
     * this method is called to get the user's last recent activities and list them in his profile.
     * @return list of notifications which will be displayed in his profile.
     */
    @GET("users/notifications")
    Call<RecentActivities> getRecentActivities();

    /**
     * This method is used to check if the current user follows a user or not.
     * @param id of the target user.
     * @return boolean indicates whether this user is followed or not.
     */
    @GET("me/following/contains")
    Call<List<Boolean>> checkFollowing(@Query("ids") String id);

    /**
     * This method is called when the current user wants to follow a user.
     * @param requestBodyIds containing id of the targeted user.
     * @return nothing but a response code.
     */
    @PUT("me/following")
    Call<Void> followUser(@Body RequestBodyIds requestBodyIds);

    /**
     * This method is called when the current user wants to un follow a user.
     * @param requestBodyIds containing id of the targeted user.
     * @return nothing but a response code.
     */
    @HTTP(method = "DELETE",path = "me/following?type=user",hasBody = true)
    Call<Void> unFollowUser(@Body RequestBodyIds requestBodyIds);

    /**
     * This method is used when a current user wants to retrieve other user's followers.
     * @param id of the targeted user.
     * @return list of users containing information like name,image and followers number.
     */

    @GET("users/{id}/followers?limit=20&offset=0")
    Call<List<CurrentUserProfile>> getUserFollowers(@Path("id") String id);

    /**
     * This method is used to change current user's password.
     * @param changePasswordData containing information about the current password and new password.
     * @return Response body holds information about the request.
     */
    @PATCH("authentication/updatePassword")
    Call<ResponseBody> changePassword(@Body ChangePasswordData changePasswordData);

    /**
     * This method is used when a current user wants to retrieve other user's following.
     * @param id of the targeted user.
     * @return list of users containing information like name,image and followers number.
     */
    @GET("users/{id}/following?limit=20&offset")
    Call<List<CurrentUserProfile>> getUserFollowing(@Path("id") String id);

}