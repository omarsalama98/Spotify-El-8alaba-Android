 package com.vnoders.spotify_el8alaba.models;

 import com.google.gson.annotations.SerializedName;

 /**
     * This class holds the email address of the user who has forgotten his password
     * @author Mohamed Samy
     */
    public class forgot_password_info {

        public forgot_password_info(String email_address) {
            this.email_address = email_address;
        }

        public String getEmail_address() {
            return email_address;
        }

        public void setEmail_address(String email_address) {
            this.email_address = email_address;
        }
        @SerializedName("email")
        private String email_address;

    }



