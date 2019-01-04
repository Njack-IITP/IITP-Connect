package com.iitp.njack.iitp_connect.common.di.modules;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class YoutubeModule {

    @Provides
    @Singleton
    GoogleAccountCredential providesGoogleAccountCredential(Context context) {
        String[] SCOPES = {YouTubeScopes.YOUTUBE_READONLY};
        GoogleAccountCredential googleAccountCredential = GoogleAccountCredential.usingOAuth2(
            context, Arrays.asList(SCOPES))
            .setBackOff(new ExponentialBackOff());
        return googleAccountCredential;
    }

    @Provides
    @Singleton
    HttpTransport providesHttpTransport() {
        return AndroidHttp.newCompatibleTransport();
    }

    @Provides
    @Singleton
    JsonFactory providesJsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Provides
    @Singleton
    YouTube providesYoutubeService(HttpTransport httpTransport, JsonFactory jsonFactory, GoogleAccountCredential googleAccountCredential) {
        return new YouTube.Builder(
            httpTransport, jsonFactory, googleAccountCredential)
            .setApplicationName("IITP-Connect")
            .build();
    }
}
