package com.iitp.njack.iitp_connect.common.di.modules;

import dagger.Module;

@Module(includes = {
        AndroidModule.class,
        RepoModule.class,
        ModelModule.class,
        NetworkModule.class,
        ViewModelModule.class,
        DaoModule.class
})
public class AppModule {
    // Add misc dependencies
}
