package com.iitp.njack.iitp_connect.core.facebook.feed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.iitp.njack.iitp_connect.common.livedata.SingleEventLiveData;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;
import com.iitp.njack.iitp_connect.data.network.Resource;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.List;

import javax.inject.Inject;

public class FacebookFeedViewModel extends ViewModel {

    private static final String FACEBOOK_POST_VIEW_MODEL = "facebookPostViewModel";

    private final FacebookFeedRepository facebookFeedRepository;
    private final RateLimiter<String> repoListRateLimit;
    private LiveData<Resource<List<FacebookPost>>> facebookPosts;
    private SingleEventLiveData<String> clickAction = new SingleEventLiveData<>();

    @Inject
    public FacebookFeedViewModel(FacebookFeedRepository facebookFeedRepository,
                                 RateLimiter<String> repoListRateLimit) {
        this.facebookFeedRepository = facebookFeedRepository;
        this.repoListRateLimit = repoListRateLimit;
        repoListRateLimit.reset(FACEBOOK_POST_VIEW_MODEL);
    }

    protected LiveData<Resource<List<FacebookPost>>> loadFacebookPosts(boolean reload) {
        if (reload || repoListRateLimit.shouldFetch(FACEBOOK_POST_VIEW_MODEL)) {
            facebookPosts = facebookFeedRepository.fetchFacebookPosts(reload);
            repoListRateLimit.refreshRateLimiter(FACEBOOK_POST_VIEW_MODEL);
        }
        return facebookPosts;
    }

    public void openFacebookPostDetails(String url) {
        clickAction.setValue(url);
    }

    protected LiveData<String> getSelectedFacebookPost() {
        return clickAction;
    }
}
