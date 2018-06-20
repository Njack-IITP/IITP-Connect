package com.iitp.njack.iitp_connect.core.calendar.detail;

import android.arch.lifecycle.LiveData;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;

import javax.inject.Inject;

public class ContestDetailRepository {
    private final ContestDao contestDao;

    @Inject
    public ContestDetailRepository(ContestDao contestDao) {
        this.contestDao = contestDao;
    }

    protected LiveData<Contest> getContest(Long id) {
        return contestDao.getContestById(id);
    }
}
