package com.iitp.njack.iitp_connect.core.youtube;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableInt;
import android.view.View;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.youtube.VideoAdapter;
import com.iitp.njack.iitp_connect.core.youtube.VideoRepository;

import java.util.List;

public class VideoViewModel extends ViewModel {
    private MutableLiveData<YoutubeVideo> selected;
    private VideoRepository videorepository;
    private VideoAdapter videoAdapter;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private GoogleAccountCredential googleAccountCredential;

    void init(){

        videorepository = new VideoRepository(googleAccountCredential);
        selected = new MutableLiveData<>();
        videoAdapter = new VideoAdapter(R.layout.view_video,this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }


    void fetchList() {
        videorepository.getDataFromAPI();
    }

    public void onItemClick(Integer index) {
        selected.setValue(getYoutubeVideoAt(index));
    }

    MutableLiveData<List<YoutubeVideo>> getVideos(){
        return videorepository.getVideos();
    }


    void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential){
        this.googleAccountCredential = googleAccountCredential;
    }

    public YoutubeVideo getYoutubeVideoAt(Integer index) {
        if (videorepository.getVideos().getValue() != null &&
                index != null &&
                videorepository.getVideos().getValue().size() > index) {
            return videorepository.getVideos().getValue().get(index);
        }
        return null;
    }

    public VideoAdapter getAdapter(){
        return videoAdapter;
    }

    void setPlaylistsInAdapter(List<YoutubeVideo> playlists){
        this.videoAdapter.setPlaylists(playlists);
        this.videoAdapter.notifyDataSetChanged();
    }

    MutableLiveData<YoutubeVideo> getSelected(){
        return this.selected;
    }


    void setPlaylist_id(String playlist_id) {
        videorepository.setPlaylist_id(playlist_id);
    }
}
