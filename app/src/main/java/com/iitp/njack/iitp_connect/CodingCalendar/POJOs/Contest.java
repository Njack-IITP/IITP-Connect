package com.iitp.njack.iitp_connect.CodingCalendar.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Model class for a single contest
 */

@AllArgsConstructor
@Getter
public class Contest implements Parcelable {
    private final String title;
    private final String description;
    private final String url;
    private final Date startTime;
    private final Date endTime;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
    }

    protected Contest(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpEndTime = in.readLong();
        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
    }

    public static final Parcelable.Creator<Contest> CREATOR = new Parcelable.Creator<Contest>() {
        @Override
        public Contest createFromParcel(Parcel source) {
            return new Contest(source);
        }

        @Override
        public Contest[] newArray(int size) {
            return new Contest[size];
        }
    };
}
