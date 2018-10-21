package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.iitp.njack.iitp_connect.data.user.User;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private final ProfileRepository profileRepository;
    private LiveData<DataSnapshot> user;

    @Inject
    public ProfileViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    protected LiveData<DataSnapshot> getUser() {
        if (user == null) {
            user = profileRepository.getUser();
        }
        return user;
    }

    public void setUser(User user) {
        profileRepository.setUser(user);
    }

    public void setField(UserFieldType userFieldType, String profileField) {
        profileRepository.setField(userFieldType, profileField);
    }

}
