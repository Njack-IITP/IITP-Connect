package com.iitp.njack.iitp_connect.core.Injection.Component;

import android.app.Application;
import android.content.Context;

import com.iitp.njack.iitp_connect.core.Injection.Module.ApplicationModule;
import com.iitp.njack.iitp_connect.core.androidapp.IITPConnectApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void Inject(IITPConnectApplication application);

    Application application();
    Context context();
}