package com.iitp.njack.iitp_connect.data.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class YoutubeApi {
    public YouTube Service;

    @Inject
    YoutubeApi(YouTube service) {
        this.Service = service;
    }

    public List<YoutubePlaylist> getPlaylistsFromApi(String channelId) throws IOException {
        List<YoutubePlaylist> playlistsInfo = new ArrayList<>();

        YouTube.Playlists.List playlistsListByChannelIdRequest = Service.playlists()
            .list("snippet,contentDetails");
        playlistsListByChannelIdRequest.setChannelId(channelId);

        List<Playlist> playlist_list = new ArrayList<>();
        String nextToken = "";

        do {
            playlistsListByChannelIdRequest.setPageToken(nextToken);
            PlaylistListResponse playlistListResponse = playlistsListByChannelIdRequest.execute();
            playlist_list.addAll(playlistListResponse.getItems());
            nextToken = playlistListResponse.getNextPageToken();
        } while (nextToken != null && playlist_list.size() < 50);

        if (playlist_list != null) {
            for (Playlist playlist1 : playlist_list) {
                playlistsInfo.add(new YoutubePlaylist(channelId,
                    playlist1.getId(),
                    playlist1.getSnippet().getTitle(),
                    playlist1.getSnippet().getPublishedAt().toStringRfc3339(),
                    playlist1.getContentDetails().getItemCount(),
                    playlist1.getSnippet().getThumbnails().getDefault().getUrl()));
            }
        }
        return playlistsInfo;
    }

    public List<YoutubeVideo> getVideosFromApi(String playlistId) throws IOException {
        List<YoutubeVideo> videos = new ArrayList<>();
        List<PlaylistItem> playlistItemList = new ArrayList<>();
        YouTube.PlaylistItems.List playlistItemRequest =
            Service.playlistItems().list("id,contentDetails,snippet");

        playlistItemRequest.setPlaylistId(playlistId);
        String nextToken = "";

        do {
            playlistItemRequest.setPageToken(nextToken);
            PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

            playlistItemList.addAll(playlistItemResult.getItems());

            nextToken = playlistItemResult.getNextPageToken();
        } while (nextToken != null);

        for (PlaylistItem playlistItem : playlistItemList) {
            videos.add(new YoutubeVideo(playlistItem.getContentDetails().getVideoId()
                , playlistId
                , playlistItem.getSnippet().getTitle()
                , playlistItem.getSnippet().getPublishedAt().toStringRfc3339()
                , playlistItem.getSnippet().getThumbnails().getDefault().getUrl()));
        }
        return videos;
    }
}
