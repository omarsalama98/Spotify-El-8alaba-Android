package com.vnoders.spotify_el8alaba.repositories;

import android.content.Context;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class creates a retrofit object and build it with needed info like
 * {base_url,ConverterFactory}
 *
 * @author Mohamed Samy
 */
public class RetrofitClient {

    private static Context context;
    private static final String base_url = "http://192.168.1.10:8000/api/v1/";
    private static RetrofitClient mRetrofitClient;
    private Retrofit retrofit;

    /**
     * this is the default constructor of the class which build the RetrofitClient object with the
     * given base_url and ConverterFactory
     */
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * if there is already a retrofit client , it will be returned ,otherwise will create a retrofit
     * client and build it then it will be returned
     *
     * @return object of RetrofitClient
     */
    public static synchronized RetrofitClient getInstance() {
        if (mRetrofitClient == null) {
            mRetrofitClient = new RetrofitClient();
        }
        return mRetrofitClient;
    }

    /**
     * This method return the API interface to access its methods by using retrofit client object
     *
     * @return API interface
     */
    public API getAPI() {
        return retrofit.create(API.class);
    }

}
