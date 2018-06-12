package com.iitp.njack.iitp_connect.common.di.modules.android;

import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarActivity;
import com.iitp.njack.iitp_connect.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract CodingCalendarActivity contributeCodingCalendarActivity();

    @ContributesAndroidInjector
    abstract HomeActivity contributeHomeActivity();
}
