package com.iitp.njack.iitp_connect.core.youtube;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;

public class VideoRepository {
    Context context;
    private List<YoutubeVideo> youtubeVideos = new ArrayList<>();
    private MutableLiveData<List<YoutubeVideo>> videos = new MutableLiveData<>();
    private GoogleAccountCredential googleAccountCredential;
    private String playlist_id = "";

    VideoRepository(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
    }

    MutableLiveData<List<YoutubeVideo>> getVideos() {
        return this.videos;
    }

    void getDataFromAPI(Context context) {
        this.context = context;
        new MakeRequestTask(googleAccountCredential).execute();
        this.youtubeVideos.add(new YoutubeVideo("abcd"));
        videos.setValue(youtubeVideos);
    }

    void setPlaylist_id(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    @Singleton
    private class MakeRequestTask extends AsyncTask<Void, Void, List<YoutubeVideo>> {
        private com.google.api.services.youtube.YouTube mService;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.youtube.YouTube.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("IITP-Connect")
                .build();
            youtubeVideos.add(new YoutubeVideo("http1"));
        }

        @Override
        protected List<YoutubeVideo> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (UserRecoverableAuthIOException e) {
                ((Activity) context).startActivityForResult(e.getIntent(), 2);
                return null;
            } catch (Exception e) {
                Log.e("error", "error occured connect");

                youtubeVideos.add(new YoutubeVideo("error occured"));
                cancel(true);
                return null;
            }
        }

        private List<YoutubeVideo> getDataFromApi() throws IOException {
            List<YoutubeVideo> channelInfo = new ArrayList<>();
            List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
            YouTube.PlaylistItems.List playlistItemRequest =
                mService.playlistItems().list("id,contentDetails,snippet");

            playlistItemRequest.setPlaylistId(playlist_id);
            playlistItemRequest.setFields(
                "items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");
            String nextToken = "";

            do {
                playlistItemRequest.setPageToken(nextToken);
                PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                playlistItemList.addAll(playlistItemResult.getItems());

                nextToken = playlistItemResult.getNextPageToken();
            } while (nextToken != null);

            for (PlaylistItem playlistItem : playlistItemList) {
                channelInfo.add(new YoutubeVideo(playlistItem.getContentDetails().getVideoId()));
            }
            youtubeVideos.add(new YoutubeVideo("HTTP"));
            return channelInfo;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(List<YoutubeVideo> output) {
            youtubeVideos.add(new YoutubeVideo("http"));
            if (output == null || output.size() == 0) {
                youtubeVideos.add(new YoutubeVideo("hkhk"));
            } else {
                youtubeVideos = output;
            }
            videos.setValue(youtubeVideos);
        }

        @Override
        protected void onCancelled() {

        }
    }
}
