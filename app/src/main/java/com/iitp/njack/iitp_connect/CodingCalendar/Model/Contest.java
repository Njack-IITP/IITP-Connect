package com.iitp.njack.iitp_connect.CodingCalendar.Model;

import java.util.Date;

/**
 * Created by srv_twry on 20/8/17.
 * The POJO for a single contest.
 */

public class Contest {
    private final String title;
    private final String description;
    private final String url;
    private final Date startTime;
    private final Date endTime;

    public Contest(String title,String description,String url,Date startTime,Date endTime){
        this.title=title;
        this.description= description;
        this.url=url;
        this.startTime=startTime;
        this.endTime=endTime;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
