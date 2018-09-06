package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

/**
 * Handles all the Auth related work.
 */
public class AuthViewModel extends ViewModel {

    private static final FirebaseAuthLiveData firebaseAuthLiveData = new FirebaseAuthLiveData();

    @Inject
    public AuthViewModel() {

    }

    public static LiveData<FirebaseUser> getFirebaseAuthLiveData() {
        return firebaseAuthLiveData;
    }

    public void logout() {
        // TODO log the user out
    }
}
