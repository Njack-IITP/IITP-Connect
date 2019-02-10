package com.iitp.njack.iitp_connect.core.youtube.video.viewholder;

import android.support.v7.widget.RecyclerView;

import com.iitp.njack.iitp_connect.core.youtube.video.VideoViewModel;
import com.iitp.njack.iitp_connect.databinding.VideoItemBinding;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    final VideoItemBinding binding;

    public VideoViewHolder(VideoItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(VideoViewModel videoViewModel, Integer position) {
        binding.setViewModel(videoViewModel);
        binding.setPosition(position);
        binding.executePendingBindings();
    }
}
