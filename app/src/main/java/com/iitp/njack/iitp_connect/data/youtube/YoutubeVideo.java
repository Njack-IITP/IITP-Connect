package com.iitp.njack.iitp_connect.data.youtube;

import com.google.api.client.util.DateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YoutubeVideo {
    private String video_id;
    private String title;
    private DateTime publishedAt;
    private String imageUrl;
}
