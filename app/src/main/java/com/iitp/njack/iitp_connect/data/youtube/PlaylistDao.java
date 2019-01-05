package com.iitp.njack.iitp_connect.data.youtube;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PlaylistDao {
    @Query("SELECT * FROM " + YoutubePlaylist.TABLE_NAME)
    LiveData<List<YoutubePlaylist>> getPlaylists();

    @Query("SELECT * FROM " + YoutubePlaylist.TABLE_NAME + " WHERE channelId=:id")
    LiveData<List<YoutubePlaylist>> getPlaylistsById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPlayists(List<YoutubePlaylist> playlists);

    @Query("DELETE FROM " + YoutubePlaylist.TABLE_NAME)
    void deleteAll();
}
