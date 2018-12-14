package com.iitp.njack.iitp_connect.common.di.modules;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;
import com.iitp.njack.iitp_connect.core.youtube.PlaylistRepository;
import com.iitp.njack.iitp_connect.core.youtube.VideoRepository;
import com.google.api.services.youtube.YouTube;


import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class YoutubeModule {
    @Provides
    @Singleton
    public GoogleAccountCredential providesGoogleAccountCredential(Context context) {
        String[] SCOPES = {YouTubeScopes.YOUTUBE_READONLY};
        return GoogleAccountCredential.usingOAuth2(
            context, Arrays.asList(SCOPES))
            .setBackOff(new ExponentialBackOff());
    }

    @Provides
    @Singleton
    public HttpTransport providesHttpTransport(){
        return AndroidHttp.newCompatibleTransport();
    }

    @Singleton
    @Provides
    public JsonFactory providesJsonFactory() {
        return  JacksonFactory.getDefaultInstance();
    }

    @Provides
    @Singleton
    public YouTube providesYoutubeService(HttpTransport transport, JsonFactory jsonFactory, GoogleAccountCredential googleAccountCredential) {
        return new YouTube.Builder(
            transport, jsonFactory, googleAccountCredential)
            .setApplicationName("IITP-Connect")
            .build();
    }
}
