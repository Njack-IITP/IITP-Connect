package com.iitp.njack.iitp_connect.core.calendar.list;

import com.iitp.njack.iitp_connect.common.view.Emptiable;
import com.iitp.njack.iitp_connect.common.view.Erroneous;
import com.iitp.njack.iitp_connect.common.view.Progressive;
import com.iitp.njack.iitp_connect.common.view.Refreshable;
import com.iitp.njack.iitp_connect.data.contest.Contest;

public interface CodingCalendarView extends Progressive, Erroneous, Refreshable, Emptiable<Contest> {
}
