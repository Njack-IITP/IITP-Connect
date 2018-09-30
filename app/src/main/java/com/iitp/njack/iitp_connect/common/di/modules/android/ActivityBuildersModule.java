package com.iitp.njack.iitp_connect.common.di.modules.android;

import com.firebase.ui.auth.KickoffActivity;
import com.firebase.ui.auth.ui.credentials.CredentialSaveActivity;
import com.firebase.ui.auth.ui.email.EmailActivity;
import com.firebase.ui.auth.ui.email.WelcomeBackPasswordPrompt;
import com.iitp.njack.iitp_connect.core.home.HomeActivity;
import com.iitp.njack.iitp_connect.core.calendar.detail.ContestDetailActivity;
import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarActivity;
import com.iitp.njack.iitp_connect.core.profile.ProfileActivity;

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

    @ContributesAndroidInjector
    abstract KickoffActivity contributeKickoffActivity();

    @ContributesAndroidInjector
    abstract EmailActivity contributeEmailActivity();

    @ContributesAndroidInjector
    abstract CredentialSaveActivity contributeCredentialSaveActivity();

    @ContributesAndroidInjector
    abstract WelcomeBackPasswordPrompt contributeWelcomeBackPasswordPrompt();

    @ContributesAndroidInjector
    abstract ProfileActivity contributeProfileActivity();
}
