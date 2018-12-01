package com.iitp.njack.iitp_connect.core.youtube.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.iitp.njack.iitp_connect.core.youtube.models.YoutubeVideo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoRepository {
    private MutableLiveData<List<YoutubeVideo>> videos = new MutableLiveData<>();
    private GoogleAccountCredential googleAccountCredential;
    private String playlist_id = "";
    private YouTube service;

    @Inject
    public VideoRepository(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
    }

    public void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
    }

    public MutableLiveData<List<YoutubeVideo>> getVideos() {
        return this.videos;
    }

    public void setPlaylistId(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public void getDataFromAPI() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        service = new YouTube.Builder(
            transport, jsonFactory, googleAccountCredential)
            .setApplicationName("IITP-Connect")
            .build();

        io.reactivex.Observable.just(playlist_id).map(s -> getFromApi(service))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<YoutubeVideo>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    //nothing here
                }

                @Override
                public void onNext(List<YoutubeVideo> youtubeVideos) {
                    videos.setValue(youtubeVideos);
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

    private List<YoutubeVideo> getFromApi(YouTube Service) throws IOException {
        List<YoutubeVideo> channelInfo = new ArrayList<>();
        List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
        YouTube.PlaylistItems.List playlistItemRequest =
            Service.playlistItems().list("id,contentDetails,snippet");

        playlistItemRequest.setPlaylistId(playlist_id);
        playlistItemRequest.setFields(
            "items(contentDetails,snippet/title,snippet),nextPageToken,pageInfo");
        String nextToken = "";

        do {
            playlistItemRequest.setPageToken(nextToken);
            PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

            playlistItemList.addAll(playlistItemResult.getItems());

            nextToken = playlistItemResult.getNextPageToken();
        } while (nextToken != null);

        for (PlaylistItem playlistItem : playlistItemList) {
            channelInfo.add(new YoutubeVideo(playlistItem.getContentDetails().getVideoId()
                , playlistItem.getSnippet().getTitle()
                , playlistItem.getSnippet().getPublishedAt()
                , playlistItem.getSnippet().getThumbnails().getStandard().getUrl()));
        }
        return channelInfo;
    }
}
