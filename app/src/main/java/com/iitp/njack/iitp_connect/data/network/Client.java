package com.iitp.njack.iitp_connect.data.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface for retrofit network calls
 */

public interface Client {
    @GET("/calendar/feed.json")
    Call<ResponseBody> Contests();
}
