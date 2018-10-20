package com.iitp.njack.iitp_connect.core.home;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class AuthRepositoryTest {

    @InjectMocks
    private AuthRepository authRepository;


    @Mock
    private FirebaseAuthLiveData firebaseAuthLiveData;


    @Test
    public void it_should_return_fireBase_live_data() {
        //Act
        FirebaseAuthLiveData expectedData = authRepository.getFirebaseAuthLiveData();

        //Assert
        assertThat(expectedData, equalTo(firebaseAuthLiveData));
    }
}
