package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.databinding.ActivityProfileBinding;

import javax.inject.Inject;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    User user;
    @Inject
    FirebaseAuth firebaseAuth;

    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setClickListeners();
        profileViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProfileViewModel.class);

        final Observer<DataSnapshot> userObserver = currentUser -> {
            if (currentUser.getValue(User.class) == null) {
                user.setUserName(firebaseAuth.getCurrentUser().getDisplayName());
                user.setEmail(firebaseAuth.getCurrentUser().getEmail());
                profileViewModel.setUser( user);
            } else {
                binding.setProfile(currentUser.getValue(User.class));
            }
        };
        profileViewModel.getUser().observe(this, userObserver);
    }

    private void setClickListeners() {
        binding.profileFirstNameImage.setOnClickListener(this::onClick);
        binding.profileLastNameImage.setOnClickListener(this::onClick);
        binding.profileRollImage.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        String profileField;
        if (v == binding.profileFirstNameImage) {
            if (binding.profileFirstName.getVisibility() == View.VISIBLE) {
                binding.profileFirstName.setVisibility(View.GONE);
                binding.profileFirstNameEdit.setVisibility(View.VISIBLE);
                binding.profileFirstNameImage.setImageResource(R.drawable.ic_done);
            } else {
                profileField = binding.profileFirstNameEdit.getText().toString();
                profileViewModel.setField(3, profileField);
                binding.profileFirstName.setVisibility(View.VISIBLE);
                binding.profileFirstNameEdit.setVisibility(View.GONE);
                binding.profileFirstNameImage.setImageResource(R.drawable.ic_edit);
            }
        } else if (v == binding.profileLastNameImage) {
            if (binding.profileLastName.getVisibility() == View.VISIBLE) {
                binding.profileLastName.setVisibility(View.GONE);
                binding.profileLastNameEdit.setVisibility(View.VISIBLE);
                binding.profileLastNameImage.setImageResource(R.drawable.ic_done);
            } else {
                profileField = binding.profileLastNameEdit.getText().toString();
                profileViewModel.setField(4, profileField);
                binding.profileLastName.setVisibility(View.VISIBLE);
                binding.profileLastNameEdit.setVisibility(View.GONE);
                binding.profileLastNameImage.setImageResource(R.drawable.ic_edit);
            }
        } else {
            if (binding.profileRoll.getVisibility() == View.VISIBLE) {
                binding.profileRoll.setVisibility(View.GONE);
                binding.profileRollEdit.setVisibility(View.VISIBLE);
                binding.profileRollImage.setImageResource(R.drawable.ic_done);
            } else {
                profileField = binding.profileRollEdit.getText().toString();
                profileViewModel.setField(5, profileField);
                binding.profileRoll.setVisibility(View.VISIBLE);
                binding.profileRollEdit.setVisibility(View.GONE);
                binding.profileRollImage.setImageResource(R.drawable.ic_edit);
            }
        }
    }
}
