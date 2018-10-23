package com.iitp.njack.iitp_connect.core.youtube;

public class YoutubePlaylist {
    private String playlist_id;

    public YoutubePlaylist(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getPlaylist_id() {
        return playlist_id;
    }
    public YoutubePlaylist() {
    }
}
