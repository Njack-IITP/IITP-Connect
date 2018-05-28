package com.iitp.njack.iitp_connect.core.Injection.Module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application application(){
        return application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return this.application;
    }
}