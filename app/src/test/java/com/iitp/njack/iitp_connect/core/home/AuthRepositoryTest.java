package com.iitp.njack.iitp_connect.core.home;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthRepositoryTest {

    @Test
    public void it_should_return_firebase_auth_live_data() {
        FirebaseAuthLiveData firebaseAuthLiveData = new FirebaseAuthLiveData();

        AuthRepository authRepository = new AuthRepository(firebaseAuthLiveData);

        assertEquals(authRepository.getFirebaseAuthLiveData(), firebaseAuthLiveData);
    }

}
