package com.iitp.njack.iitp_connect.core.androidapp;

import android.app.Application;
import android.os.Bundle;

import timber.log.Timber;

public class AndroidApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

    }
}
