package com.iitp.njack.iitp_connect.core.calendar.detail;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.iitp.njack.iitp_connect.data.contest.Contest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContestDetailViewModelTest {
    private static final long MOCK_CONTEST_ID = 222L;
    private static final Contest MOCK_CONTEST = new Contest();

    @Rule
    public InstantTaskExecutorRule ruleExecutorRule = new InstantTaskExecutorRule();

    @InjectMocks
    private ContestDetailViewModel contestDetailViewModel;

    @Mock
    private ContestDetailRepository contestDetailRepository;

    @Test
    public void shouldLoadContest_whenCurrentContestIsNull() {
        // when
        contestDetailViewModel.getContest(MOCK_CONTEST_ID);

        // then
        Mockito.verify(contestDetailRepository).getContest(Mockito.eq(MOCK_CONTEST_ID));
    }

    @Test
    public void shouldLoadContest_whenCurrentContestIsNotNull() {
        // given
        MutableLiveData<Contest> contestLiveData = new MutableLiveData<>();
        contestLiveData.setValue(MOCK_CONTEST);

        Mockito.when(contestDetailRepository.getContest(MOCK_CONTEST_ID))
            .thenReturn(contestLiveData);


        // when
        contestDetailViewModel.getContest(MOCK_CONTEST_ID);
        LiveData<Contest> currentValue = contestDetailViewModel.getContest(MOCK_CONTEST_ID);

        // then
        Assert.assertSame(contestLiveData, currentValue);
    }
}
