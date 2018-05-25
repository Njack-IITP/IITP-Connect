package com.iitp.njack.iitp_connect.data.database.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.iitp.njack.iitp_connect.data.contest.Contest;

@Database(entities = {Contest.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class IITPConnectDatabase extends RoomDatabase {

}
