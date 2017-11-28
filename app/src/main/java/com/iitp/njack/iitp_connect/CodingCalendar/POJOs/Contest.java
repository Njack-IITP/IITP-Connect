package com.iitp.njack.iitp_connect.CodingCalendar.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 *Model class for a single contest
 */

public class Contest {
    private String title;
    private String description;
    private String url;

    @SerializedName("start")
    private Date startTime;
    @SerializedName("end")
    private Date endTme;

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTme() {
        return endTme;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTme(Date endTme) {
        this.endTme = endTme;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
