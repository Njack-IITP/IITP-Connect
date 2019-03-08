package com.iitp.njack.iitp_connect.core.youtube;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.youtube.playlist.YoutubeActivity;

public class ChannelSelectActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "channelId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_channel_select);

    }

    public void onChannelClick(View view) {
        String id = getString(getResources().getIdentifier(getResources().getResourceEntryName(view.getId()), "string", getPackageName()));
        Intent intent = new Intent(this, YoutubeActivity.class);
        intent.putExtra(CHANNEL_ID, id);
        startActivity(intent);
    }
}

