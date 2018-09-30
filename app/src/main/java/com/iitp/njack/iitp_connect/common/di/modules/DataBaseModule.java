package com.iitp.njack.iitp_connect.common.di.modules;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {
    @Provides
    @Singleton
    FirebaseDatabase providesFirebaseDatabase() { return FirebaseDatabase.getInstance(); }

}
