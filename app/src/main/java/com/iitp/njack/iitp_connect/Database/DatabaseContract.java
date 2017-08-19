package com.iitp.njack.iitp_connect.Database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by srv_twry on 19/8/17.
 * The contract class for the SQlite database.
 */

public class DatabaseContract {

    // Constants for the database
    public static final String AUTHORITY= "com.iitp.njack.iitp_connect";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_CONTESTS = "contests";

    //Class for the Coding-Calendar Table
    public static final class ContestEntry implements BaseColumns {

        public static final Uri CONTENT_URI_CONTESTS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTESTS).build();

        // Table name
        public static final String TABLE_NAME_CONTESTS = "contestsTable";

        //Table columns
        public static final String CONTEST_COLUMN_TITLE = "contestTitle";
        public static final String CONTEST_COLUMN_DESCRIPTION = "contestDescription";
        public static final String CONTEST_COLUMN_URL = "contestUrl";
        public static final String CONTEST_COLUMN_START_TIME = "startTime";
        public static final String CONTEST_COLUMN_END_TIME = "endTime";
    }


}
