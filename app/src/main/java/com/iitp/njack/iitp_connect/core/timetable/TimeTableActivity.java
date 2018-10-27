package com.iitp.njack.iitp_connect.core.timetable;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.timetabledata.TimeTableDatabase;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.databinding.ActivityTimeTableBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TimeTableActivity extends AppCompatActivity {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    User user;

    private TimeTableViewModel timeTableViewModel;
    ActivityTimeTableBinding binding;

    String course = "Btech";
    String year = "First";
    String branch = "CS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_time_table);
        timeTableViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TimeTableViewModel.class);

        final Observer<DataSnapshot> userObserver = currentUser -> {
            if(currentUser != null) {
                user = currentUser.getValue(User.class);
                course = (user.getCourse() == null) ? "Btech" : user.getCourse();
                branch = (user.getBranch() == null) ? "CS" : user.getBranch();
                year = (user.getYear() == null) ? "First" : user.getYear();
            } else {
                //Do Nothing
            }
        };
        timeTableViewModel.getUser().observe(this,userObserver);

        final Observer<DataSnapshot> timeTableObserver = currentTimeTable -> {
            binding.setTimeTable(currentTimeTable.child(course).child(year).child(branch).getValue(TimeTableDatabase.class));
        };
        timeTableViewModel.getData().observe(this, timeTableObserver);

    }
}
