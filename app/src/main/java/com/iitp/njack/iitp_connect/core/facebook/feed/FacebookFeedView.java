package com.iitp.njack.iitp_connect.core.facebook.feed;

import com.iitp.njack.iitp_connect.common.view.Emptiable;
import com.iitp.njack.iitp_connect.common.view.Erroneous;
import com.iitp.njack.iitp_connect.common.view.Progressive;
import com.iitp.njack.iitp_connect.common.view.Refreshable;
import com.iitp.njack.iitp_connect.data.facebook.FacebookPost;

public interface FacebookFeedView extends Progressive, Erroneous, Refreshable, Emptiable<FacebookPost> {
    void openFacebookPostDetails(String url);
}
