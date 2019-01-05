package com.iitp.njack.iitp_connect.core.youtube.playlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.iitp.njack.iitp_connect.data.youtube.PlaylistDao;
import com.iitp.njack.iitp_connect.data.youtube.YoutubeApi;
import com.iitp.njack.iitp_connect.data.youtube.YoutubePlaylist;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlaylistRepository {
    private static final String PLAYLISTS = "playlists";
    private final PlaylistDao playlistDao;
    private final RateLimiter<String> repoListRateLimit;
    private YoutubeApi youtubeApi;
    private String channelId;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<YoutubePlaylist>> playlists = new MutableLiveData<>();

    @Inject
    public PlaylistRepository(PlaylistDao playlistDao,
                              YoutubeApi youtubeApi,
                              RateLimiter<String> repoListRateLimit) {
        this.playlistDao = playlistDao;
        this.youtubeApi = youtubeApi;
        this.repoListRateLimit = repoListRateLimit;
    }

    public MutableLiveData<List<YoutubePlaylist>> getPlaylists() {
        return this.playlists;
    }

    public void getDataFromAPI(String channelId) {
        this.channelId = channelId;
        loadFromDb();
    }

    private void saveCallResult(List<YoutubePlaylist> playlist) {
        compositeDisposable.add(
            Single.just(playlistDao)
                .subscribeOn(Schedulers.io())
                .subscribe(playlistDao1 -> {
                    playlistDao1.deleteAll();
                    playlistDao1.addPlayists(playlist);
                }));
    }

    private void loadFromDb() {
        Observable.just(playlistDao).map(playlistDao1 -> playlistDao1.getPlaylistsById(channelId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<LiveData<List<YoutubePlaylist>>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(LiveData<List<YoutubePlaylist>> listLiveData) {
                    if (listLiveData.getValue() != null) {
                        playlists.setValue(listLiveData.getValue());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    //nothing here
                }

                @Override
                public void onComplete() {
                    if (shouldFetch()) {
                        fetchPlaylists();
                    }
                }
            });
    }

    private boolean shouldFetch() {
        return playlists.getValue() == null || playlists.getValue().isEmpty() || repoListRateLimit.shouldFetchAndRefresh(PLAYLISTS);
    }

    private void fetchPlaylists() {
        Observable.just(channelId).map(s -> youtubeApi.getPlaylistsFromApi(s))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<YoutubePlaylist>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(List<YoutubePlaylist> youtubePlaylists) {
                    playlists.setValue(youtubePlaylists);
                    saveCallResult(youtubePlaylists);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    repoListRateLimit.reset(PLAYLISTS);
                }

                @Override
                public void onComplete() {
                    //nothing here
                }
            });
    }
}

