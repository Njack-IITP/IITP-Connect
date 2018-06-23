package com.iitp.njack.iitp_connect.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.data.user.UserDao;

@Database(entities = {Contest.class, User.class}, version = 2)
public abstract class IITPConnectDatabase extends RoomDatabase {
    public abstract ContestDao contestDao();

    public abstract UserDao userDao();
}
