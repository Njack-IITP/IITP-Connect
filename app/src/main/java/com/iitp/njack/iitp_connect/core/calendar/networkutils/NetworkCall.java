package com.iitp.njack.iitp_connect.core.calendar.networkutils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.database.room.DatabaseContract;
import com.iitp.njack.iitp_connect.data.contest.ContestApi;
import com.iitp.njack.iitp_connect.ServiceGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NetworkCall {
    private Context context;
    private final onLoadingFinishedListener1 OnLoadingFinishedListener1;

    public NetworkCall(Context context, onLoadingFinishedListener1 onLoadingFinishedListener1) {
        this.context = context;
        OnLoadingFinishedListener1 = onLoadingFinishedListener1;
    }

    public void fetchContest() {
        ContestApi contestApi = ServiceGenerator.createService(ContestApi.class);    //Create the contestApi and call object
        Call<ResponseBody> myCall = contestApi.Contests();
        myCall.enqueue(new Callback<ResponseBody>() {                   //.enqueue() makes async https call to the api
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody res = response.body();                     //res stores the raw json response recieved
                String baseJson = null;
                try {
                    baseJson = res.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject baseObject = null;
                try {
                    baseObject = new JSONObject(baseJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray models = null;
                try {
                    models = baseObject.getJSONArray("models");           //models stores the array "models" containing json objects describing contests
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (models != null)
                    addToDatabase(models);
                OnLoadingFinishedListener1.onLoadingFinished();         //call the onLoadingFinished method implemented by the CodingCalenderHome activity

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, R.string.check_your_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addToDatabase(JSONArray models) {
        try {
            Uri uri = DatabaseContract.ContestEntry.CONTENT_URI_CONTESTS;
            context.getContentResolver().delete(uri, null, null);
            //The hackerrank API sorts contests in reverse order so reversing the array

            for (int i = models.length() - 1; i >= 0; i--) {
                JSONObject obj = models.getJSONObject(i);
                String title = obj.getString("title");
                String description = obj.getString("description");
                String start = obj.getString("start");
                String end = obj.getString("end");
                String url = obj.getString("url");
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_TITLE, title);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_DESCRIPTION, description);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_URL, url);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_START_TIME, start);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_END_TIME, end);

                //Adding the data to the database.
                context.getContentResolver().insert(DatabaseContract.ContestEntry.CONTENT_URI_CONTESTS, contentValues);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface onLoadingFinishedListener1 {        //interface to be implemented by the class calling the methods of this class
        void onLoadingFinished();
    }

}

