package com.iitp.njack.iitp_connect.common.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.iitp.njack.iitp_connect.common.di.IITPConnectViewModelFactory;
import com.iitp.njack.iitp_connect.core.calendar.detail.ContestDetailViewModel;
import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarViewModel;
import com.iitp.njack.iitp_connect.core.home.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CodingCalendarViewModel.class)
    public abstract ViewModel bindCodingCalendarViewModel(CodingCalendarViewModel codingCalendarViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContestDetailViewModel.class)
    public abstract ViewModel bindContestDetailViewModel(ContestDetailViewModel contestDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(IITPConnectViewModelFactory factory);
}
