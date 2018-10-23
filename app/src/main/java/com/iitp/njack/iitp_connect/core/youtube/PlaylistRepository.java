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
import com.google.api.services.youtube.model.PlaylistListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

public class PlaylistRepository {
    private List<YoutubePlaylist> youtubePlaylists = new ArrayList<>();
    private GoogleAccountCredential googleAccountCredential;
    private MutableLiveData<List<YoutubePlaylist>> playlists = new MutableLiveData<>();


    PlaylistRepository(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
        getDataFromAPI();
    }

    MutableLiveData<List<YoutubePlaylist>> getPlaylists() {
        return this.playlists;
    }

    void getDataFromAPI() {
        new MakeRequestTask(googleAccountCredential).execute();
        this.youtubePlaylists.add(new YoutubePlaylist("abcd"));

        playlists.setValue(youtubePlaylists);
    }


    @Singleton
    private class MakeRequestTask extends AsyncTask<Void, Void, List<YoutubePlaylist>> {
        private com.google.api.services.youtube.YouTube mService;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.youtube.YouTube.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("YouTube Data API Android Quickstart")
                    .build();

            youtubePlaylists.add(new YoutubePlaylist("http1"));

        }


        @Override
        protected List<YoutubePlaylist> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                youtubePlaylists.add(new YoutubePlaylist("error occured"));
                cancel(true);
                return null;
            }
        }


        private List<YoutubePlaylist> getDataFromApi() throws IOException {
            List<YoutubePlaylist> channelInfo = new ArrayList<>();

            ChannelListResponse result = mService.channels().list("snippet,contentDetails,statistics")
                    .setForUsername("GoogleDevelopers")
                    .execute();
            youtubePlaylists.add(new YoutubePlaylist("http2"));
            List<Channel> channels = result.getItems();

            if (channels != null) {

                Channel channel = channels.get(0);
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("part", "snippet,contentDetails");
                parameters.put("channelId", channel.getId());
                parameters.put("maxResults", "25");
                YouTube.Playlists.List playlistsListByChannelIdRequest = mService.playlists().list(parameters.get("part"));
                if (parameters.containsKey("channelId") && !parameters.get("channelId").equals("")) {
                    playlistsListByChannelIdRequest.setChannelId(parameters.get("channelId"));
                }

                if (parameters.containsKey("maxResults")) {
                    playlistsListByChannelIdRequest.setMaxResults(Long.parseLong(parameters.get("maxResults")));
                }
                PlaylistListResponse playlistListResponse = playlistsListByChannelIdRequest.execute();

                List<Playlist> playlist_list = new ArrayList<>();
                playlist_list = playlistListResponse.getItems();

                if (playlist_list != null) {
                    for (Playlist playlist1 : playlist_list) {
                        channelInfo.add(new YoutubePlaylist(playlist1.getId()));
                        youtubePlaylists.add(new YoutubePlaylist(playlist1.getId()));
                    }
                }
            }
            youtubePlaylists.add(new YoutubePlaylist("hkhkkkhh"));
            return channelInfo;
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(List<YoutubePlaylist> output) {
            youtubePlaylists.add(new YoutubePlaylist("http"));
            if (output == null || output.size() == 0) {
                youtubePlaylists.add(new YoutubePlaylist("hkhk"));
            } else {
                youtubePlaylists = output;
            }
            playlists.setValue(youtubePlaylists);
        }

        @Override
        protected void onCancelled() {

        }
    }
}
