package com.iitp.njack.iitp_connect.common.di.modules;

import android.arch.lifecycle.ViewModelProvider;

import com.iitp.njack.iitp_connect.common.di.IITPConnectViewModelFactory;
import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarRepository;
import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarViewModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelModule {

    /**
     * Example entry:
     * TODO: Remove when a real entry is added
     *
     * @Binds
     * @IntoMap
     * @ViewModelKey(MainViewModel.class)
     * public abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);
     */

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(IITPConnectViewModelFactory factory);

    @Binds
    public abstract CodingCalendarViewModel bindCodingCalendarViewModel(CodingCalendarRepository codingCalendarRepository);
}
