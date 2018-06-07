package com.iitp.njack.iitp_connect.common.di.modules;

import com.iitp.njack.iitp_connect.data.contest.ContestDao;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DaoModule {
    @Binds
    @Singleton
    public abstract ContestDao bindsContestDao(ContestDao contestDao);
}
