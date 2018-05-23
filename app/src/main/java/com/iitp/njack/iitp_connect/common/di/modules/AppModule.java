package com.iitp.njack.iitp_connect.common.di.modules;

import android.content.Context;

import com.iitp.njack.iitp_connect.IITPConnectApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    Context provideContext(IITPConnectApplication application) {
        return application.getApplicationContext();
    }
}
