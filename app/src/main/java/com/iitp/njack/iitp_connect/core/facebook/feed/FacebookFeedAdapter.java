package com.iitp.njack.iitp_connect.core.facebook.feed;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;

import java.util.List;

public class FacebookFeedAdapter extends RecyclerView.Adapter<FacebookFeedViewHolder> {
    private final FacebookFeedViewModel facebookFeedViewModel;
    private List<FacebookPost> facebookPosts;

    public FacebookFeedAdapter(FacebookFeedViewModel facebookFeedViewModel) {
        this.facebookFeedViewModel = facebookFeedViewModel;
    }

    @NonNull
    @Override
    public FacebookFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        FacebookFeedViewHolder facebookFeedViewHolder =
            new FacebookFeedViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.facebook_post_item, viewGroup, false),viewGroup.getContext());

        facebookFeedViewHolder.setClickAction(facebookFeedViewModel::openFacebookPostDetails);
        return facebookFeedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FacebookFeedViewHolder facebookFeedViewHolder, int position) {
        facebookFeedViewHolder.bind(facebookPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return facebookPosts != null ? facebookPosts.size() : 0;
    }

    protected void setFacebookPosts(final List<FacebookPost> newFacebookPosts) {
        if (facebookPosts == null) {
            facebookPosts = newFacebookPosts;
            notifyItemRangeInserted(0, newFacebookPosts.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return facebookPosts.size();
                }

                @Override
                public int getNewListSize() {
                    return newFacebookPosts.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return facebookPosts.get(oldItemPosition).getId() ==
                        newFacebookPosts.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return facebookPosts.get(oldItemPosition).equals(newFacebookPosts.get(newItemPosition));
                }
            });
            facebookPosts = newFacebookPosts;
            result.dispatchUpdatesTo(this);
        }
    }
}
