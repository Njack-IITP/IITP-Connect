package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.iitp.njack.iitp_connect.R;

import javax.inject.Inject;

/**
 * Handles all the Auth related work.
 */
public class AuthViewModel extends ViewModel {

    private AuthRepository loginRepository;
    private FirebaseAuthLiveData firebaseAuthLiveData;

    @Inject
    public AuthViewModel(AuthRepository loginRepository) {
        this.loginRepository=loginRepository;
    }

    public LiveData<FirebaseUser> getFirebaseAuthLiveData() {
        if(firebaseAuthLiveData==null) {
            firebaseAuthLiveData=loginRepository.getFirebaseAuthLiveData();
        }
        return firebaseAuthLiveData;
    }

    public void logout(Context context) {
        AuthUI.getInstance().signOut(context);
        Toast.makeText(context, R.string.log_out_success,Toast.LENGTH_SHORT).show();
    }

}
