package com.iitp.njack.iitp_connect.core.timetable;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.timetabledata.TimeTableDatabase;
import com.iitp.njack.iitp_connect.data.timetabledata.TimeTableInformation;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.databinding.ActivityTimeTableBinding;
import com.iitp.njack.iitp_connect.databinding.TimetableDialogBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TimeTableActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    User user;
    @Inject
    TimeTableInformation timeTableInformation;

    ArrayAdapter<CharSequence> courseAdapter;
    ArrayAdapter<CharSequence> branchAdapter;
    ArrayAdapter<CharSequence> yearAdapter;

    private TimeTableViewModel timeTableViewModel;
    private ActivityTimeTableBinding binding;
    private DataSnapshot timeTableData;
    private Dialog dialog;
    private TimetableDialogBinding timetableDialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_time_table);
        timeTableViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TimeTableViewModel.class);

        final Observer<DataSnapshot> userObserver = currentUser -> {
            if(currentUser != null) {
                user = currentUser.getValue(User.class);
                timeTableInformation.setCourse((user.getCourse() == null) ? "Btech" : user.getCourse());
                timeTableInformation.setBranch((user.getBranch() == null) ? "CS" : user.getBranch());
                timeTableInformation.setYear((user.getYear() == null) ? "First" : user.getYear());
                setValues();
            } else {
                //Do Nothing
            }
        };
        if(timeTableViewModel.getUser() != null)
        timeTableViewModel.getUser().observe(this,userObserver);

        final Observer<DataSnapshot> timeTableObserver = currentTimeTable -> {
            timeTableData = currentTimeTable;
            setValues();
        };
        timeTableViewModel.getData().observe(this, timeTableObserver);

    }

    private void setValues()
    {
        if(timeTableData == null)
            return;
        binding.timeTableCourse.setText(timeTableInformation.getCourse());
        binding.timeTableBranch.setText(timeTableInformation.getBranch());
        binding.timeTableYear.setText(timeTableInformation.getYear());
        binding.setTimeTable(timeTableData
            .child(timeTableInformation.getCourse())
            .child(timeTableInformation.getYear())
            .child(timeTableInformation.getBranch())
            .getValue(TimeTableDatabase.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_time_table_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.change_table) {
           // LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // View layout = inflater.inflate(R.layout.timetable_dialog, null);
           // layout.setMinimumWidth((int)( * 0.9f));
           // layout.setMinimumHeight((int)(displayRectangle.height() * 0.9f));
            dialog = new Dialog(this);
            dialog.setTitle(R.string.time_table_dialog_title);
            timetableDialogBinding = (TimetableDialogBinding)DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.timetable_dialog, null, false);
            dialog.setContentView(timetableDialogBinding.getRoot());
            timetableDialogBinding.timeTableChangeButton.setOnClickListener(this::onClick);
            courseAdapter = ArrayAdapter.createFromResource(this,
                R.array.course_array, android.R.layout.simple_spinner_item);
            branchAdapter = ArrayAdapter.createFromResource(this,
                R.array.branch_array, android.R.layout.simple_spinner_item);
            yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
            timetableDialogBinding.timeTableCourseChange.setAdapter(courseAdapter);
            timetableDialogBinding.timeTableBranchChange.setAdapter(branchAdapter);
            timetableDialogBinding.timeTableYearChange.setAdapter(yearAdapter);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        timeTableInformation.setCourse(timetableDialogBinding.timeTableCourseChange.getSelectedItem().toString());
        timeTableInformation.setBranch(timetableDialogBinding.timeTableBranchChange.getSelectedItem().toString());
        timeTableInformation.setYear(timetableDialogBinding.timeTableYearChange.getSelectedItem().toString());
        dialog.dismiss();
        setValues();
    }
}
