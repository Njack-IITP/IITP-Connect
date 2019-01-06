package com.iitp.njack.iitp_connect.common.di.modules.android;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract FacebookFeedFragment contributeFacebookFeedFragment();
}
