package com.iitp.njack.iitp_connect.core.calendar.list;

import com.iitp.njack.iitp_connect.common.mvvm.view.Emptiable;
import com.iitp.njack.iitp_connect.common.mvvm.view.Erroneous;
import com.iitp.njack.iitp_connect.common.mvvm.view.Progressive;
import com.iitp.njack.iitp_connect.common.mvvm.view.Refreshable;
import com.iitp.njack.iitp_connect.data.contest.Contest;

public interface CodingCalendarView extends Progressive, Erroneous, Refreshable, Emptiable<Contest> {
}
