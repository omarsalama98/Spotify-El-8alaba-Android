package com.vnoders.spotify_el8alaba.repositories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.vnoders.spotify_el8alaba.App;
import com.vnoders.spotify_el8alaba.BuildConfig;
import com.vnoders.spotify_el8alaba.R;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //    private static final String BASE_URL = "https://my-json-server.typicode.com/MohamedSamiMohamed/MOCKING/";
    private static final String BASE_URL = "http://192.168.1.10:8000/api/v1/";


    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_BEARER = "Bearer "; // The tailing space is required
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String TYPE_JSON = "application/json";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_PRAGMA = "Pragma";


    // volatile keyword ensures that all threads use the same object that is in main memory
    private static volatile RetrofitClient mRetrofitClient = null;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient.Builder httpClientBuilder;
    private HeadersInterceptor headers;


    private RetrofitClient() {
        httpClientBuilder = new OkHttpClient.Builder();

        headers = new HeadersInterceptor(httpClientBuilder);

        setToken("ACCESS_TOKEN");
        headers.addHeader(HEADER_CONTENT_TYPE, TYPE_JSON);
        headers.addHeader(HEADER_USER_AGENT, getUserAgent());

        httpClientBuilder.cache(getCache())
                .addInterceptor(useCachedResponsesInterceptor())
                .addNetworkInterceptor(cacheResponsesInterceptor());

        // Logging must be the last added interceptor
        // in order to log other interceptors
        enableLogging();

        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build());

    }

    public static RetrofitClient getInstance() {
        if (mRetrofitClient == null) {
            synchronized (RetrofitClient.class) {
                // check again to avoid multi-thread access
                if (mRetrofitClient == null) {
                    mRetrofitClient = new RetrofitClient();
                }
            }
        }
        return mRetrofitClient;
    }


    public <T> T getAPI(final Class<T> API) {
        return retrofitBuilder.build().create(API);
    }

    public void setToken(@NotNull String token) {
        headers.addHeader(HEADER_AUTHORIZATION, token);
    }

    public void removeToken() {
        headers.removeHeader(HEADER_AUTHORIZATION);
    }

    private static Cache getCache() {
        long cacheSize = 20 * 1024 * 1024; // 20 MegaBytes
        return new Cache(new File(App.getInstance().getCacheDir(), "Requests Cache"), cacheSize);
    }

    private String getUserAgent() {

        return String.format(Locale.US,
                "%s/%s (Android %s; %s %s %s; %s/%s)",
                App.getInstance().getString(R.string.app_name),
                BuildConfig.VERSION_NAME,
                Build.VERSION.RELEASE,
                Build.MANUFACTURER,
                Build.MODEL,
                Build.PRODUCT,
                Build.DEVICE,
                Build.ID);
    }


    private void enableLogging() {
        if (BuildConfig.DEBUG) {
            // Add Logging for debugging purposes
            // Should be the LAST added Interceptor in order to log all other interceptors
            httpClientBuilder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(Level.BODY));
        }
    }


    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnected();
    }


    /**
     * This interceptor will be called both if the network is available and if the network is not
     * available
     */
    private static Interceptor useCachedResponsesInterceptor() {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();

                // Caching is allowed only in GET requests
                if (request.method().equals("GET")) {

                    // prevent caching when network is on. For that we use the cacheResponsesInterceptor
                    if (!isOnline()) {
                        CacheControl cacheControl = new CacheControl.Builder()
                                // After this time cache is dismissed and no longer used
                                .maxStale(7, TimeUnit.DAYS)
                                .build();

                        request = request.newBuilder()
                                // Remove cache control provided by the server to provide ours
                                .removeHeader(HEADER_PRAGMA)
                                .removeHeader(HEADER_CACHE_CONTROL)
                                .cacheControl(cacheControl)
                                .build();
                    }
                }

                return chain.proceed(request);
            }
        };
    }

    /**
     * This interceptor will be called ONLY if the network is available
     */
    private static Interceptor cacheResponsesInterceptor() {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Response response = chain.proceed(chain.request());

                // Caching is allowed only in GET requests
                if (chain.request().method().equals("GET")) {

                    CacheControl cacheControl = new CacheControl.Builder()
                            // if the response of this request was received in less than 5 minutes
                            // do not make another request and used the cached one instead
                            .maxAge(5, TimeUnit.MINUTES)
                            .build();

                    response = response.newBuilder()
                            // Remove cache control provided by the server to provide ours
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                            .build();
                }
                return response;
            }
        };
    }


    private static class HeadersInterceptor implements Interceptor {

        Map<String, String> headers = new Hashtable<>();

        HeadersInterceptor(@NotNull OkHttpClient.Builder httpClientBuilder) {
            httpClientBuilder.addInterceptor(this);
        }

        void addHeader(String header, String value) {

            if (header.equals(HEADER_AUTHORIZATION)) {
                headers.put(header, TOKEN_BEARER + value);
            } else {
                headers.put(header, value);
            }
        }

        void removeHeader(String header) {
            headers.remove(header);
        }

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder();

            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }

            return chain.proceed(requestBuilder.build());
        }
    }

}
