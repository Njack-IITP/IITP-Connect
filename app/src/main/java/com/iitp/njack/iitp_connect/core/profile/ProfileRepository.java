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
        firebaseDatabaseLiveData = new FirebaseDatabaseLiveData(this.databaseReference);
    }

    @NonNull
    public LiveData<DataSnapshot> getUser() {
        if(firebaseDatabaseLiveData.getValue()==null)
        {
            User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(),"","","");
            databaseReference.setValue(user);
        }
        return firebaseDatabaseLiveData;
    }
}
