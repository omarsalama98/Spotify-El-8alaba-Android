package com.vnoders.spotify_el8alaba.repositories;
import android.content.Context;
import com.vnoders.spotify_el8alaba.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Context context;
//    private static final String base_url= "https://my-json-server.typicode.com/MohamedSamiMohamed/MOCKING/";
    private static final String base_url= "http://192.168.1.10:8000/api/v1/";

    private static RetrofitClient mRetrofitClient;
    private Retrofit retrofit;

private RetrofitClient(){
    retrofit=new Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

public static synchronized RetrofitClient getInstance(){
    if(mRetrofitClient==null){
        mRetrofitClient=new RetrofitClient();
    }
    return mRetrofitClient;
}

public API getAPI(){
    return retrofit.create(API.class);
}

}
