package com.iitp.njack.iitp_connect.CodingCalendar.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.iitp.njack.iitp_connect.CodingCalendar.Adapters.CodingCalendarAdapter;
import com.iitp.njack.iitp_connect.CodingCalendar.NetworkUtils.DataScraper;
import com.iitp.njack.iitp_connect.CodingCalendar.POJOs.Contest;
import com.iitp.njack.iitp_connect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CodingCalendarHomeActivity extends AppCompatActivity {

    @BindView(R.id.rv_coding_calendar)
    RecyclerView contestRecyclerView;
    private CodingCalendarAdapter codingCalendarAdapter;
    private ArrayList<Contest> contestArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_calendar_home);

        ButterKnife.bind(this);

        /*
        *Fetch the data.
        * TODO: Check for network connectivity before fetching data.
        * */
        DataScraper dataScraper = new DataScraper(this);
        dataScraper.fetchContest();
    }
}
