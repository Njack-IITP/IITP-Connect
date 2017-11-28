package com.iitp.njack.iitp_connect.Retrofit;

import com.iitp.njack.iitp_connect.CodingCalendar.POJOs.ContestList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface for retrofit network calls
 */

public interface Client {
    @GET("/calender/feed.json")
    Call<ContestList> Contests();
}
