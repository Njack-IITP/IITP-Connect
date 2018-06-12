package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestApi;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.network.ConnectionStatusImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CodingCalendarRepository {
    private final ContestDao contestDao;
    private final ContestApi contestApi;
    private final ConnectionStatusImpl connectionStatus;
    private Disposable contestDisposable;

    @Inject
    public CodingCalendarRepository(ContestDao contestDao, ContestApi contestApi,
                                    ConnectionStatusImpl connectionStatus) {
        this.contestDao = contestDao;
        this.contestApi = contestApi;
        this.connectionStatus = connectionStatus;
    }

    protected LiveData<List<Contest>> fetchContests(MutableLiveData<Boolean> progress, boolean reload) {
        if (connectionStatus.isConnected() && reload) {
            if (contestDisposable != null) {
                contestDisposable.dispose();
            }
            contestDisposable = contestApi.getContests()
                    .doOnNext(contestDao::addContest)
                    .doOnSubscribe(contest -> progress.postValue(true))
                    .doFinally(() -> {
                        contestDisposable.dispose();
                        progress.setValue(false);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
        return contestDao.getContests();
    }
}
