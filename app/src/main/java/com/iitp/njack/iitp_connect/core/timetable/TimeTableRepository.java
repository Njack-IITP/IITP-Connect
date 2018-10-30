package com.iitp.njack.iitp_connect.core.timetable;

import android.arch.lifecycle.LiveData;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.core.profile.FirebaseDatabaseLiveData;

import javax.inject.Inject;

public class TimeTableRepository {
    private FirebaseDatabaseLiveData timeTableDatabaseLiveData;
    private FirebaseDatabaseLiveData firebaseDatabaseLiveData;

    @Inject
    public TimeTableRepository(FirebaseApp firebaseApp, DatabaseReference databaseReference, FirebaseAuth firebaseAuth) {
        timeTableDatabaseLiveData = new FirebaseDatabaseLiveData(FirebaseDatabase.getInstance(firebaseApp).getReference());
        if (firebaseAuth.getCurrentUser() == null) {
            firebaseDatabaseLiveData = null;
        } else {
            firebaseDatabaseLiveData = new FirebaseDatabaseLiveData(databaseReference
                .child("users")
                .child(firebaseAuth
                    .getCurrentUser()
                    .getUid()));
        }
    }

    public LiveData<DataSnapshot> getData() {
        return timeTableDatabaseLiveData;
    }

    public LiveData<DataSnapshot> getUser() {
        return firebaseDatabaseLiveData;
    }
}
