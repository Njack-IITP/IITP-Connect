package com.iitp.njack.iitp_connect.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.utils.type_converters.DateTypeConverter;

@Database(entities = {Contest.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class IITPConnectDatabase extends RoomDatabase {
    public abstract ContestDao contestDao();
}
