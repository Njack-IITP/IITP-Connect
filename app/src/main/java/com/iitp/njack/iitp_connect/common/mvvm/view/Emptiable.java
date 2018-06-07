package com.iitp.njack.iitp_connect.common.mvvm.view;

import java.util.List;

public interface Emptiable<T> {

    void showResults(List<T> items);

    void showEmptyView(boolean show);

}
