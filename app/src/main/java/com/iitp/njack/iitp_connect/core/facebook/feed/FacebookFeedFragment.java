package com.iitp.njack.iitp_connect.core.facebook.feed;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.common.di.Injectable;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;
import com.iitp.njack.iitp_connect.data.network.Status;
import com.iitp.njack.iitp_connect.databinding.FragmentFacebookFeedBinding;
import com.iitp.njack.iitp_connect.ui.ViewUtils;

import java.util.List;

import javax.inject.Inject;

import static com.iitp.njack.iitp_connect.ui.ViewUtils.showView;


public class FacebookFeedFragment extends Fragment implements FacebookFeedView, Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FacebookFeedViewModel facebookFeedViewModel;
    private FragmentFacebookFeedBinding binding;
    private SwipeRefreshLayout refreshLayout;
    private FacebookFeedAdapter facebookFeedAdapter;

    @Inject
    public FacebookFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facebook_feed, container, false);
        facebookFeedViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FacebookFeedViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();

        facebookFeedViewModel.getSelectedFacebookPost().observe(this, this::openFacebookPostDetails);
        loadFacebookPosts(false);

        setupRecyclerView();
        setupRefreshListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        refreshLayout.setOnRefreshListener(null);
    }

    private void setupRecyclerView() {
        facebookFeedAdapter = new FacebookFeedAdapter(facebookFeedViewModel);

        RecyclerView recyclerView = binding.facebookPostsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(facebookFeedAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadFacebookPosts(boolean reload) {
        facebookFeedViewModel.loadFacebookPosts(reload).observe(this, facebookPostResource -> {
            if (facebookPostResource != null) {
                if (facebookPostResource.status == Status.SUCCESS) {
                    showResults(facebookPostResource.data);
                    showProgress(false);
                } else if (facebookPostResource.status == Status.ERROR) {
                    showError(facebookPostResource.message);
                    showProgress(false);
                } else {
                    showProgress(true);
                }
            } else {
                showProgress(true);
            }
        });
    }

    //@Override
    //protected int getTitle() {
    //    return R.string.facebook_feed;
    //}

    private void setupRefreshListener() {
        refreshLayout = binding.swipeContainer;
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            loadFacebookPosts(true);
        });
    }

    @Override
    public void showResults(List<FacebookPost> items) {
        facebookFeedAdapter.setFacebookPosts(items);
    }

    @Override
    public void showEmptyView(boolean show) {
        showView(binding.emptyView, show);
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
            ViewUtils.showSnackbar(binding.facebookPostsRecyclerView, R.string.refresh_complete);
    }

    @Override
    public void openFacebookPostDetails(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
