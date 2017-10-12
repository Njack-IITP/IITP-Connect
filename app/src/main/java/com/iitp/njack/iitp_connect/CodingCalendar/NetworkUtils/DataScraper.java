package com.iitp.njack.iitp_connect.CodingCalendar.NetworkUtils;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iitp.njack.iitp_connect.Database.DatabaseContract;
import com.iitp.njack.iitp_connect.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by srv_twry on 11/10/17.
 * Helper function to fetch the data from hackerrank.
 * It uses volley to fetch the data.
 */

public class DataScraper {
    private static final String HACKERRANK_API_URL = "https://www.hackerrank.com/calendar/feed.json";
    private final Context context;
    private static RequestQueue requestQueue;
    private final onLoadingFinishedListener onLoadingFinishedListener;

    public DataScraper(Context context ,onLoadingFinishedListener onLoadingFinishedListener){
        this.context = context;
        this.onLoadingFinishedListener = onLoadingFinishedListener;
        requestQueue = Volley.newRequestQueue(context);
    }

    public  void fetchContest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HACKERRANK_API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addToDatabase(response);
                onLoadingFinishedListener.onLoadingFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.check_your_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    // Parse the JSON Response and add to database
    private void addToDatabase(JSONObject response) {
        try{

            JSONArray models = response.getJSONArray("models");

            //The hackerrank API sorts contests in reverse order so reversing the array
            for(int i=models.length()-1; i>=0; i--){
                JSONObject obj = models.getJSONObject(i);
                String title = obj.getString("title");
                String description = obj.getString("description");
                String start = obj.getString("start");
                String end = obj.getString("end");
                String url = obj.getString("url");


                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_TITLE,title);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_DESCRIPTION,description);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_URL,url);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_START_TIME,start);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_END_TIME,end);

                //Adding the data to the database.
                context.getContentResolver().insert(DatabaseContract.ContestEntry.CONTENT_URI_CONTESTS,contentValues);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface onLoadingFinishedListener{
        void onLoadingFinished();
    }
}
