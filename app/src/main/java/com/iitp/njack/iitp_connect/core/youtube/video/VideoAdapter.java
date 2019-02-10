package com.iitp.njack.iitp_connect.core.youtube.video;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.iitp.njack.iitp_connect.BR;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.youtube.playlist.viewholder.PlaylistViewHolder;
import com.iitp.njack.iitp_connect.core.youtube.video.viewholder.VideoViewHolder;
import com.iitp.njack.iitp_connect.data.youtube.YoutubeVideo;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private List<YoutubeVideo> videos;
    private VideoViewModel videoViewModel;

    public VideoAdapter(VideoViewModel videoViewModel) {
        this.videoViewModel = videoViewModel;
    }

    public void setPlaylists(List<YoutubeVideo> newVideos) {
        if (videos == null) {
            videos = newVideos;
            notifyItemRangeInserted(0, newVideos.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return videos.size();
                }

                @Override
                public int getNewListSize() {
                    return newVideos.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return videos.get(oldItemPosition).getPlaylistId() ==
                        newVideos.get(newItemPosition).getPlaylistId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return videos.get(oldItemPosition).equals(newVideos.get(newItemPosition));
                }
            });
            videos = newVideos;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
            R.layout.video_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        videoViewHolder.bind(videoViewModel, i);
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }
}
