package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.iitp.njack.iitp_connect.data.contest.Contest;

import java.util.List;

import javax.inject.Inject;

public class CodingCalendarViewModel extends ViewModel {
    private final CodingCalendarRepository codingCalendarRepository;
    private LiveData<List<Contest>> contests;
    private MutableLiveData<Boolean> progress = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public CodingCalendarViewModel(CodingCalendarRepository codingCalendarRepository) {
        this.codingCalendarRepository = codingCalendarRepository;
        progress.setValue(false);
    }

    protected LiveData<List<Contest>> loadContests(boolean reload) {
        if (contests != null && !reload) {
            return contests;
        } else if (contests == null) {
            reload = true;
        }
        contests = codingCalendarRepository.fetchContests(progress, reload);
        return contests;
    }

    protected LiveData<Boolean> getProgress() {
        return progress;
    }

    protected LiveData<String> getError() {
        return error;
    }
}
