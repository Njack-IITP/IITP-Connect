package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.databinding.ActivityProfileBinding;

import javax.inject.Inject;

public class ProfileActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        profileViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProfileViewModel.class);

        final Observer<DataSnapshot> userObserver = new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot currentUser) {
                binding.setProfile(currentUser.getValue(User.class));
            }
        };
        profileViewModel.getUser().observe(this, userObserver);
    }
}
