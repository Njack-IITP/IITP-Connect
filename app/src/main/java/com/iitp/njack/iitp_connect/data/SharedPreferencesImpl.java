package com.iitp.njack.iitp_connect.data;

import android.content.Context;

import com.iitp.njack.iitp_connect.IITPConnectProvider;
import com.iitp.njack.iitp_connect.common.Constants;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

public class SharedPreferencesImpl implements Preferences {
    private final android.content.SharedPreferences sharedPreferences;

    @Inject
    SharedPreferencesImpl() {
        sharedPreferences = IITPConnectProvider.context.getSharedPreferences(Constants.IITP_CONNECT_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void saveString(String key, String value) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public void setLong(String key, long value) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putLong(key, value);
        editor.apply();
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    @Override
    public void saveStringSet(String key, Set<String> value) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putStringSet(key, value);
        editor.apply();
    }

    @Override
    public void addStringSetElement(String key, String value) {
        Set<String> set = getStringSet(key, new HashSet<String>());
        set.add(value);
        saveStringSet(key, set);
    }

    private android.content.SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

}
