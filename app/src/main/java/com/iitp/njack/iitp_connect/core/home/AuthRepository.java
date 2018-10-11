package com.iitp.njack.iitp_connect.core.home;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.iitp.njack.iitp_connect.data.user.UserDao;

import javax.inject.Inject;

public class AuthRepository {
   // private final FirebaseAuth firebaseAuth;
  //  private final DatabaseReference databaseReference;
  //  private final UserDao userDao;
    private FirebaseAuthLiveData firebaseAuthLiveData;

    @Inject
    public AuthRepository(FirebaseAuthLiveData firebaseAuthLiveData) {
        this.firebaseAuthLiveData=firebaseAuthLiveData;
    }

    @NonNull
    public FirebaseAuthLiveData getFirebaseAuthLiveData() {
        return firebaseAuthLiveData;
    }
   // @Inject
   // public AuthRepository(DatabaseReference databaseReference, FirebaseAuth firebaseAuth,
    //                      UserDao userDao) {
    //    this.databaseReference = databaseReference;
    //    this.firebaseAuth = firebaseAuth;
    //    this.userDao = userDao;
  //  }

//    public LiveData<User> getLoggedInUser() {
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//    }
}
