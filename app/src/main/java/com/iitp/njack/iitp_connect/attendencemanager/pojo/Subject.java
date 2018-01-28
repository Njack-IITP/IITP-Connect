package com.iitp.njack.iitp_connect.attendencemanager.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by srv_twry on 28/1/18.
 * The POJO class for holding information of a single subject associated with the current semester.
 * Implements Parceable interface and hence can be passed around with Intents.
 */

@Data
@AllArgsConstructor
public class Subject implements Parcelable {
    private String name;
    private String code;
    private int daysAttended;
    private int totalDays;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeInt(this.daysAttended);
        dest.writeInt(this.totalDays);
    }

    protected Subject(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
        this.daysAttended = in.readInt();
        this.totalDays = in.readInt();
    }

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}
