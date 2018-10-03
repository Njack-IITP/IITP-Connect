package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.iitp.njack.iitp_connect.data.user.User;

import javax.inject.Inject;

public class ProfileRepository {
    private DatabaseReference databaseReference;
    private FirebaseDatabaseLiveData firebaseDatabaseLiveData;

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

    public void setField(int fieldType, String profileField) {
        switch (fieldType) {
            case 1:
                databaseReference.child("userName").setValue(profileField);
                break;
            case 2:
                databaseReference.child("email").setValue(profileField);
                break;
            case 3:
                databaseReference.child("firstName").setValue(profileField);
                break;
            case 4:
                databaseReference.child("lastName").setValue(profileField);
                break;
            case 5:
                databaseReference.child("roll").setValue(profileField);
                break;
        }
    }
}
