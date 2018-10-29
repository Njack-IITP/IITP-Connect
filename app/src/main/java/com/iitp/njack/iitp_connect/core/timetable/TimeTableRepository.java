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
    private FirebaseApp timeTableGenerator;
    private FirebaseDatabase timeTableDatabase;
    private DatabaseReference timeTableReference;
    private DatabaseReference databaseReference;
    private FirebaseDatabaseLiveData timeTableDatabaseLiveData;
    private FirebaseDatabaseLiveData firebaseDatabaseLiveData;

    @Inject
    public TimeTableRepository(FirebaseApp firebaseApp, DatabaseReference databaseReference, FirebaseAuth firebaseAuth) {
        timeTableGenerator = firebaseApp;
        timeTableDatabase = FirebaseDatabase.getInstance(timeTableGenerator);
        timeTableReference = timeTableDatabase.getReference();
        timeTableDatabaseLiveData = new FirebaseDatabaseLiveData(this.timeTableReference);
        if (firebaseAuth.getCurrentUser() == null) {
            firebaseDatabaseLiveData = null;
        } else {
            this.databaseReference = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid());
            firebaseDatabaseLiveData = new FirebaseDatabaseLiveData(this.databaseReference);
        }
    }

    public LiveData<DataSnapshot> getData() {
        return timeTableDatabaseLiveData;
    }

    public LiveData<DataSnapshot> getUser() {
        return firebaseDatabaseLiveData;
    }
}
