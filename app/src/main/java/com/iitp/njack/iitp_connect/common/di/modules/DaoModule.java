package com.iitp.njack.iitp_connect.common.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.database.IITPConnectDatabase;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPostDao;
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

    @Provides
    @Singleton
    DatabaseReference providesFirebaseDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Singleton @Provides
    public UserDao providesUserDao(IITPConnectDatabase iitpConnectDatabase){
        return iitpConnectDatabase.userDao();
    }

    @Singleton @Provides
    public ContestDao providesContestDao(IITPConnectDatabase iitpConnectDatabase){
        return iitpConnectDatabase.contestDao();
    }

    @Singleton @Provides
    public FacebookPostDao providesFacebookPostDao(IITPConnectDatabase iitpConnectDatabase) {
        return iitpConnectDatabase.facebookPostDao();
    }
}
