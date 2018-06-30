package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

/**
 * Handles all the Auth related work.
 */
public class AuthViewModel extends ViewModel {

    @Inject
    public AuthViewModel() {

    }

    public void logout() {
        // log the user out
    }
}
