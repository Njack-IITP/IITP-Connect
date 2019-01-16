package com.iitp.njack.iitp_connect.common.di.component;

import android.app.Application;

import com.iitp.njack.iitp_connect.IITPConnectApplication;
import com.iitp.njack.iitp_connect.common.di.modules.AppModule;
import com.iitp.njack.iitp_connect.common.di.modules.android.ActivityBuildersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class
})
public interface AppComponent extends AndroidInjector<IITPConnectApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(IITPConnectApplication iitpConnectApplication);
}
