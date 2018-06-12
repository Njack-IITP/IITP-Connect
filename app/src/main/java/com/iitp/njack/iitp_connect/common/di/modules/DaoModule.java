package com.iitp.njack.iitp_connect.common.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.database.IITPConnectDatabase;
import com.iitp.njack.iitp_connect.data.user.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {
    @Provides
    @Singleton
    IITPConnectDatabase providesIITPConnectDatabase(Context context) {
        return Room.databaseBuilder(context, IITPConnectDatabase.class, "iitpConnectDatabase")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton @Provides
    public UserDao providesUserDao(IITPConnectDatabase iitpConnectDatabase){
        return iitpConnectDatabase.userDao();
    }

    @Singleton @Provides
    public ContestDao providesContestDao(IITPConnectDatabase iitpConnectDatabase){
        return iitpConnectDatabase.contestDao();
    }
}
