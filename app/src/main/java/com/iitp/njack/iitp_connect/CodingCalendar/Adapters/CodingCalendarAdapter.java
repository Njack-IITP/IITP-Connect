package com.iitp.njack.iitp_connect.CodingCalendar.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitp.njack.iitp_connect.CodingCalendar.POJOs.Contest;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.Utils.DatabaseUtilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by srv_twry on 20/8/17.
 * The adapter for the CodingCalendarRecyclerView.
 */

public class CodingCalendarAdapter extends RecyclerView.Adapter<CodingCalendarAdapter.ViewHolder> {

    private final ArrayList<Contest> contestArrayList;
    private final ContestRecyclerViewOnClickListener contestRecyclerViewOnClickListener;

    public CodingCalendarAdapter(ContestRecyclerViewOnClickListener contestRecyclerViewOnClickListener, ArrayList<Contest> contestArrayList){
        this.contestArrayList = contestArrayList;
        this.contestRecyclerViewOnClickListener = contestRecyclerViewOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contest_recyclerview_view_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return contestArrayList.size();
    }

    public interface ContestRecyclerViewOnClickListener{
        void onContestListItemClicked(Contest clickedContest);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView contestPlatform;
        TextView contestTitle;
        TextView contestStartTime;
        final boolean isLandscape;

        public ViewHolder(View view){
            super(view);
            contestTitle = (TextView) view.findViewById(R.id.tv_contest_platform_name);
            contestStartTime = (TextView) view.findViewById(R.id.tv_contest_start_time);
            contestPlatform = (ImageView) view.findViewById(R.id.contest_view_holder_platform_image);
            view.setOnClickListener(this);
            isLandscape = view.getResources().getBoolean(R.bool.isLandscape);
        }

        @Override
        public void onClick(View v) {
            Contest clickedContest = contestArrayList.get(getAdapterPosition());
            contestRecyclerViewOnClickListener.onContestListItemClicked(clickedContest);
        }

        public void bind(int position) {
            setImageViewUsingUrl(contestArrayList.get(position).getUrl());
            String contestTitleString = contestArrayList.get(position).getTitle();
            if (contestTitleString.length() >=34 && !isLandscape){
                String temp = contestTitleString.substring(0,31) + "...";
                contestTitle.setText(temp);
            }else{
                contestTitle.setText(contestTitleString);
            }
            SpannableString contestStartTimeString = DatabaseUtilities.getStartTimeTextContestList(contestArrayList.get(position).getStartTime());
            contestStartTime.setText(contestStartTimeString);
        }

        private void setImageViewUsingUrl(String url) {
            URL urlPlatform;
            try{
                urlPlatform = new URL(url);
                //get the platform of the contest.
                String platformString = urlPlatform.getHost();
                Log.v("ContestRecyclerViewAdap",platformString);

                if (url.contains("topcoder")){
                    contestPlatform.setImageResource(R.mipmap.topcoder_logo);
                }else if (platformString.contains("codechef")){
                    contestPlatform.setImageResource(R.mipmap.codechef_logo);
                }else if(platformString.contains("hackerrank")){
                    contestPlatform.setImageResource(R.mipmap.hackerrank_logo);
                }else if(platformString.contains("hackerearth")){
                    contestPlatform.setImageResource(R.mipmap.hackerearth_logo);
                }else if(platformString.contains("codeforces")){
                    contestPlatform.setImageResource(R.mipmap.codeforces_logo);
                }
                else if(platformString.contains("urionlinejudge")){
                    contestPlatform.setImageResource(R.mipmap.urioj_logo);
                }

            }catch (MalformedURLException e){
                e.printStackTrace();
                contestPlatform.setVisibility(View.GONE);
            }
        }
    }
}
