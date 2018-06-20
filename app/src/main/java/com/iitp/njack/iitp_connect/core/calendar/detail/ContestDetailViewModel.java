package com.iitp.njack.iitp_connect.core.calendar.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.iitp.njack.iitp_connect.data.contest.Contest;

import javax.inject.Inject;

public class ContestDetailViewModel extends ViewModel {
    private final ContestDetailRepository contestDetailRepository;

    private LiveData<Contest> contest;

    @Inject
    public ContestDetailViewModel(ContestDetailRepository contestDetailRepository) {
        this.contestDetailRepository = contestDetailRepository;
    }

    protected LiveData<Contest> getContest(Long id) {
        if (contest == null) {
            contest = contestDetailRepository.getContest(id);
        }
        return contest;
    }
}
