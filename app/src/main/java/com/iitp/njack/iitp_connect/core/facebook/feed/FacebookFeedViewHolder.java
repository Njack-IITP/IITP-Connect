package com.iitp.njack.iitp_connect.core.facebook.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iitp.njack.iitp_connect.common.Pipe;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;
import com.iitp.njack.iitp_connect.databinding.FacebookPostItemBinding;

public class FacebookFeedViewHolder extends RecyclerView.ViewHolder {
    private final FacebookPostItemBinding binding;
    private FacebookPost facebookPost;
    private Pipe<Long> clickAction;
    private Context context;
    private Glide glide;

    public FacebookFeedViewHolder(FacebookPostItemBinding binding, Context context, Glide glide) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
        this.glide = glide;

        binding.getRoot().setOnClickListener(view -> {
            if (clickAction != null) {
                clickAction.push(facebookPost.getId());
            }
        });
    }

    public void bind(FacebookPost facebookPost) {
        this.facebookPost = facebookPost;
        binding.setFacebookPost(facebookPost);
        if (facebookPost.getPostpic() != null) {
            binding.imgViewPostPic.setVisibility(View.VISIBLE);
            glide.with(context)
                .load(facebookPost.getPostpic())
                .apply(new RequestOptions()
                    .dontAnimate())
                .into(binding.imgViewPostPic);
        } else {
            binding.imgViewPostPic.setVisibility(View.GONE);
        }
        glide.with(context)
            .load(facebookPost.getPropic())
            .apply(new RequestOptions()
                .dontAnimate())
            .into(binding.imgViewProPic);
        binding.executePendingBindings();
    }

    public void setClickAction(Pipe<Long> clickAction) {
        this.clickAction = clickAction;
    }
}
