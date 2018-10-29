package com.iitp.njack.iitp_connect.core.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

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
    private User user;
    @Inject
    private FirebaseAuth firebaseAuth;
    private ArrayAdapter<CharSequence> courseAdapter;
    private ArrayAdapter<CharSequence> branchAdapter;
    private ArrayAdapter<CharSequence> yearAdapter;
    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setClickListeners();
        profileViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProfileViewModel.class);

        courseAdapter = ArrayAdapter.createFromResource(this,
            R.array.course_array, android.R.layout.simple_spinner_item);
        branchAdapter = ArrayAdapter.createFromResource(this,
            R.array.branch_array, android.R.layout.simple_spinner_item);
        yearAdapter = ArrayAdapter.createFromResource(this,
            R.array.year_array, android.R.layout.simple_spinner_item);

        binding.profileCourseEdit.setAdapter(courseAdapter);
        binding.profileBranchEdit.setAdapter(branchAdapter);
        binding.profileYearEdit.setAdapter(yearAdapter);

        final Observer<DataSnapshot> userObserver = currentUser -> {
            if (currentUser.getValue(User.class) == null) {
                user.setUserName(firebaseAuth.getCurrentUser().getDisplayName());
                user.setEmail(firebaseAuth.getCurrentUser().getEmail());
                profileViewModel.setUser(user);
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
        binding.profileCourseImage.setOnClickListener(this::onClick);
        binding.profileBranchImage.setOnClickListener(this::onClick);
        binding.profileYearImage.setOnClickListener(this::onClick);
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
                profileViewModel.setField(UserFieldType.FIRST_NAME, profileField);
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
                profileViewModel.setField(UserFieldType.LAST_NAME, profileField);
                binding.profileLastName.setVisibility(View.VISIBLE);
                binding.profileLastNameEdit.setVisibility(View.GONE);
                binding.profileLastNameImage.setImageResource(R.drawable.ic_edit);
            }
        } else if (v == binding.profileRollImage) {
            if (binding.profileRoll.getVisibility() == View.VISIBLE) {
                binding.profileRoll.setVisibility(View.GONE);
                binding.profileRollEdit.setVisibility(View.VISIBLE);
                binding.profileRollImage.setImageResource(R.drawable.ic_done);
            } else {
                profileField = binding.profileRollEdit.getText().toString();
                profileViewModel.setField(UserFieldType.ROLL, profileField);
                binding.profileRoll.setVisibility(View.VISIBLE);
                binding.profileRollEdit.setVisibility(View.GONE);
                binding.profileRollImage.setImageResource(R.drawable.ic_edit);
            }
        } else if (v == binding.profileCourseImage) {
            if (binding.profileCourse.getVisibility() == View.VISIBLE) {
                binding.profileCourse.setVisibility(View.GONE);
                binding.profileCourseEdit.setVisibility(View.VISIBLE);
                binding.profileCourseImage.setImageResource(R.drawable.ic_done);
            } else {
                profileField = binding.profileCourseEdit.getSelectedItem().toString();
                profileViewModel.setField(UserFieldType.COURSE, profileField);
                binding.profileCourse.setVisibility(View.VISIBLE);
                binding.profileCourseEdit.setVisibility(View.GONE);
                binding.profileCourseImage.setImageResource(R.drawable.ic_edit);
            }
        } else if (v == binding.profileBranchImage) {
            if (binding.profileBranch.getVisibility() == View.VISIBLE) {
                binding.profileBranch.setVisibility(View.GONE);
                binding.profileBranchEdit.setVisibility(View.VISIBLE);
                binding.profileBranchImage.setImageResource(R.drawable.ic_done);
            } else {
                profileField = binding.profileBranchEdit.getSelectedItem().toString();
                profileViewModel.setField(UserFieldType.BRANCH, profileField);
                binding.profileBranch.setVisibility(View.VISIBLE);
                binding.profileBranchEdit.setVisibility(View.GONE);
                binding.profileBranchImage.setImageResource(R.drawable.ic_edit);
            }
        } else {
            if (binding.profileYear.getVisibility() == View.VISIBLE) {
                binding.profileYear.setVisibility(View.GONE);
                binding.profileYearEdit.setVisibility(View.VISIBLE);
                binding.profileYearImage.setImageResource(R.drawable.ic_done);
            } else {
                profileField = binding.profileYearEdit.getSelectedItem().toString();
                profileViewModel.setField(UserFieldType.YEAR, profileField);
                binding.profileYear.setVisibility(View.VISIBLE);
                binding.profileYearEdit.setVisibility(View.GONE);
                binding.profileYearImage.setImageResource(R.drawable.ic_edit);
            }
        }
    }
}
