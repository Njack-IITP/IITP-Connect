package com.iitp.njack.iitp_connect.common.di.modules;

import com.iitp.njack.iitp_connect.data.AndroidUtils;
import com.iitp.njack.iitp_connect.data.ContextUtils;
import com.iitp.njack.iitp_connect.data.SharedPreferencesImpl;
import com.iitp.njack.iitp_connect.data.network.ConnectionStatus;
import com.iitp.njack.iitp_connect.data.network.ConnectionStatusImpl;

import java.util.prefs.Preferences;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ModelModule {
    @Binds
    @Singleton
    abstract ContextUtils bindsUtilModel(AndroidUtils utilModel);

    @Binds
    @Singleton
    abstract ConnectionStatus bindsConnectionObserver(ConnectionStatusImpl connectionStatus);

    @Binds
    @Singleton
    abstract Preferences bindsSharedPreferenceModel(SharedPreferencesImpl sharedPreferenceModel);
}
