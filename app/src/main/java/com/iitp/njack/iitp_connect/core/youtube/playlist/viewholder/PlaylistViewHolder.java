package com.iitp.njack.iitp_connect.core.youtube.playlist.viewholder;

import android.support.v7.widget.RecyclerView;
import com.iitp.njack.iitp_connect.core.youtube.playlist.PlaylistViewModel;
import com.iitp.njack.iitp_connect.databinding.PlaylistItemBinding;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {
    private final PlaylistItemBinding binding;

    public PlaylistViewHolder(PlaylistItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PlaylistViewModel playlistViewModel, Integer position) {
        binding.setViewModel(playlistViewModel);
        binding.setPosition(position);
        binding.executePendingBindings();
    }
}

