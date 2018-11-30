package com.iitp.njack.iitp_connect.core.youtube;

import com.google.api.client.util.DateTime;

public class YoutubeVideo {
    private String video_id;
    private String title;
    private DateTime publishedAt;
    private String imageUrl;

    public YoutubeVideo(String video_id, String title, DateTime publishedAt,String thumbnail) {
        this.video_id = video_id;
        this.title = title;
        this.publishedAt = publishedAt;
        this.imageUrl = thumbnail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedAt() {
        return publishedAt.toString().substring(0, 10);
    }

    public String getVideoId() {
        return video_id;
    }

}
