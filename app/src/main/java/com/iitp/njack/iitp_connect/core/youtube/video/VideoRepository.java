package com.iitp.njack.iitp_connect.core.youtube.video;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.iitp.njack.iitp_connect.data.youtube.VideoDao;
import com.iitp.njack.iitp_connect.data.youtube.YoutubeApi;
import com.iitp.njack.iitp_connect.data.youtube.YoutubeVideo;
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
import timber.log.Timber;

public class VideoRepository {
    private static final String VIDEOS = "videos";
    private final RateLimiter<String> repoListRateLimit;
    private YoutubeApi youtubeApi;
    private VideoDao videoDao;
    private String playlistId;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<YoutubeVideo>> videos = new MutableLiveData<>();

    @Inject
    public VideoRepository(VideoDao videoDao,
                           YoutubeApi youtubeApi,
                           RateLimiter<String> repoListRateLimit) {
        this.videoDao = videoDao;
        this.youtubeApi = youtubeApi;
        this.repoListRateLimit = repoListRateLimit;
    }

    public MutableLiveData<List<YoutubeVideo>> getVideos() {
        return this.videos;
    }

    public void getDataFromAPI(String playlistId) {
        this.playlistId = playlistId;
        loadFromDb();
    }

    private void saveCallResult(List<YoutubeVideo> playlist) {
        compositeDisposable.add(
            Single.just(videoDao)
                .subscribeOn(Schedulers.io())
                .subscribe(videoDao1 -> {
                    videoDao1.deleteAll();
                    videoDao1.addVideos(playlist);
                }));
    }

    private void loadFromDb() {
        Observable.just(videoDao).map(videoDao1 -> videoDao1.getVideosById(playlistId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<LiveData<List<YoutubeVideo>>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(LiveData<List<YoutubeVideo>> youtubeVideoLiveData) {
                    if (youtubeVideoLiveData.getValue() != null) {
                        videos.setValue(youtubeVideoLiveData.getValue());
                    }
                }
                @Override
                public void onError(Throwable e) {
                    Timber.e(e);
                }

                @Override
                public void onComplete() {
                    if (shouldFetch()) {
                        fetchVideos();
                    }
                }
                });
    }

    private boolean shouldFetch() {
        return (videos.getValue() == null || videos.getValue().isEmpty() || repoListRateLimit.shouldFetchAndRefresh(VIDEOS));
    }

    private void fetchVideos() {
        Observable.just(playlistId).map(s -> youtubeApi.getVideosFromApi(s))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<YoutubeVideo>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(List<YoutubeVideo> youtubeVideos) {
                    videos.setValue(youtubeVideos);
                    saveCallResult(youtubeVideos);
                }

                @Override
                public void onError(Throwable e) {
                    Timber.e(e);
                    repoListRateLimit.reset(VIDEOS);
                }

                @Override
                public void onComplete() {
                    //nothing here
                }
            });
    }
}
