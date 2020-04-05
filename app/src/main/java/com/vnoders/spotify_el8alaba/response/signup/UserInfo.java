package com.vnoders.spotify_el8alaba.response.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserInfo {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("currentlyPlaying")
    @Expose
    private Object currentlyPlaying;
    @SerializedName("followers")
    @Expose
    private Object followers;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("id")
    @Expose
    private String id2;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("devices")
    @Expose
    private List<Object> devices = null;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(Object currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public Object getFollowers() {
        return followers;
    }

    public void setFollowers(Object followers) {
        this.followers = followers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Object> getDevices() {
        return devices;
    }

    public void setDevices(List<Object> devices) {
        this.devices = devices;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

}
