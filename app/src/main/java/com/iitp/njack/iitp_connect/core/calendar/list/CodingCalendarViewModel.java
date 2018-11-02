package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.iitp.njack.iitp_connect.common.livedata.SingleEventLiveData;
import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.network.Resource;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.List;

import javax.inject.Inject;

public class CodingCalendarViewModel extends ViewModel {
    @VisibleForTesting
    static final String CONTEST_VIEW_MODEL = "contestViewModel";

    private final CodingCalendarRepository codingCalendarRepository;
    private final RateLimiter<String> repoListRateLimit;
    private LiveData<Resource<List<Contest>>> contests;
    private SingleEventLiveData<Long> clickAction = new SingleEventLiveData<>();


    @Inject
    public CodingCalendarViewModel(CodingCalendarRepository codingCalendarRepository,
                                   RateLimiter<String> repoListRateLimit) {
        this.codingCalendarRepository = codingCalendarRepository;
        this.repoListRateLimit = repoListRateLimit;
        repoListRateLimit.reset(CONTEST_VIEW_MODEL);
    }

    protected LiveData<Resource<List<Contest>>> loadContests(boolean reload) {
        if (reload || repoListRateLimit.shouldFetch(CONTEST_VIEW_MODEL)) {
            contests = codingCalendarRepository.fetchContests(reload);
            repoListRateLimit.refreshRateLimiter(CONTEST_VIEW_MODEL);
        }
        return contests;
    }

    public void openContestDetails(Long id) {
        clickAction.setValue(id);
    }

    protected LiveData<Long> getSelectedContest() {
        return clickAction;
    }
}
