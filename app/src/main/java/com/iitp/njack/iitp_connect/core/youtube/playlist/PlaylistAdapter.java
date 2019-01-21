package com.iitp.njack.iitp_connect.core.youtube.playlist;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.youtube.playlist.viewholder.PlaylistViewHolder;
import com.iitp.njack.iitp_connect.data.youtube.YoutubePlaylist;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {
    List<YoutubePlaylist> playlists;
    PlaylistViewModel playlistViewModel;
    private int layoutId;

    public PlaylistAdapter(@LayoutRes int layoutId, PlaylistViewModel playlistViewModel) {
        this.layoutId = layoutId;
        this.playlistViewModel = playlistViewModel;
    }

    public void setPlaylists(List<YoutubePlaylist> newPlaylists) {
        if (playlists == null) {
            playlists = newPlaylists;
            notifyItemRangeInserted(0, newPlaylists.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return playlists.size();
                }

                @Override
                public int getNewListSize() {
                    return newPlaylists.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return playlists.get(oldItemPosition).getPlaylistId() ==
                        newPlaylists.get(newItemPosition).getPlaylistId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return playlists.get(oldItemPosition).equals(newPlaylists.get(newItemPosition));
                }
            });
            playlists = newPlaylists;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PlaylistViewHolder playlistViewHolder =
            new PlaylistViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.playlist_item, viewGroup, false));
        return playlistViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder playlistViewHolder, int i) {
        playlistViewHolder.bind(playlistViewModel, i);
    }

    @Override
    public int getItemCount() {
        return playlists == null ? 0 : playlists.size();
    }
}
