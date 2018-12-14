package com.iitp.njack.iitp_connect.data.youtube;

import com.google.api.client.util.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class YoutubePlaylist {
    private String playlist_id;
    private String title;
    private long itemCount;
    private DateTime publishedAt;
}
