package com.iitp.njack.iitp_connect.core.youtube;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.databinding.ActivityVideoPlayBinding;

import static com.iitp.njack.iitp_connect.core.youtube.video.VideoActivity.VIDEO_ID;

public class VideoPlayActivity extends YouTubeBaseActivity {
    private String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVideoPlayBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_video_play);
        final String id = getIntent().getStringExtra(VIDEO_ID);
        binding.player.initialize(API_KEY,
            new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.cueVideo(id);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                    YouTubeInitializationResult youTubeInitializationResult) {
                    //nothing here
                }
            });
    }
}
