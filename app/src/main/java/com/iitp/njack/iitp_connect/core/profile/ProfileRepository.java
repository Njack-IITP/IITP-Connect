package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.data.user.UserDao;

import javax.inject.Inject;

public class ProfileRepository {
    private DatabaseReference databaseReference;
    private FirebaseDatabaseLiveData firebaseDatabaseLiveData;
    private FirebaseUser firebaseUser;

    @Inject
    public ProfileRepository(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        this.databaseReference = databaseReference.child("users").child(firebaseUser.getUid());
        this.firebaseUser = firebaseUser;
        if (databaseReference.equals(null)) {
            User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), "", "", "");
            databaseReference.setValue(user);
        }
        firebaseDatabaseLiveData = new FirebaseDatabaseLiveData(this.databaseReference);
    }

    @NonNull
    public LiveData<DataSnapshot> getUser() {

        return firebaseDatabaseLiveData;
    }

    public void setField(int fieldType, String profileField) {
        switch (fieldType) {
            case 1:
                databaseReference.child("firstName").setValue(profileField);
                break;
            case 2:
                databaseReference.child("lastName").setValue(profileField);
                break;
            case 3:
                databaseReference.child("roll").setValue(profileField);
                break;
        }

    }
}
