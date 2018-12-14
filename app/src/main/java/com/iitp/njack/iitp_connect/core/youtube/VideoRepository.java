package com.iitp.njack.iitp_connect.core.youtube;

import android.arch.lifecycle.MutableLiveData;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.iitp.njack.iitp_connect.data.youtube.YoutubeVideo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoRepository {
    @Inject
    public YouTube service;
    private MutableLiveData<List<YoutubeVideo>> videos = new MutableLiveData<>();
    private String playlist_id = "";

    public VideoRepository() {
        getDataFromAPI();
    }

    public MutableLiveData<List<YoutubeVideo>> getVideos() {
        return this.videos;
    }

    public void setPlaylistId(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    private void getDataFromAPI() {
        Observable.just(playlist_id).map(s -> getFromApi(service))
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
            Service.playlistItems()
                .list("id,contentDetails,snippet");

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
