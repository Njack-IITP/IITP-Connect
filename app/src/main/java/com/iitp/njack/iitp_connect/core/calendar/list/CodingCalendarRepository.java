package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestApi;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.network.ConnectionStatusImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
                    .doOnSubscribe(contest -> progress.postValue(true))
                    .doOnNext(contestListWrapper -> {
                        List<Contest> contests = contestListWrapper.getContests();
                        Timber.v("%s contests loaded", contests.size());
                        contestDao.deleteAll();
                        contestDao.addContests(contests);
                    })
                    .doOnError(Timber::e)
                    .doOnComplete(() -> Timber.v("Fetch contest complete"))
                    .doFinally(() -> {
                        contestDisposable.dispose();
                        progress.postValue(false);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
        return contestDao.getContests();
    }
}
