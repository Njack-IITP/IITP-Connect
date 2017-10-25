package com.iitp.njack.iitp_connect.CodingCalendar.Activities;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.iitp.njack.iitp_connect.CodingCalendar.Adapters.CodingCalendarAdapter;
import com.iitp.njack.iitp_connect.CodingCalendar.NetworkUtils.DataScraper;
import com.iitp.njack.iitp_connect.CodingCalendar.POJOs.Contest;
import com.iitp.njack.iitp_connect.Database.DatabaseContract;
import com.iitp.njack.iitp_connect.R;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class CodingCalendarHomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> , DataScraper.onLoadingFinishedListener , CodingCalendarAdapter.ContestRecyclerViewOnClickListener {

    private static final String TAG = CodingCalendarHomeActivity.class.getSimpleName();

    private ArrayList<Contest> contestArrayList = new ArrayList<>();
    ProgressBar loadingContestsProgressBar;
    @BindView(R.id.rv_coding_calendar)
    RecyclerView contestRecyclerView;
    private GridLayoutManager gridLayoutManager;

    private static final int CONTEST_LOADER_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_calendar_home);

        loadingContestsProgressBar = (ProgressBar) findViewById(R.id.pb_loading_contests);
        contestRecyclerView = (RecyclerView) findViewById(R.id.rv_coding_calendar);

        /*
        *Fetch the data.
        * */
        startLoadingData();

        loadingContestsProgressBar.setVisibility(View.VISIBLE);
        contestRecyclerView.setVisibility(View.INVISIBLE);
        CodingCalendarAdapter contestRecyclerViewAdapter = new CodingCalendarAdapter(this,contestArrayList);
        gridLayoutManager = new GridLayoutManager(CodingCalendarHomeActivity.this,1);
        contestRecyclerView.setAdapter(contestRecyclerViewAdapter);
        contestRecyclerView.setLayoutManager(gridLayoutManager);

    }

    void startLoadingData(){
        //If the device is online then get the updated data from the server otherwise use the cached data from the database.
        if (isOnline()){
            DataScraper dataScraper = new DataScraper(getBaseContext(),this);
            dataScraper.fetchContest();
        }else{
            getDataFromDatabase();
        }
    }

    // The method which uses loaders to get the Data from the database to the recycler view.
    private void getDataFromDatabase() {
        this.getSupportLoaderManager().initLoader(CONTEST_LOADER_ID,null,this);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    /*
    * Loader methods
    * */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getBaseContext()) {

            Cursor mCursor;

            @Override
            protected void onStartLoading() {
                if (mCursor !=null){
                    deliverResult(mCursor);
                }else{
                    forceLoad();
                }
            }

            public void deliverResult(Cursor c) {
                mCursor=c;
                super.deliverResult(mCursor);
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return getContentResolver().query(DatabaseContract.ContestEntry.CONTENT_URI_CONTESTS,null,null,null,null);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data !=null){
            contestArrayList = convertCursorToArrayList(data);
            CodingCalendarAdapter contestRecyclerViewAdapter = new CodingCalendarAdapter(this,contestArrayList);
            contestRecyclerView.setAdapter(contestRecyclerViewAdapter);
            contestRecyclerView.invalidate();
            loadingContestsProgressBar.setVisibility(View.INVISIBLE);
            contestRecyclerView.setVisibility(View.VISIBLE);

//            //scrolling the recyclerview to the last visited position.
//            if (recyclerViewState != null){
//                gridLayoutManager.onRestoreInstanceState(recyclerViewState);
//            }
        }
    }

    // To convert the cursor to an array list
    private ArrayList<Contest> convertCursorToArrayList(Cursor data) {
        ArrayList<Contest> returnArrayList = new ArrayList<>();

        int titleColumnIndex = data.getColumnIndex(DatabaseContract.ContestEntry.CONTEST_COLUMN_TITLE);
        int descriptionColumnIndex = data.getColumnIndex(DatabaseContract.ContestEntry.CONTEST_COLUMN_DESCRIPTION);
        int urlColumnIndex = data.getColumnIndex(DatabaseContract.ContestEntry.CONTEST_COLUMN_URL);
        int startTimeColumnIndex = data.getColumnIndex(DatabaseContract.ContestEntry.CONTEST_COLUMN_START_TIME);
        int endTimeColumnIndex = data.getColumnIndex(DatabaseContract.ContestEntry.CONTEST_COLUMN_END_TIME);

        for(int i=0; i< data.getCount();i++){
            data.moveToPosition(i);
            String title = data.getString(titleColumnIndex);
            String description= data.getString(descriptionColumnIndex);
            String url = data.getString(urlColumnIndex);
            String start = data.getString(startTimeColumnIndex);
            String end = data.getString(endTimeColumnIndex);

            Date startTime = getDateFromString(start);
            Date endTime = getDateFromString(end);

            returnArrayList.add(new Contest(title,description,url,startTime,endTime));
        }
        return returnArrayList;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //set the blank data to the recyclerview.
        contestArrayList = new ArrayList<>();
    }

    //A helper method to convert the time in String to Java Date Class
    private Date getDateFromString(String string){

        Date result;
        try {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(tz);
            result = sdf.parse(string);
            Log.v(TAG,string);
            Log.v(TAG,result.toString());
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public void onLoadingFinished() {
        // Start the loader to fetch the updated data.
        getDataFromDatabase();
    }

    @Override
    public void onContestListItemClicked(Contest clickedContest) {
        Log.v(TAG,"Contest clicked with title- "+ clickedContest.getTitle());
    }
}
