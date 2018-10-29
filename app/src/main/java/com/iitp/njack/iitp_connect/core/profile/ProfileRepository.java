package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.iitp.njack.iitp_connect.data.user.User;

import javax.inject.Inject;

public class ProfileRepository {
    @VisibleForTesting
    private FirebaseDatabaseLiveData firebaseDatabaseLiveData;
    private DatabaseReference databaseReference;

    @Inject
    public ProfileRepository(DatabaseReference databaseReference, FirebaseAuth firebaseAuth) {
        this.databaseReference = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid());
        firebaseDatabaseLiveData = new FirebaseDatabaseLiveData(this.databaseReference);
    }

    @NonNull
    public LiveData<DataSnapshot> getUser() {
        return firebaseDatabaseLiveData;
    }

    public void setUser(User user) {
        databaseReference.setValue(user);
    }

    public void setField(UserFieldType userFieldType, String profileField) {
        databaseReference.child(userFieldType.key).setValue(profileField);
    }
}
