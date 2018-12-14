package com.iitp.njack.iitp_connect.core.youtube.models;

import com.google.api.client.util.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class YoutubePlaylist {
    String playlist_id;
    String title;
    long itemCount;
    DateTime publishedAt;
}
