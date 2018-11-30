package com.iitp.njack.iitp_connect.core.youtube.adapters;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.iitp.njack.iitp_connect.BR;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private int layoutId;
    private List<YoutubeVideo> videos;
    private VideoViewModel videoViewModel;

    public VideoAdapter(@LayoutRes int layoutId, VideoViewModel videoViewModel) {
        this.layoutId = layoutId;
        this.videoViewModel = videoViewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    public void setPlaylists(List<YoutubeVideo> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, i, viewGroup, false);
        return new VideoAdapter.VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder playlistViewHolder, int i) {
        playlistViewHolder.bind(videoViewModel, i);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        VideoViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(VideoViewModel videoViewModel, Integer position) {
            binding.setVariable(BR.viewModel, videoViewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
