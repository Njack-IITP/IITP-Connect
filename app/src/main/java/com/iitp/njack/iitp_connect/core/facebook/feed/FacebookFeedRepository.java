package com.iitp.njack.iitp_connect.core.fb.feed;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.iitp.njack.iitp_connect.AppExecutors;
import com.iitp.njack.iitp_connect.data.facebook.FacebookApi;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPostDao;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPostsWrapper;
import com.iitp.njack.iitp_connect.data.network.ApiResponse;
import com.iitp.njack.iitp_connect.data.network.NetworkBoundResource;
import com.iitp.njack.iitp_connect.data.network.Resource;
import com.iitp.njack.iitp_connect.utils.RateLimiter;

import java.util.List;

import javax.inject.Inject;

import static com.iitp.njack.iitp_connect.common.Constants.ACCESS_TOKEN;
import static com.iitp.njack.iitp_connect.common.Constants.FIELDS;

public class FacebookFeedRepository {

    private static final String FACEBOOKPOSTS = "facebookPosts";

    private final FacebookPostDao facebookPostDao;
    private final FacebookApi facebookApi;
    private final AppExecutors appExecutors;
    private final RateLimiter<String> repoListRateLimit;

    @Inject
    public FacebookFeedRepository(FacebookPostDao facebookPostDao,
                                  FacebookApi facebookApi,
                                  AppExecutors appExecutors,
                                  RateLimiter<String> repoListRateLimit) {
        this.facebookPostDao = facebookPostDao;
        this.facebookApi = facebookApi;
        this.appExecutors = appExecutors;
        this.repoListRateLimit = repoListRateLimit;
    }

    public LiveData<Resource<List<FacebookPost>>> fetchFacebookPosts(boolean reload) {
        return new NetworkBoundResource<List<FacebookPost>, FacebookPostsWrapper>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull FacebookPostsWrapper item) {
                facebookPostDao.deleteAll();
                List<FacebookPost> contests = item.getFacebookPosts();
                facebookPostDao.addFacebookPosts(contests);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<FacebookPost> data) {
                return data == null || data.isEmpty() || reload || repoListRateLimit.shouldFetchAndRefresh(FACEBOOKPOSTS);
            }

            @NonNull
            @Override
            protected LiveData<List<FacebookPost>> loadFromDb() {
                return facebookPostDao.getFacebookPosts();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<FacebookPostsWrapper>> createCall() {
                return facebookApi.getFacebookPosts(FIELDS,ACCESS_TOKEN);
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(FACEBOOKPOSTS);
            }
        }.asLiveData();
    }
}

