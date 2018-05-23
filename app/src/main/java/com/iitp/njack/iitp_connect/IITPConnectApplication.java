package com.iitp.njack.iitp_connect;

import android.app.Application;

import timber.log.Timber;

public class IITPConnectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupTimber();
    }

    private void setupTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}
