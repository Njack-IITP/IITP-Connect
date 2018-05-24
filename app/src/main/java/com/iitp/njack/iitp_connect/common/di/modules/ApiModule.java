package com.iitp.njack.iitp_connect.common.di.modules;

import com.iitp.njack.iitp_connect.data.contest.ContestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    ContestApi providesContestApi(Retrofit retrofit) {
        return retrofit.create(ContestApi.class);
    }
}
