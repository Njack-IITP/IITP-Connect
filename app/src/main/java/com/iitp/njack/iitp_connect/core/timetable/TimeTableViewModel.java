package com.iitp.njack.iitp_connect.core.timetable;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;

import javax.inject.Inject;


public class TimeTableViewModel extends ViewModel {
    private TimeTableRepository timeTableRepository;
    private LiveData<DataSnapshot> timeTableDatabaseLiveData;
    private LiveData<DataSnapshot> user;

    @Inject
    public TimeTableViewModel(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }

    LiveData<DataSnapshot> getData() {
        if (timeTableDatabaseLiveData == null) {
            timeTableDatabaseLiveData = timeTableRepository.getData();
        }
        return timeTableDatabaseLiveData;
    }

    protected LiveData<DataSnapshot> getUser() {
        if (user == null) {
            user = timeTableRepository.getUser();
        }
        return user;
    }
}
