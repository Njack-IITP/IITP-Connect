package com.iitp.njack.iitp_connect.CodingCalendar.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitp.njack.iitp_connect.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srv_twry on 20/8/17.
 * The adapter for the CodingCalendarRecyclerView.
 */

public class CodingCalendarAdapter extends RecyclerView.Adapter<CodingCalendarAdapter.ViewHolder> {

    public CodingCalendarAdapter(Context context){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.contest_view_holder_platform_image) ImageView contestPlatform;
        @BindView(R.id.tv_contest_platform_name) TextView contestTitle;
        @BindView(R.id.tv_contest_start_time) TextView contestStartTime;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
