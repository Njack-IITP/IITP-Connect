package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.LiveData;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestApi;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.network.ConnectionStatusImpl;

import java.util.List;

import javax.inject.Inject;

public class CodingCalendarRepository {
    private final ContestDao contestDao;
    private final ContestApi contestApi;
    private final ConnectionStatusImpl connectionStatus;

    @Inject
    public CodingCalendarRepository(ContestDao contestDao, ContestApi contestApi, ConnectionStatusImpl connectionStatus) {
        this.contestDao = contestDao;
        this.contestApi = contestApi;
        this.connectionStatus = connectionStatus;
    }

    protected LiveData<List<Contest>> fetchContests() {
        if (connectionStatus.isConnected()) {
            contestApi.getContests()
                    .doOnNext(contestDao::addContest)
                    .subscribe();
        }
        return contestDao.getContests();
    }
}
