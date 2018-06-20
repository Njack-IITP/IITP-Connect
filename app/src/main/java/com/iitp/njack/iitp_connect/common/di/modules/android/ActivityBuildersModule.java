package com.iitp.njack.iitp_connect.common.di.modules.android;

import com.iitp.njack.iitp_connect.HomeActivity;
import com.iitp.njack.iitp_connect.core.calendar.detail.ContestDetailActivity;
import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract CodingCalendarActivity contributeCodingCalendarActivity();

    @ContributesAndroidInjector
    abstract ContestDetailActivity contributeContestDetailActivity();

    @ContributesAndroidInjector
    abstract HomeActivity contributeHomeActivity();
}
