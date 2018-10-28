package com.iitp.njack.iitp_connect.common.di.modules;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.core.profile.FirebaseDatabaseLiveData;
import com.iitp.njack.iitp_connect.data.timetabledata.TimeTableInformation;
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
    User providesDatabaseUser() {
        return new User();
    }

     @Provides
    RateLimiter<String> providesRateByTagLimiter() {
        return new RateLimiter<>(10, TimeUnit.MINUTES);
    }

    @Provides
    @Singleton
    FirebaseOptions providesFirebaseOptions() {
        return new FirebaseOptions.Builder()
            .setApplicationId("1:915375757584:android:71d541a7bb607bdb") // Required for Analytics.
            .setApiKey("AIzaSyDTWaBiVYkYy56Okb-CpRb1sHtY2g3wZxo") // Required for Auth.
            .setDatabaseUrl("https://timetable-generator-40b48.firebaseio.com") // Required for RTDB.
            .build();
    }

    @Provides
    @Singleton
    FirebaseApp providesFirebaseApp(FirebaseOptions firebaseOptions, Context context) {
        FirebaseApp.initializeApp(context, firebaseOptions, "timeTableGenerator");
        return FirebaseApp.getInstance("timeTableGenerator");
    }

    @Provides
    @Singleton
    TimeTableInformation providesTimeTableInformation() {
        return new TimeTableInformation("Btech", "CS", "First");
    }
}
