package com.iitp.njack.iitp_connect.core.timetable;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.iitp.njack.iitp_connect.data.user.User;

import javax.inject.Inject;


public class TimeTableViewModel extends ViewModel {
    private TimeTableRepository timeTableRepository;
    private LiveData<DataSnapshot> user;

    @Inject
    public TimeTableViewModel(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }
    LiveData<DataSnapshot> getData() {
        return timeTableRepository.getData();
    }

    protected LiveData<DataSnapshot> getUser() {
        if (user == null) {
            user = timeTableRepository.getUser();
        }
        return user;
    }
}
