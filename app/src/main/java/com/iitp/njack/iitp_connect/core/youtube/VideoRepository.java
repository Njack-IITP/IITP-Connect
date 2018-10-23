package com.iitp.njack.iitp_connect.core.youtube;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

public class VideoRepository {
    private List<YoutubeVideo> youtubeVideos = new ArrayList<>();
    private MutableLiveData<List<YoutubeVideo>> videos = new MutableLiveData<>();
    private GoogleAccountCredential googleAccountCredential;
    private String playlist_id = "";

    VideoRepository(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
    }

    MutableLiveData<List<YoutubeVideo>> getVideos() {
        return this.videos;
    }
    void getDataFromAPI(){
        new MakeRequestTask(googleAccountCredential).execute();
        this.youtubeVideos.add(new YoutubeVideo("abcd"));
        videos.setValue(youtubeVideos);
    }

    void setPlaylist_id(String playlist_id) {
        this.playlist_id =playlist_id;
    }

    @Singleton
    private class MakeRequestTask extends AsyncTask<Void, Void, List<YoutubeVideo>> {
        private com.google.api.services.youtube.YouTube mService;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.youtube.YouTube.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("YouTube Data API Android Quickstart")
                    .build();

            youtubeVideos.add(new YoutubeVideo("http1"));

        }


        @Override
        protected List<YoutubeVideo> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                youtubeVideos.add(new YoutubeVideo("error occured"));
                cancel(true);
                return null;
            }
        }


        private List<YoutubeVideo> getDataFromApi() throws IOException {
            List<YoutubeVideo> channelInfo = new ArrayList<>();

                List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();

                YouTube.PlaylistItems.List playlistItemRequest =
                        mService.playlistItems().list("id,contentDetails,snippet");
                playlistItemRequest.setPlaylistId(playlist_id);

                playlistItemRequest.setFields(
                        "items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");

                String nextToken = "";

                do {
                    playlistItemRequest.setPageToken(nextToken);
                    PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                    playlistItemList.addAll(playlistItemResult.getItems());

                    nextToken = playlistItemResult.getNextPageToken();
                } while (nextToken != null);


                for(PlaylistItem playlistItem : playlistItemList){
                     channelInfo.add(new YoutubeVideo(playlistItem.getContentDetails().getVideoId()));
                }
            // ChannelListResponse result = mService.channels().list("snippet,contentDetails,statistics")
            //         .setForUsername("GoogleDevelopers")
            //         .execute();
            // youtubeVideos.add(new YoutubeVideo("http2"));
            // List<Channel> channels = result.getItems();

            // if (channels != null) {

            //     Channel channel = channels.get(0);
            //     HashMap<String, String> parameters = new HashMap<>();
            //     parameters.put("part", "snippet,contentDetails");
            //     parameters.put("channelId", channel.getId());
            //     parameters.put("maxResults", "25");
            //     YouTube.Playlists.List playlistsListByChannelIdRequest = mService.playlists().list(parameters.get("part"));
            //     if (parameters.containsKey("channelId") && !parameters.get("channelId").equals("")) {
            //         playlistsListByChannelIdRequest.setChannelId(parameters.get("channelId"));
            //     }

            //     if (parameters.containsKey("maxResults")) {
            //         playlistsListByChannelIdRequest.setMaxResults(Long.parseLong(parameters.get("maxResults")));
            //     }
            //     PlaylistListResponse playlistListResponse = playlistsListByChannelIdRequest.execute();

            //     List<Playlist> playlist_list = new ArrayList<>();
            //     playlist_list = playlistListResponse.getItems();

            //     if (playlist_list != null) {
            //         for (Playlist playlist1 : playlist_list) {
            //             channelInfo.add(new YoutubeVideo(playlist1.getId()));
            //             youtubeVideos.add(new YoutubeVideo(playlist1.getId()));
            //         }
            //     }
            // }
            youtubeVideos.add(new YoutubeVideo("hkhkkkhh"));
            return channelInfo;
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(List<YoutubeVideo> output) {
            youtubeVideos.add(new YoutubeVideo("http"));
            if (output == null || output.size() == 0) {
                youtubeVideos.add(new YoutubeVideo("hkhk"));
            } else {
                youtubeVideos = output;
            }
            videos.setValue(youtubeVideos);
        }

        @Override
        protected void onCancelled() {

        }
    }
}
