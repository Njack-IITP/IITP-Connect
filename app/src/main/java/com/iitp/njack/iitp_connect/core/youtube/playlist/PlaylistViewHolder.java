package com.iitp.njack.iitp_connect.core.youtube.playlist;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.iitp.njack.iitp_connect.BR;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public PlaylistViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(PlaylistViewModel playlistViewModel, Integer position) {
        binding.setVariable(BR.viewModel, playlistViewModel);
        binding.setVariable(BR.position, position);
        binding.executePendingBindings();
    }
}
