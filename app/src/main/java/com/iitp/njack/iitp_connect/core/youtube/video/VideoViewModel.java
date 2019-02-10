package com.iitp.njack.iitp_connect.core.youtube.video;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableInt;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import com.iitp.njack.iitp_connect.data.youtube.YoutubeVideo;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.List;

import javax.inject.Inject;

public class VideoViewModel extends ViewModel {
    @VisibleForTesting
    static final String VIDEO_VIEW_MODEL = "videoViewModel";

    private final RateLimiter<String> repoRateListLimit;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private MutableLiveData<YoutubeVideo> selected;
    private VideoRepository videorepository;
    private VideoAdapter videoAdapter;

    @Inject
    public VideoViewModel(VideoRepository videorepository,
                          RateLimiter<String> repoRateListLimit) {
        this.videorepository = videorepository;
        selected = new MutableLiveData<>();
        videoAdapter = new VideoAdapter(this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        this.repoRateListLimit = repoRateListLimit;

        repoRateListLimit.reset(VIDEO_VIEW_MODEL);
    }

    public void fetchList(String playlistId, boolean reload) {
        if (reload || repoRateListLimit.shouldFetch(VIDEO_VIEW_MODEL)) {
            videorepository.getDataFromAPI(playlistId);
            repoRateListLimit.refreshRateLimiter(VIDEO_VIEW_MODEL);
        }
    }

    public void onItemClick(Integer index) {
        selected.setValue(getYoutubeVideoAt(index));
    }

    public MutableLiveData<List<YoutubeVideo>> getVideos() {
        return videorepository.getVideos();
    }

    public YoutubeVideo getYoutubeVideoAt(Integer index) {
        if (videorepository.getVideos().getValue() != null &&
            index != null &&
            videorepository.getVideos().getValue().size() > index) {
            return videorepository.getVideos().getValue().get(index);
        }
        return null;
    }

    public VideoAdapter getAdapter() {
        return videoAdapter;
    }

    public void setPlaylistsInAdapter(List<YoutubeVideo> playlists) {
        this.videoAdapter.setPlaylists(playlists);
        this.videoAdapter.notifyDataSetChanged();
    }

    public MutableLiveData<YoutubeVideo> getSelected() {
        return this.selected;
    }
}
