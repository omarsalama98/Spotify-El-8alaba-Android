package com.vnoders.spotify_el8alaba.models;

/**
 * This class stores all user's information that will be needed in the registration an object of
 * this class will be sent to the post function which creates account to the user
 *
 * @author Mohamed Samy
 */

public class SignUpInfo {


    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
    private String gender;
    private String birthdate;
    private String type;

    /**
     * signup_info constructor with all fields as parameters
     *
     * @param name            the user's name
     * @param email           the user's email address
     * @param password        the user's password
     * @param passwordConfirm the user's confirmation password
     * @param gender          the user's gender
     * @param birthdate       the user's birth date in unix stamp format
     * @param type            the user's type which specifies whether he is artist or just ordinary
     *                        user
     */
    public SignUpInfo(String name, String email, String password, String passwordConfirm,
            String gender, String birthdate, String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.gender = gender;
        this.birthdate = birthdate;
        this.type = type;
    }

    /**
     * user's name getter method
     *
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * user's name setter method
     *
     * @param name the user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * user's email address getter method
     *
     * @return the user's email address
     */

    public String getEmail() {
        return email;
    }

    /**
     * user's email address setter method
     *
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * user's password getter method
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * user's password setter method
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * user's confirmed password getter method
     *
     * @return the user's confirmation password
     */

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * user's confirmed password setter method
     *
     * @param passwordConfirm the user's confirmation password
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /**
     * user's gender getter method
     *
     * @return the user's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * user's gender setter method
     *
     * @param gender the user's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * user's birth date getter method
     *
     * @return the user's birth date in unix stamp format
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * user's birth date setter method
     *
     * @param birthdate the user's birth date in unix stamp format
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * user's type getter method
     *
     * @return the user's type which specifies whether he is artist or just ordinary user
     */
    public String getType() {
        return type;
    }

    /**
     * user's type setter method
     *
     * @param type the user's type which specifies whether he is artist or just ordinary user
     */
    public void setType(String type) {
        this.type = type;
    }
}
