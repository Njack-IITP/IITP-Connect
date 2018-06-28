package com.iitp.njack.iitp_connect.data.contest;

import android.arch.lifecycle.LiveData;

import com.iitp.njack.iitp_connect.data.network.ApiResponse;

import retrofit2.http.GET;

public interface ContestApi {
    @GET("/calendar/feed.json")
    LiveData<ApiResponse<ContestListWrapper>> getContests();
}
