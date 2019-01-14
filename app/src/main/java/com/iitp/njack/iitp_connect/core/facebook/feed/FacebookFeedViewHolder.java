package com.iitp.njack.iitp_connect.core.facebook.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iitp.njack.iitp_connect.common.Pipe;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;
import com.iitp.njack.iitp_connect.databinding.FacebookPostItemBinding;
import com.iitp.njack.iitp_connect.ui.GlideApp;

public class FacebookFeedViewHolder extends RecyclerView.ViewHolder {
    private final FacebookPostItemBinding binding;
    private FacebookPost facebookPost;
    private Pipe<String> clickAction;
    private Context context;

    public FacebookFeedViewHolder(FacebookPostItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;

        binding.getRoot().setOnClickListener(view -> {
            if (clickAction != null) {
                clickAction.push(facebookPost.url);
            }
        });
    }

    public void bind(FacebookPost facebookPost) {
        this.facebookPost = facebookPost;
        binding.setFacebookPost(facebookPost);
        if (facebookPost.getPostpic() != null) {
            binding.imgViewPostPic.setVisibility(View.VISIBLE);
            GlideApp.with(context)
                .load(facebookPost.getPostpic())
                .dontAnimate()
                .into(binding.imgViewPostPic);
        } else {
            binding.imgViewPostPic.setVisibility(View.GONE);
        }
        GlideApp.with(context)
            .load(facebookPost.getPropic())
            .dontAnimate()
            .into(binding.imgViewProPic);
        binding.executePendingBindings();
    }

    public void setClickAction(Pipe<String> clickAction) {
        this.clickAction = clickAction;
    }
}
