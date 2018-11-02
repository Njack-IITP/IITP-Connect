package com.iitp.njack.iitp_connect.utils;

import android.os.SystemClock;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.TimeUnit;

/**
 * Utility class that decides whether we should fetch some data or not.
 */
public class RateLimiter<KEY> {
    private ArrayMap<KEY, Long> timestamps = new ArrayMap<>();
    private final long timeout;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
    }

    @Deprecated
    public synchronized boolean shouldFetchAndRefresh(KEY key) {
        Long lastFetched = timestamps.get(key);
        long now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    public synchronized boolean shouldFetch(KEY key) {
        Long lastFetched = timestamps.get(key);
        long now = now();
        if (lastFetched == null) {
            return true;
        }else return now - lastFetched > timeout;
    }

    public synchronized void refreshRateLimiter(KEY key) {
        Long lastFetched = timestamps.get(key);
        long now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);

        }else if (now - lastFetched > timeout) {
            timestamps.put(key, now);
        }
    }


    private long now() {
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(KEY key) {
        timestamps.remove(key);
    }
}
