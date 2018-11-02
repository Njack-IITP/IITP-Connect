package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.iitp.njack.iitp_connect.AppExecutors;
import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestApi;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.contest.ContestListWrapper;
import com.iitp.njack.iitp_connect.data.network.ApiResponse;
import com.iitp.njack.iitp_connect.data.network.NetworkBoundResource;
import com.iitp.njack.iitp_connect.data.network.Resource;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.List;

import javax.inject.Inject;

public class CodingCalendarRepository {
    private static final String CONTESTS = "contests";

    private final ContestDao contestDao;
    private final ContestApi contestApi;
    private final AppExecutors appExecutors;
    private final RateLimiter<String> repoListRateLimit;

    @Inject
    public CodingCalendarRepository(ContestDao contestDao,
                                    ContestApi contestApi,
                                    AppExecutors appExecutors,
                                    RateLimiter<String> repoListRateLimit) {
        this.contestDao = contestDao;
        this.contestApi = contestApi;
        this.appExecutors = appExecutors;
        this.repoListRateLimit = repoListRateLimit;
    }

    public LiveData<Resource<List<Contest>>> fetchContests(boolean reload) {
        return new NetworkBoundResource<List<Contest>, ContestListWrapper>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull ContestListWrapper item) {
                contestDao.deleteAll();
                List<Contest> contests = item.getContests();
                contestDao.addContests(contests);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Contest> data) {
                return data == null || data.isEmpty() || reload || repoListRateLimit.shouldFetchAndRefresh(CONTESTS);
            }

            @NonNull
            @Override
            protected LiveData<List<Contest>> loadFromDb() {
                return contestDao.getContests();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ContestListWrapper>> createCall() {
                return contestApi.getContests();
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(CONTESTS);
            }
        }.asLiveData();
    }
}
