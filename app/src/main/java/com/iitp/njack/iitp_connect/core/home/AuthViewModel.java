package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

/**
 * Handles all the Auth related work.
 */
public class AuthViewModel extends ViewModel {

    @Inject
    public AuthViewModel() {

    }

    private static final FirebaseAuthLiveData firebaseAuthLiveData = new
            FirebaseAuthLiveData();

    public static LiveData<FirebaseUser> getFirebaseAuthLiveData() {
        return firebaseAuthLiveData; }

    public void logout() {
        // TODO log the user out
    }
}
