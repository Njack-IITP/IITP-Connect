package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.iitp.njack.iitp_connect.utils.RateLimiter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CodingCalendarViewModelTest {
    @Rule
    public InstantTaskExecutorRule ruleExecutorRule = new InstantTaskExecutorRule();

    private static final long MOCK_CONTEST_ID = 222L;

    @Mock
    private CodingCalendarRepository codingCalendarRepository;

    @Mock
    private RateLimiter<String> repoListRateLimit;

    @Mock
    private Observer<Long>  observerLong;

    @InjectMocks
    private CodingCalendarViewModel codingCalendarViewModel;


    @Test
    public void shouldLoadContests_when_reloadFalse_and_rateLimitFalse() {
        // given
        Mockito.when(repoListRateLimit.shouldFetch(CodingCalendarViewModel.CONTEST_VIEW_MODEL))
            .thenReturn(false);

        // when
        codingCalendarViewModel.loadContests(false);

        // then
        Mockito.verify(codingCalendarRepository, Mockito.never()).fetchContests(Mockito.anyBoolean());
        Mockito.verify(repoListRateLimit, Mockito.never()).refreshRateLimiter(CodingCalendarViewModel.CONTEST_VIEW_MODEL);
    }

    @Test
    public void shouldLoadContests_when_reloadTrue_and_rateLimitFalse() {
        // when
        codingCalendarViewModel.loadContests(true);

        // then
        Mockito.verify(codingCalendarRepository).fetchContests(true);
        Mockito.verify(repoListRateLimit).refreshRateLimiter(CodingCalendarViewModel.CONTEST_VIEW_MODEL);
    }


    @Test
    public void shouldLoadContests_when_reloadFalse_and_rateLimitTrue() {
        // given
        Mockito.when(repoListRateLimit.shouldFetch(CodingCalendarViewModel.CONTEST_VIEW_MODEL))
            .thenReturn(true);

        // when
        codingCalendarViewModel.loadContests(false);

        // then
        Mockito.verify(codingCalendarRepository).fetchContests(false);
        Mockito.verify(repoListRateLimit).refreshRateLimiter(CodingCalendarViewModel.CONTEST_VIEW_MODEL);
    }

    @Test
    public void shouldSetClickAction() {
        // given
        codingCalendarViewModel.getSelectedContest().observeForever(observerLong);

        // when
        codingCalendarViewModel.openContestDetails(MOCK_CONTEST_ID);

        // then
        Mockito.verify(observerLong).onChanged(MOCK_CONTEST_ID);
    }

}
