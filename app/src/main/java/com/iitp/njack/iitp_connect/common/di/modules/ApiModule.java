package com.iitp.njack.iitp_connect.common.di.modules;

import com.iitp.njack.iitp_connect.common.Constants;
import com.iitp.njack.iitp_connect.data.contest.ContestApi;
import com.iitp.njack.iitp_connect.data.facebook.FacebookApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    public ContestApi providesContestApi(Retrofit retrofit) {
        return retrofit.create(ContestApi.class);
    }

    @Provides
    @Singleton
    public FacebookApi providesFacebookApi(@Named("RxCallAdapterFactory") CallAdapter.Factory callAdapterFactory,
                                           @Named("LiveDataCallAdapterFactory") CallAdapter.Factory liveDataCallAdapterFactory,
                                           @Named("jackson") Converter.Factory factory, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(callAdapterFactory)
            .addCallAdapterFactory(liveDataCallAdapterFactory)
            .addConverterFactory(factory)
            .client(client)
            .baseUrl(Constants.TEST_PAGE)
            .build();
        return retrofit.create(FacebookApi.class);
    }
}
