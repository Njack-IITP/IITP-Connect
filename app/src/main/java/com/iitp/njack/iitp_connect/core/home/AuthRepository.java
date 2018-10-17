package com.iitp.njack.iitp_connect.core.home;

import android.support.annotation.NonNull;

import javax.inject.Inject;

public class AuthRepository {

    private FirebaseAuthLiveData firebaseAuthLiveData;

    @Inject
    public AuthRepository(FirebaseAuthLiveData firebaseAuthLiveData) {
        this.firebaseAuthLiveData = firebaseAuthLiveData;
    }

    @NonNull
    public FirebaseAuthLiveData getFirebaseAuthLiveData() {
        return firebaseAuthLiveData;
    }
}
