package com.iitp.njack.iitp_connect.core.codingcalendar.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.common.utils.StringUtils;
import com.iitp.njack.iitp_connect.data.models.Contest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * The Activity to show the details of a particular contest.
 * */

public class ContestDetailsActivity extends AppCompatActivity {

    private Contest mContest;

    ImageView contestCoverImageView;
    TextView contestTitle;
    TextView contestDescription;
    TextView contestStartTime;
    TextView contestDuration;
    Button contestLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_details);

        contestCoverImageView = (ImageView) findViewById(R.id.iv_contest_poster_image);
        contestTitle = (TextView) findViewById(R.id.tv_contest_details_title);
        contestDescription = (TextView) findViewById(R.id.tv_contest_details_description);
        contestStartTime = (TextView) findViewById(R.id.tv_contest_details_start_time);
        contestDuration = (TextView) findViewById(R.id.tv_contest_details_duration);
        contestLink = (Button) findViewById(R.id.button_contest_details_button);

        mContest = getIntent().getParcelableExtra(CodingCalendarHomeActivity.INTENT_EXTRA);
        setDataToViews();
    }

    void setDataToViews() {
        contestTitle.setText(mContest.getTitle());
        contestDescription.setText(mContest.getDescription());

        contestCoverImageView.setImageResource(getCoverImage(mContest.getUrl()));

        SpannableString contestDetailsStartTime = StringUtils.getStartTimeTextDetailsFragment(mContest.getStartTime());
        contestStartTime.setText(contestDetailsStartTime);
        String duration = getString(R.string.duration_approximately) + " " + getContestDuration(mContest.getStartTime(), mContest.getEndTime()) + " " + getString(R.string.hours);
        contestDuration.setText(duration);


        contestLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mContest.getUrl()));
                startActivity(intent);
            }
        });
    }

    private long getContestDuration(Date start, Date end) {
        long startTime = start.getTime();
        long endTime = end.getTime();

        long difference = endTime - startTime;
        difference = TimeUnit.MILLISECONDS.toHours(difference);        // returning the difference in hours.
        return difference;
    }

    //Helper method to get the Cover image of the Contest
    public static int getCoverImage(String url) {
        URL urlPlatform;
        int returnId;
        try {
            urlPlatform = new URL(url);
            String platformString = urlPlatform.getHost();

            switch (platformString) {
                case "www.topcoder.com":
                    returnId = R.drawable.topcoder_cover;
                    break;
                case "www.codechef.com":
                    returnId = R.drawable.codechef_cover;
                    break;
                case "www.hackerrank.com":
                    returnId = R.drawable.hackerrank_cover;
                    break;
                case "www.hackerearth.com":
                    returnId = R.drawable.hackerearth_cover;
                    break;
                case "codeforces.com":
                    returnId = R.drawable.codeforces_cover;
                    break;
                default:
                    returnId = R.drawable.ic_code;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            returnId = R.drawable.ic_code;
        }
        return returnId;
    }
}
