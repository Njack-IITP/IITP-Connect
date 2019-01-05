package com.iitp.njack.iitp_connect.data.youtube;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.iitp.njack.iitp_connect.data.youtube.YoutubePlaylist.TABLE_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = TABLE_NAME)
public class YoutubePlaylist {
    public static final String TABLE_NAME = "playlists";
    public String channelId;
    @PrimaryKey
    @NonNull
    public String playlistId;
    public String title;
    public String date;
    public Long itemCount;
}
