package com.vnoders.spotify_el8alaba;


public class LoginResponse {

    private boolean error;
    private String msg;
    private User user;


    public LoginResponse(boolean error,String msg,User user) {
    this.error=error;
    this.msg=msg;
    this.user=user;
    }


    public boolean isError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }

    public User getUser() {
        return user;
    }

}
