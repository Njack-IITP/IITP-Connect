package com.iitp.njack.iitp_connect.common;

import android.support.annotation.NonNull;

public interface Function<T, R> {
    @NonNull
    R apply(@NonNull T var1);
}
