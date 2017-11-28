package com.iitp.njack.iitp_connect.Retrofit;

import com.iitp.njack.iitp_connect.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service Generator class
 */

public class ServiceGenerator {

    private static String apiUrl;

    public ServiceGenerator(String url) {
        apiUrl = url;
    }

    //Define the retrofit builder object
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create());

    //Build the Retrofit object
    private static Retrofit retrofit = builder.build();

    //attach logging interceptor
    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    //Define okhttpClient builder object
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    //Create service
    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            if (BuildConfig.DEBUG)
                httpClient.addInterceptor(logging);

            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
