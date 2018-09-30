package com.iitp.njack.iitp_connect.common.di.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {
    @Provides
    @Singleton
    FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    FirebaseUser providesFirebaseUser(FirebaseAuth firebaseAuth) { return firebaseAuth.getCurrentUser(); }
}
