package com.iitp.njack.iitp_connect.data.facebook;

import android.arch.lifecycle.LiveData;

import com.iitp.njack.iitp_connect.data.network.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FacebookApi {
    @GET("/v3.2/studconnect/feed")
    LiveData<ApiResponse<FacebookPostListWrapper>> getFacebookPosts(@Query("fields") String fields, @Query("access_token") String api);
}

