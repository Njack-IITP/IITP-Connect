package com.iitp.njack.iitp_connect.core.youtube;

import com.google.api.client.util.DateTime;

public class YoutubePlaylist {
    DateTime publishedAt;
    private String playlist_id;
    private String title;
    private long itemCount;
    private String date;

    public YoutubePlaylist(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public YoutubePlaylist(String playlist_id, String title, long itemCount, DateTime publishedAt) {
        this.playlist_id = playlist_id;
        this.title = title;
        this.itemCount = itemCount;
        this.publishedAt = publishedAt;
        this.date = publishedAt.toStringRfc3339();
    }

    public String getDate() {
        return date;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public String getPlaylistId() {
        return playlist_id;
    }

    public String getTitle() {
        return title;
    }

    public long getItemCount() {
        return itemCount;
    }
}
