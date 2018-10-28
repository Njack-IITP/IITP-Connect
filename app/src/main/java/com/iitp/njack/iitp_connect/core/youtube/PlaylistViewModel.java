package com.iitp.njack.iitp_connect.core.youtube;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableInt;
import android.view.View;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.iitp.njack.iitp_connect.R;

import java.util.List;

import javax.inject.Inject;

public class PlaylistViewModel extends ViewModel {
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private MutableLiveData<YoutubePlaylist> selected;
    private PlaylistRepository playlistRepository;
    private PlaylistAdapter playlistAdapter;
    private GoogleAccountCredential googleAccountCredential;
    @Inject
    public PlaylistViewModel(){
        playlistRepository = new PlaylistRepository(googleAccountCredential);
        selected = new MutableLiveData<>();
        playlistAdapter = new PlaylistAdapter(R.layout.view_playlist, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    void fetchList() {
        playlistRepository.getDataFromAPI();
    }

    public void onItemClick(Integer index) {
        selected.setValue(getYoutubePlaylistAt(index));
    }

    MutableLiveData<List<YoutubePlaylist>> getPlaylists() {
        return playlistRepository.getPlaylists();
    }

    void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential) {
        playlistRepository.setGoogleAccountCredential(googleAccountCredential);
    }

    public YoutubePlaylist getYoutubePlaylistAt(Integer index) {
        if (playlistRepository.getPlaylists().getValue() != null &&
            index != null &&
            playlistRepository.getPlaylists().getValue().size() > index) {
            return playlistRepository.getPlaylists().getValue().get(index);
        }
        return null;
    }

    public PlaylistAdapter getAdapter() {
        return playlistAdapter;
    }

    void setPlaylistsInAdapter(List<YoutubePlaylist> playlists) {
        this.playlistAdapter.setPlaylists(playlists);
        this.playlistAdapter.notifyDataSetChanged();
    }

    MutableLiveData<YoutubePlaylist> getSelected() {
        return this.selected;
    }
}
