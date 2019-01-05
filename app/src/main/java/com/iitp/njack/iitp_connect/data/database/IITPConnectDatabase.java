package com.iitp.njack.iitp_connect.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.data.user.UserDao;
import com.iitp.njack.iitp_connect.data.youtube.PlaylistDao;
import com.iitp.njack.iitp_connect.data.youtube.YoutubePlaylist;

@Database(entities = {Contest.class, User.class, YoutubePlaylist.class}, version = 4)
public abstract class IITPConnectDatabase extends RoomDatabase {
    public abstract ContestDao contestDao();

    public abstract UserDao userDao();

    public abstract PlaylistDao playlistDao();
}
