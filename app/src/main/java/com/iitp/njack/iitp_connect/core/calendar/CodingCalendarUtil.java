package com.iitp.njack.iitp_connect.core.calendar;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.utils.DateUtils;

public class CodingCalendarUtil {

    private CodingCalendarUtil() {
        // never called
    }

    public static String getContestStartDate(Contest contest) {
        return DateUtils.formatDateWithDefault(DateUtils.FORMAT_DAY_COMPLETE, contest.getStartDate());
    }

    public static int getContestLogo(Contest contest) {
        String url = contest.getUrl().toLowerCase();

        if (url.contains("codeforces")) {
            return R.mipmap.codeforces_logo;
        } else if(url.contains("codechef")){
            return R.mipmap.codechef_logo;
        } else if(url.contains("topcoder")) {
            return R.mipmap.topcoder_logo;
        } else  if(url.contains("hackerrank")) {
            return R.mipmap.hackerrank_logo;
        } else if (url.contains("hackerearth")) {
            return R.mipmap.hackerearth_logo;
        }

        return R.mipmap.ic_launcher_round;
    }
}
