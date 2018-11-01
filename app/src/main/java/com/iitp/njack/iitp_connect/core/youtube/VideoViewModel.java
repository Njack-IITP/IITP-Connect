package com.iitp.njack.iitp_connect.core.youtube;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableInt;
import android.view.View;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.iitp.njack.iitp_connect.R;

import java.util.List;

import javax.inject.Inject;

public class VideoViewModel extends ViewModel {
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private MutableLiveData<YoutubeVideo> selected;
    private VideoRepository videorepository;
    private VideoAdapter videoAdapter;
    @Inject
    public VideoViewModel(VideoRepository videorepository){
        this.videorepository = videorepository;
        selected = new MutableLiveData<>();
        videoAdapter = new VideoAdapter(R.layout.view_video, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    void fetchList() {
        videorepository.getDataFromAPI();
    }

    public void onItemClick(Integer index) {
        selected.setValue(getYoutubeVideoAt(index));
    }

    MutableLiveData<List<YoutubeVideo>> getVideos() {
        return videorepository.getVideos();
    }

    void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential) {
        videorepository.setGoogleAccountCredential(googleAccountCredential);
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

    void setPlaylistsInAdapter(List<YoutubeVideo> playlists) {
        this.videoAdapter.setPlaylists(playlists);
        this.videoAdapter.notifyDataSetChanged();
    }

    MutableLiveData<YoutubeVideo> getSelected() {
        return this.selected;
    }

    void setPlaylist_id(String playlist_id) {
        videorepository.setPlaylist_id(playlist_id);
    }
}
