package com.iitp.njack.iitp_connect.core.calendar.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.databinding.ActivityCodingCalendarBinding;
import com.iitp.njack.iitp_connect.ui.ViewUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.iitp.njack.iitp_connect.ui.ViewUtils.showView;

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
        codingCalendarViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CodingCalendarViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();

        codingCalendarViewModel.getProgress().observe(this, this::showProgress);
        codingCalendarViewModel.getError().observe(this, this::showError);
        loadContests(false);

        setupRecyclerView();
        setupRefreshListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        refreshLayout.setOnRefreshListener(null);
    }

    private void setupRecyclerView() {
        codingCalendarAdapter = new CodingCalendarAdapter();

        RecyclerView recyclerView = binding.contestsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(codingCalendarAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadContests(boolean reload) {
        codingCalendarViewModel.loadContests(reload).observe(this, this::showResults);
    }

    private void setupRefreshListener() {
        refreshLayout = binding.swipeContainer;
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            loadContests(true);
        });
    }

    @Override
    public void showResults(List<Contest> items) {
        Collections.reverse(items);
        codingCalendarAdapter.setContests(items);
    }

    @Override
    public void showEmptyView(boolean show) {
        ViewUtils.showView(binding.emptyView, show);
    }

    @Override
    public void showError(String error) {
        ViewUtils.showSnackbar(binding.getRoot(), error);
    }

    @Override
    public void showProgress(boolean show) {
        showView(binding.progressBar, show);
    }

    @Override
    public void onRefreshComplete(boolean success) {
        if (success)
            ViewUtils.showSnackbar(binding.contestsRecyclerView, R.string.refresh_complete);
    }
}
