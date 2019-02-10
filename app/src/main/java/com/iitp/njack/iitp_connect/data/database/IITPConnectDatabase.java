package com.iitp.njack.iitp_connect.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.data.contest.ContestDao;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPostDao;
import com.iitp.njack.iitp_connect.data.user.User;
import com.iitp.njack.iitp_connect.data.user.UserDao;
import com.iitp.njack.iitp_connect.data.youtube.PlaylistDao;
import com.iitp.njack.iitp_connect.data.youtube.VideoDao;
import com.iitp.njack.iitp_connect.data.youtube.YoutubePlaylist;
import com.iitp.njack.iitp_connect.data.youtube.YoutubeVideo;

@Database(entities = {Contest.class, User.class, YoutubePlaylist.class, YoutubeVideo.class, FacebookPost.class}, version = 4)
public abstract class IITPConnectDatabase extends RoomDatabase {
    public abstract ContestDao contestDao();

    public abstract UserDao userDao();

    public abstract PlaylistDao playlistDao();

    public abstract VideoDao videoDao();

    public abstract FacebookPostDao facebookPostDao();
}
