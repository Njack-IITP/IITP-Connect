package com.iitp.njack.iitp_connect.core.youtube;

import android.arch.lifecycle.MutableLiveData;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.iitp.njack.iitp_connect.data.youtube.YoutubePlaylist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlaylistRepository {
    @Inject
    public YouTube service;
    private String channelName = "GoogleDevelopers";
    private MutableLiveData<List<YoutubePlaylist>> playlists = new MutableLiveData<>();

    public PlaylistRepository() {
        getDataFromAPI();
    }

    public MutableLiveData<List<YoutubePlaylist>> getPlaylists() {
        return this.playlists;
    }

    private void getDataFromAPI() {
        Observable.just(channelName).map(s -> getFromApi(service))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<YoutubePlaylist>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    // nothing here
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
                    //nothing here
                }
            });
    }

    private List<YoutubePlaylist> getFromApi(YouTube Service) throws IOException {
        List<YoutubePlaylist> channelInfo = new ArrayList<>();
        ChannelListResponse result = Service.channels()
            .list("snippet,contentDetails,statistics")
            .setForUsername("GoogleDevelopers")
            .execute();

        List<Channel> channels = result.getItems();
        if (channels != null) {
            Channel channel = channels.get(0);
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,contentDetails");
            parameters.put("channelId", channel.getId());
            parameters.put("maxResults", "50");

            YouTube.Playlists.List playlistsListByChannelIdRequest = service.playlists().list(parameters.get("part"));
            if (parameters.containsKey("channelId") && !parameters.get("channelId").equals("")) {
                playlistsListByChannelIdRequest.setChannelId(parameters.get("channelId"));
            }

            List<Playlist> playlist_list = new ArrayList<>();
            String nextToken = "";

            do {
                playlistsListByChannelIdRequest.setPageToken(nextToken);
                PlaylistListResponse playlistListResponse = playlistsListByChannelIdRequest.execute();
                playlist_list.addAll(playlistListResponse.getItems());
                nextToken = playlistListResponse.getNextPageToken();
            } while (nextToken != null && playlist_list.size() < 25);

            if (playlist_list != null) {
                for (Playlist playlist : playlist_list) {
                    channelInfo.add(new YoutubePlaylist(playlist.getId(),
                        playlist.getSnippet().getTitle(),
                        playlist.getContentDetails().getItemCount(),
                        playlist.getSnippet().getPublishedAt()));
                }
            }
        }
        return channelInfo;
    }
}

