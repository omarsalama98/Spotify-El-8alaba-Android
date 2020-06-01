package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;

/**
 * This class stores user's rmail and password that will be needed in the LOGIN an object of this
 * class will be sent to the post function which log the user into the application
 *
 * @author Mohamed Samy
 */

public class LoginInfo {

    @Expose
    private String email;

    @Expose
    private String password;

    /**
     * Login_info constructor with all fields as parameters
     *
     * @param email    The user's email address
     * @param password The user's password
     */
    public LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * get the user's email address
     *
     * @return the user's email
     */

    public String getEmail() {
        return email;
    }

    /**
     * user's email address setter method
     *
     * @param email The user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get the user's password
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * user's password setter method
     *
     * @param password The user's password
     */

    public void setPassword(String password) {
        this.password = password;
    }
}
