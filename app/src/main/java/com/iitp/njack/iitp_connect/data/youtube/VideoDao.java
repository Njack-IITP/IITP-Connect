package com.iitp.njack.iitp_connect.data.youtube;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface VideoDao {
    @Query("SELECT * FROM " + YoutubeVideo.TABLE_NAME + " WHERE playlistId=:id")
    LiveData<List<YoutubeVideo>> getVideosById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addVideos(List<YoutubeVideo> videos);

    @Query("DELETE FROM " + YoutubeVideo.TABLE_NAME)
    void deleteAll();
}
