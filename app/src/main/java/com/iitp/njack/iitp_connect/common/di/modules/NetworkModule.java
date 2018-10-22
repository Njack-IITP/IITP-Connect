package com.iitp.njack.iitp_connect.common.di.modules;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory;
import com.iitp.njack.iitp_connect.IITPConnectProvider;
import com.iitp.njack.iitp_connect.common.Constants;
import com.iitp.njack.iitp_connect.common.livedata.LiveDataCallAdapterFactory;
import com.iitp.njack.iitp_connect.data.auth.AuthHolder;
import com.iitp.njack.iitp_connect.data.contest.ContestListWrapper;
import com.iitp.njack.iitp_connect.data.network.HostSelectionInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

@Module(includes = ApiModule.class)
@SuppressWarnings("PMD.CouplingBetweenObjects")
public class NetworkModule {

    @Provides
    @Singleton
    ObjectMapper providesObjectMapper() {
        return new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Provides
    Class[] providesMappedClasses() {
        return new Class[]{ContestListWrapper.class};
    }

    @Provides
    @Singleton
    @Named("jsonapi")
    Converter.Factory providesJsonApiFactory(ObjectMapper objectMapper, Class... mappedClasses) {
        return new JSONAPIConverterFactory(objectMapper, mappedClasses);
    }

    @Provides
    @Singleton
    @Named("jackson")
    Converter.Factory providesJacksonFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }

    @Provides
    @Singleton
    @Named("RxCallAdapterFactory")
    CallAdapter.Factory providesCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    @Named("LiveDataCallAdapterFactory")
    CallAdapter.Factory providesLiveDataCallAdapterFactory() {
        return new LiveDataCallAdapterFactory();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    @Provides
    @Singleton
    StethoInterceptor stethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    @Named("authenticator")
    Interceptor authenticator(AuthHolder authHolder) {
        return chain -> {
            Request original = chain.request();

            String authorization = authHolder.getAuthorization();
            if (authorization == null) {
                Timber.d("Someone tried to access resources without auth token. Maybe auth request?");
                return chain.proceed(original);
            }

            Request request = original.newBuilder()
                .header("Authorization", authorization)
                .method(original.method(), original.body())
                .build();

            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    Cache providesCache() {
        int cacheSize = 10 * 1024 * 1024; // 10 MB

        return new Cache(IITPConnectProvider.context.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(
        HostSelectionInterceptor hostSelectionInterceptor,
        HttpLoggingInterceptor loggingInterceptor,
        StethoInterceptor stethoInterceptor,
        @Named("authenticator") Interceptor authenticator,
        Cache cache
    ) {
        return new OkHttpClient.Builder()
            .addInterceptor(hostSelectionInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authenticator)
            .addNetworkInterceptor(stethoInterceptor)
            .cache(cache)
            .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofitBuilder(@Named("RxCallAdapterFactory") CallAdapter.Factory callAdapterFactory,
                                     @Named("LiveDataCallAdapterFactory") CallAdapter.Factory liveDataCallAdapterFactory,
                                     @Named("jackson") Converter.Factory factory, OkHttpClient client) {
        return new Retrofit.Builder()
            .addCallAdapterFactory(callAdapterFactory)
            .addCallAdapterFactory(liveDataCallAdapterFactory)
            .addConverterFactory(factory)
            .client(client)
            .baseUrl(Constants.HACKERRANK_API)
            .build();
    }
}
