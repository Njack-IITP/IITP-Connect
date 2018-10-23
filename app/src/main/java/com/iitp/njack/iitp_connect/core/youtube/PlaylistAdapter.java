package com.iitp.njack.iitp_connect.core.youtube;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iitp.njack.iitp_connect.BR;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private int layoutId;

    List<YoutubePlaylist> playlists;

    PlaylistViewModel playlistViewModel;
    public PlaylistAdapter(@LayoutRes int layoutId,PlaylistViewModel playlistViewModel){
        this.layoutId = layoutId;
        this.playlistViewModel = playlistViewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    public void setPlaylists(List<YoutubePlaylist> playlists) {
        this.playlists = playlists;
    }


    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater,i,viewGroup,false);
        return new PlaylistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder playlistViewHolder, int i) {
        playlistViewHolder.bind(playlistViewModel,i);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    @Override
    public int getItemCount() {
        return playlists==null?0:playlists.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        public PlaylistViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
        void bind(PlaylistViewModel playlistViewModel,Integer position){
            binding.setVariable(BR.viewModel,playlistViewModel);
            binding.setVariable(BR.position,position);
            binding.executePendingBindings();
        }

    }
}
