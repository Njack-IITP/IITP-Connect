package com.iitp.njack.iitp_connect.core.youtube;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.youtube.playlist.YoutubeActivity;
import com.iitp.njack.iitp_connect.databinding.ActivityChannelSelectBinding;

public class ChannelSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChannelSelectBinding activityChannelSelectBinding = DataBindingUtil.setContentView(this, R.layout.activity_channel_select);

    }

    public void onChannelClick(View view) {
        String id = getString(getResources().getIdentifier(getResources().getResourceEntryName(view.getId()), "string", getPackageName()));
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, YoutubeActivity.class);
        intent.putExtra("channelId", id);
        startActivity(intent);
    }
}

