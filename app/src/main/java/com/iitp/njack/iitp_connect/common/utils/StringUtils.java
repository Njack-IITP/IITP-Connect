package com.iitp.njack.iitp_connect.common.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import java.util.Date;

/**
 * Created by srv_twry on 19/10/17.
 * Contains helper methods to work with the Strings.
 */

public class StringUtils {

    //Helper method to return the start time in the desired format for the contest list.
    public static SpannableString getStartTimeTextContestList(Date startTime) {
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

    // Helper method to set the start time and date of the contest in intended order for the details fragment.
    public static SpannableString getStartTimeTextDetailsFragment(Date startTime) {

        String originalString = startTime.toString();
        String modifiedString;

        modifiedString = originalString.substring(0, 3) +
                "\n" + originalString.substring(4, 10) +
                "\n" + originalString.substring(11, 16) + " Local";

        SpannableString returningSpannableString = new SpannableString(modifiedString);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.50f), 0, 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.25f), 4, 10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //returningSpannableString.setSpan(new ForegroundColorSpan(Color.RED), 4, 10,
        //        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.0f), 11, 22,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return returningSpannableString;
    }
}
