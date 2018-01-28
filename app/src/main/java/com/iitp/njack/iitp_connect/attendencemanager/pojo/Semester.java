package com.iitp.njack.iitp_connect.attendencemanager.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by srv_twry on 28/1/18.
 * The POJO class for a single semester object.
 * Implements Parceable interface and hence can be passed around with Intents.
 */

@Data
@AllArgsConstructor
public class Semester implements Parcelable {
    private String semName;
    private int semNumber;
    private ArrayList<Subject> subjectArrayList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.semName);
        dest.writeInt(this.semNumber);
        dest.writeTypedList(this.subjectArrayList);
    }

    protected Semester(Parcel in) {
        this.semName = in.readString();
        this.semNumber = in.readInt();
        this.subjectArrayList = in.createTypedArrayList(Subject.CREATOR);
    }

    public static final Parcelable.Creator<Semester> CREATOR = new Parcelable.Creator<Semester>() {
        @Override
        public Semester createFromParcel(Parcel source) {
            return new Semester(source);
        }

        @Override
        public Semester[] newArray(int size) {
            return new Semester[size];
        }
    };
}
