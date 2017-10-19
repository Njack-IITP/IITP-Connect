package com.iitp.njack.iitp_connect.Utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

import java.util.Date;

/**
 * Created by srv_twry on 19/10/17.
 * Contains helper methods to work with the Database.
 */

public class DatabaseUtilities {

    //Helper method to return the start time in the desired format for the contest list.
    public static SpannableString getStartTimeTextContestList(Date startTime){
        String startTimeString = startTime.toString();
        String modifiedString;

        String sb = startTimeString.substring(0, 10) +
                "\n" +
                startTimeString.substring(11, 16) +
                " Local";
        modifiedString = sb;

        SpannableString returningSpannableString = new SpannableString(modifiedString);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.0f), 0, 10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new RelativeSizeSpan(0.75f), 11, 22,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return returningSpannableString;
    }

}
