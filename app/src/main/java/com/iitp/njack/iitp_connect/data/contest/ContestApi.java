package com.iitp.njack.iitp_connect.data.contest;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ContestApi {
    @GET("/calendar/feed.json")
    Observable<ContestListWrapper> getContests();
}
