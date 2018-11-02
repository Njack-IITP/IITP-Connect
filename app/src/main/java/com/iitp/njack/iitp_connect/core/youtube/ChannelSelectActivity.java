package com.iitp.njack.iitp_connect.core.youtube;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.databinding.ActivityChannelSelectBinding;

import lombok.ToString;

public class ChannelSelectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChannelSelectBinding activityChannelSelectBinding = DataBindingUtil.setContentView(this,R.layout.activity_channel_select);
        Spinner spinner = activityChannelSelectBinding.channelIncludedLayout.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.channels_array,
            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"Selected a: " + parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
