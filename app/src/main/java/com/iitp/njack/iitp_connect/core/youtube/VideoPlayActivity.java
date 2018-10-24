package com.iitp.njack.iitp_connect.core.youtube;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.databinding.ActivityVideoPlayBinding;

public class VideoPlayActivity extends YouTubeBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVideoPlayBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_video_play);
        final String id = getIntent().getStringExtra("video_id");
        binding.player.initialize("AIzaSyDt35WYuKJD9QKaKWrx6sPNazu92nT4M9w",
            new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer, boolean b) {
                    // do any work here to cue video, play video, etc.
                    youTubePlayer.cueVideo(id);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                    YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
    }
}