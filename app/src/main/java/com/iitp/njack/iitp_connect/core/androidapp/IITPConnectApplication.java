package com.iitp.njack.iitp_connect.core.androidapp;

import android.app.Application;

import com.iitp.njack.iitp_connect.core.Injection.Component.ApplicationComponent;
import com.iitp.njack.iitp_connect.core.Injection.Component.DaggerApplicationComponent;
import com.iitp.njack.iitp_connect.core.Injection.Module.ApplicationModule;

import timber.log.Timber;

public class IITPConnectApplication extends Application {

    private ApplicationComponent applicationComponent;

    private static IITPConnectApplication singleton;

    public static IITPConnectApplication getInstance(){ return singleton;}

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.Inject(this);
        singleton = this;
    }
}
