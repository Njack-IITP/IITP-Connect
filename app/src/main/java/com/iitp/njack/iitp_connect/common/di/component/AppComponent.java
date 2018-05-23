package com.iitp.njack.iitp_connect.common.di.component;

import com.iitp.njack.iitp_connect.IITPConnectApplication;
import com.iitp.njack.iitp_connect.common.di.modules.ActivityBuildersModule;
import com.iitp.njack.iitp_connect.common.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class
})
public interface AppComponent extends AndroidInjector<IITPConnectApplication> {
    void inject(IITPConnectApplication iitpConnectApplication);
}
