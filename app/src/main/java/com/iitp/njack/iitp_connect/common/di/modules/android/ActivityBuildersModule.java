package com.iitp.njack.iitp_connect.common.di.modules.android;

import com.firebase.ui.auth.KickoffActivity;
import com.firebase.ui.auth.ui.credentials.CredentialSaveActivity;
import com.firebase.ui.auth.ui.email.EmailActivity;
import com.firebase.ui.auth.ui.email.WelcomeBackPasswordPrompt;
import com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity;
import com.firebase.ui.auth.ui.idp.SingleSignInActivity;
import com.firebase.ui.auth.ui.idp.WelcomeBackIdpPrompt;
import com.iitp.njack.iitp_connect.core.home.HomeActivity;
import com.iitp.njack.iitp_connect.core.calendar.detail.ContestDetailActivity;
import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarActivity;
import com.iitp.njack.iitp_connect.core.profile.ProfileActivity;
import com.iitp.njack.iitp_connect.core.timetable.TimeTableActivity;
import com.iitp.njack.iitp_connect.core.youtube.playlist.YoutubeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract CodingCalendarActivity contributeCodingCalendarActivity();

    @ContributesAndroidInjector
    abstract ContestDetailActivity contributeContestDetailActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
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
    abstract WelcomeBackIdpPrompt contributeWelcomeBackIdpPrompt();

    @ContributesAndroidInjector
    abstract ProfileActivity contributeProfileActivity();

    @ContributesAndroidInjector
    abstract SingleSignInActivity contributeSingleSignInActivity();

    @ContributesAndroidInjector
    abstract AuthMethodPickerActivity contributeAuthMethodPickerActivity();

    @ContributesAndroidInjector
    abstract TimeTableActivity contributeTimeTableActivity();

    @ContributesAndroidInjector
    abstract YoutubeActivity contributeYoutubeActivity();
}
