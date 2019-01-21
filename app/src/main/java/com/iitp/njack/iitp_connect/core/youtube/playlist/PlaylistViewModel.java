package com.iitp.njack.iitp_connect.core.youtube.playlist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableInt;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.youtube.YoutubePlaylist;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.List;

import javax.inject.Inject;

public class PlaylistViewModel extends ViewModel {
    @VisibleForTesting
    static final String PLAYLIST_VIEW_MODEL = "playlistViewModel";

    private final RateLimiter<String> repoRateListLimit;
    private final PlaylistRepository playlistRepository;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private MutableLiveData<YoutubePlaylist> selected;
    private PlaylistAdapter playlistAdapter;

    @Inject
    public PlaylistViewModel(PlaylistRepository playlistRepository,
                             RateLimiter<String> repoListRateLimit) {
        this.playlistRepository = playlistRepository;
        this.repoRateListLimit = repoListRateLimit;
        selected = new MutableLiveData<>();
        playlistAdapter = new PlaylistAdapter(R.layout.playlist_item, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);

        repoListRateLimit.reset(PLAYLIST_VIEW_MODEL);
    }

    public void fetchList(String channelId, boolean reload) {
        if(reload || repoRateListLimit.shouldFetch(PLAYLIST_VIEW_MODEL)) {
            playlistRepository.getDataFromAPI(channelId);
            repoRateListLimit.refreshRateLimiter(PLAYLIST_VIEW_MODEL);
        }
    }

    public void onItemClick(Integer index) {
        selected.setValue(getYoutubePlaylistAt(index));
    }

    public MutableLiveData<List<YoutubePlaylist>> getPlaylists() {
        return playlistRepository.getPlaylists();
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

    public void setPlaylistsInAdapter(List<YoutubePlaylist> playlists) {
        this.playlistAdapter.setPlaylists(playlists);
        this.playlistAdapter.notifyDataSetChanged();
    }

    public MutableLiveData<YoutubePlaylist> getSelected() {
        return this.selected;
    }
}
