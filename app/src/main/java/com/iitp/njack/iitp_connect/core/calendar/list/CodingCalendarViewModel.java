package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.iitp.njack.iitp_connect.data.contest.Contest;

import java.util.List;

import javax.inject.Inject;

public class CodingCalendarViewModel extends ViewModel {
    private final CodingCalendarRepository codingCalendarRepository;
    private LiveData<List<Contest>> contests;

    @Inject
    public CodingCalendarViewModel(CodingCalendarRepository codingCalendarRepository) {
        this.codingCalendarRepository = codingCalendarRepository;
        init();
    }

    private void init() {
        if (contests != null) {
            return;
        }
        contests = codingCalendarRepository.fetchContests();
    }

    protected LiveData<List<Contest>> getContests() {
        return contests;
    }
}
