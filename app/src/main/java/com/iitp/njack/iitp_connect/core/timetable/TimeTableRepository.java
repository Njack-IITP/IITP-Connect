package com.iitp.njack.iitp_connect.core.timetable;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.core.profile.FirebaseDatabaseLiveData;

import javax.inject.Inject;

public class TimeTableRepository {
    FirebaseApp timeTableGenerator;
    FirebaseDatabase timeTableDatabase;
    DatabaseReference timeTableReference;
    DatabaseReference databaseReference;
    FirebaseDatabaseLiveData timeTableDatabaseLiveData;
    FirebaseDatabaseLiveData firebaseDatabaseLiveData;

    @Inject
    public TimeTableRepository(FirebaseApp firebaseApp, DatabaseReference databaseReference, FirebaseAuth firebaseAuth) {
        timeTableGenerator = firebaseApp;
        timeTableDatabase = FirebaseDatabase.getInstance(timeTableGenerator);
        timeTableReference = timeTableDatabase.getReference();
        timeTableDatabaseLiveData = new FirebaseDatabaseLiveData(this.timeTableReference);
        if(firebaseAuth.getCurrentUser()==null) {
            firebaseDatabaseLiveData = null;
        } else {
            this.databaseReference = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid());
            firebaseDatabaseLiveData = new FirebaseDatabaseLiveData(this.databaseReference);
        }
    }

    LiveData<DataSnapshot> getData(){
        return timeTableDatabaseLiveData;
    }

    LiveData<DataSnapshot> getUser() { return firebaseDatabaseLiveData; }
}
