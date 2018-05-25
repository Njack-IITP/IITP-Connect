package com.iitp.njack.iitp_connect.data;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Utility class to be used by view models and models for
 * Android Context based actions
 *
 * Break in sub-modules if grows large
 */
public class AndroidUtils implements ContextUtils {

    private final Context context;

    @Inject
    AndroidUtils(Context context) {
        this.context = context;
    }

    @Override
    public String getResourceString(@StringRes int stringId) {
        return context.getResources().getString(stringId);
    }

    @Override
    @ColorInt
    public int getResourceColor(@ColorRes int colorId) {
        return ContextCompat.getColor(context, colorId);
    }

    @Override
    public Completable deleteDatabase() {
        // TODO: Implement deletion of IITPConnectDatabase
        return Completable.complete();
    }
}
