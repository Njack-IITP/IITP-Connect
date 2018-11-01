package com.iitp.njack.iitp_connect.common.di.modules;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;
import com.iitp.njack.iitp_connect.core.youtube.PlaylistRepository;
import com.iitp.njack.iitp_connect.core.youtube.VideoRepository;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class YoutubeModule {
    @Provides
    @Singleton
    PlaylistRepository providesPlaylistRepository(GoogleAccountCredential googleAccountCredential) {
        return new PlaylistRepository(googleAccountCredential);
    }

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
    VideoRepository providesVideoRepository(GoogleAccountCredential googleAccountCredential) {
        return new VideoRepository(googleAccountCredential);
    }
}
