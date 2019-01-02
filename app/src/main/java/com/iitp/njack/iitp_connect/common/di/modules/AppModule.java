package com.iitp.njack.iitp_connect.common.di.modules;

import dagger.Module;

@Module(includes = {
        AndroidModule.class,
        ModelModule.class,
        NetworkModule.class,
        ViewModelModule.class,
        DaoModule.class,
        AuthModule.class,
        DataBaseModule.class,
        YoutubeModule.class
})
public class AppModule {
    // Add misc dependencies
}
