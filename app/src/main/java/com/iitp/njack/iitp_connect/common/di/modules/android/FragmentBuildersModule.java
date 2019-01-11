package com.iitp.njack.iitp_connect.common.di.modules.android;

import com.iitp.njack.iitp_connect.core.facebook.feed.FacebookFeedFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract FacebookFeedFragment contributeFacebookFeedFragment();
}
