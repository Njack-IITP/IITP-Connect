package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.firebase.ui.auth.AuthUI;
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

    public void logout(Context context) {
        AuthUI.getInstance().signOut(context);
    }
}
