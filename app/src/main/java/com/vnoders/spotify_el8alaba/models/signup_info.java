package com.vnoders.spotify_el8alaba.models;

public class signup_info {

    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
    private String gender;
    private String birthdate;
    private String type;

    public signup_info(String name, String email, String password, String passwordConfirm,
            String gender, String birthdate, String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.gender = gender;
        this.birthdate = birthdate;
        this.type = type;
    }
}
