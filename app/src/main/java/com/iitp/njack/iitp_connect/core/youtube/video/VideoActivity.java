package com.iitp.njack.iitp_connect.core.youtube.video;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.youtube.VideoPlayActivity;
import com.iitp.njack.iitp_connect.data.youtube.YoutubeVideo;
import com.iitp.njack.iitp_connect.databinding.ActivityVideoBinding;

import javax.inject.Inject;

public class VideoActivity extends AppCompatActivity {
    @Inject
    public GoogleAccountCredential googleAccountCredential;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private String playlistId = "";
    private ActivityVideoBinding activityVideoBinding;
    private SwipeRefreshLayout refreshLayout;

    private VideoViewModel videoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        videoViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoViewModel.class);

        playlistId = getIntent().getStringExtra("playlistId");
        if (playlistId == null) {
            playlistId = "PLOU2XLYxmsILACK8NF7UHElmmZzudR7c7";
        }
        activityVideoBinding.setModel(videoViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpListUpdate();
    }

    @Override
    protected void onStop() {
        super.onStop();

        refreshLayout.setOnRefreshListener(null);
    }

    private void setUpListUpdate() {
        videoViewModel.loading.set(View.VISIBLE);
        videoViewModel.fetchList(playlistId, true);
        videoViewModel.getVideos().observe(this, youtubeVideos -> {
            videoViewModel.loading.set(View.GONE);
            if (youtubeVideos.size() == 0) {
                videoViewModel.showEmpty.set(View.VISIBLE);
            } else {
                videoViewModel.showEmpty.set(View.GONE);
                videoViewModel.setPlaylistsInAdapter(youtubeVideos);
            }
        });
        setUpRefreshListener();
        setUpListClick();
    }

    private void setUpListClick() {
        videoViewModel.getSelected().observe(this, new Observer<YoutubeVideo>() {
            @Override
            public void onChanged(@Nullable YoutubeVideo youtubeVideo) {
                if (youtubeVideo != null) {
                    Toast.makeText(VideoActivity.this, "you selected a " + youtubeVideo.getVideoId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VideoActivity.this, VideoPlayActivity.class);
                    intent.putExtra("video_id", youtubeVideo.getVideoId());
                    startActivity(intent);
                }
            }
        });
    }

    private void setUpRefreshListener() {
        refreshLayout = activityVideoBinding.swipeContainer;
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            videoViewModel.fetchList(playlistId, true);
        });
    }
}

