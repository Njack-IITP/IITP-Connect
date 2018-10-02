package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthLiveData extends LiveData<FirebaseUser> {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        setValue(firebaseUser);
    };

    @Override
    protected void onActive() {
        super.onActive();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
