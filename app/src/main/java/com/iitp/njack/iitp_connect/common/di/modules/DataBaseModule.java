package com.iitp.njack.iitp_connect.common.di.modules;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.core.profile.FirebaseDatabaseLiveData;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {
    @Provides
    @Singleton
    FirebaseDatabase providesFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    User providesDatabaseUser(FirebaseUser firebaseUser) {
        return new User();
    }


    @Provides
    RateLimiter<String> providesRateByTagLimiter() {
        return new RateLimiter<>(10, TimeUnit.MINUTES);
    }
}
