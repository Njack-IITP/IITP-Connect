package com.iitp.njack.iitp_connect.core.calendar.detail;

import com.iitp.njack.iitp_connect.data.contest.ContestDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContestDetailRepositoryTest {

    private static final long MOCK_CONTEST_ID = 222L;

    @InjectMocks
    private ContestDetailRepository contestDetailRepository;

    @Mock
    private ContestDao contestDao;

    @Test
    public void shouldReturnContestLiveDataById() {
        // when
        contestDetailRepository.getContest(MOCK_CONTEST_ID);

        // then
        Mockito.verify(contestDao).getContestById(Mockito.eq(MOCK_CONTEST_ID));
    }
}
