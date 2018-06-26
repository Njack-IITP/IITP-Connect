package com.iitp.njack.iitp_connect.core.calendar.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.databinding.ActivityContestDetailBinding;

import javax.inject.Inject;

public class ContestDetailActivity extends AppCompatActivity implements ContestDetailView {
    public static final String CONTEST_ID = "contest_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private Long contest_id;
    private ActivityContestDetailBinding binding;
    private ContestDetailViewModel contestDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contest_id=getIntent().getLongExtra(CONTEST_ID, 0);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contest_detail);
        contestDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ContestDetailViewModel.class);

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        contestDetailViewModel.getContest(contest_id).observe(this, this::showResult);
    }

    @Override
    public void showResult(Contest item) {
        binding.setContest(item);
    }
}
