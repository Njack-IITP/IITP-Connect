package com.iitp.njack.iitp_connect.core.home;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.iitp.njack.iitp_connect.data.user.UserDao;

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
