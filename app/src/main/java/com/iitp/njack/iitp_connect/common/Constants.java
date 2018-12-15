package com.iitp.njack.iitp_connect.common;

public final class Constants {
    //url to login organiser
    public static final String HACKERRANK_API = "https://www.hackerrank.com/";
    //SharedPrefs db Name
    public static final String IITP_CONNECT_PREFS = "IITPConnectPrefs";
    //No network available string
    public static final String NO_NETWORK = "ContestApi Not Available";

    public static final String SHARED_PREFS_SAVED_EMAIL = "Saved Email";

    //IIT Patna Facebook pages.
    public static final String TEST_PAGE = "https://graph.facebook.com/";
    public static final String FIELDS = "likes.summary(true),comments.limit(0).summary(true),shares,created_time,message,permalink_url,full_picture,from{picture,name}";
    public static final String ACCESS_TOKEN = "API key";

    private Constants() {
        // Never Called
    }
}
