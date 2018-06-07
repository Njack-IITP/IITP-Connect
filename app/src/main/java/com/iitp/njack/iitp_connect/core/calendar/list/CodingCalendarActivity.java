package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.databinding.ActivityCodingCalendarBinding;

import java.util.List;

import javax.inject.Inject;

public class CodingCalendarActivity extends AppCompatActivity implements CodingCalendarView {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CodingCalendarViewModel codingCalendarViewModel;
    private ActivityCodingCalendarBinding binding;
    private SwipeRefreshLayout refreshLayout;
    private CodingCalendarAdapter codingCalendarAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_coding_calendar);
        codingCalendarViewModel = ViewModelProviders.of(this, viewModelFactory).get(CodingCalendarViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupRecyclerView();
        setupRefreshListener();


    }

    private void setupRecyclerView() {

    }


    private void setupRefreshListener() {
    }

    @Override
    public void showResults(List<Contest> items) {

    }

    @Override
    public void showEmptyView(boolean show) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void onRefreshComplete(boolean success) {

    }
}
