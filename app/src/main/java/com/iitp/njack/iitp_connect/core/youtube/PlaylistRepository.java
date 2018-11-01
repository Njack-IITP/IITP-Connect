package com.iitp.njack.iitp_connect.core.youtube;

import android.arch.lifecycle.MutableLiveData;

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

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlaylistRepository {
    private String channelName = "GoogleDevelopers";
    public GoogleAccountCredential googleAccountCredential;
    private com.google.api.services.youtube.YouTube mService;
    private MutableLiveData<List<YoutubePlaylist>> playlists = new MutableLiveData<>();

    public void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
    }
    @Inject
    public PlaylistRepository(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
        getDataFromAPI();
    }

    MutableLiveData<List<YoutubePlaylist>> getPlaylists() {
        return this.playlists;
    }

    void getDataFromAPI() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.youtube.YouTube.Builder(
            transport, jsonFactory, googleAccountCredential)
            .setApplicationName("IITP-Connect")
            .build();
        io.reactivex.Observable.just(channelName).map(s -> getFromApi(mService))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<YoutubePlaylist>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<YoutubePlaylist> youtubePlaylists) {
                    playlists.setValue(youtubePlaylists);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });
    }

    private List<YoutubePlaylist> getFromApi(YouTube Service) throws IOException {
        List<YoutubePlaylist> channelInfo = new ArrayList<>();
        ChannelListResponse result = Service.channels().list("snippet,contentDetails,statistics")
            .setForUsername("GoogleDevelopers")
            .execute();

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
            List<Playlist> playlist_list;
            playlist_list = playlistListResponse.getItems();

            if (playlist_list != null) {
                for (Playlist playlist1 : playlist_list) {
                    channelInfo.add(new YoutubePlaylist(playlist1.getId()));
                }
            }
        }
        return channelInfo;
    }
}

