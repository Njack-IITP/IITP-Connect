package com.iitp.njack.iitp_connect.core.youtube;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.databinding.ActivityVideoBinding;

import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoActivity extends AppCompatActivity {
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    @Inject
    GoogleAccountCredential googleAccountCredential;
    String playlist_id = "";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private VideoViewModel videoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlist_id = getIntent().getStringExtra("playlist_id");
        setupbindings(savedInstanceState);
    }

    private void setupbindings(Bundle savedInstanceState) {
        ActivityVideoBinding activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        videoViewModel = ViewModelProviders.of(this,viewModelFactory).get(VideoViewModel.class);
        getResultsFromApi();
        if (playlist_id == null) {
            playlist_id = "PLOU2XLYxmsILACK8NF7UHElmmZzudR7c7";
        }
        videoViewModel.setGoogleAccountCredential(googleAccountCredential);
        videoViewModel.setPlaylist_id(playlist_id);
        activityVideoBinding.setModel(videoViewModel);
        setUpListUpdate();
    }

    private void setUpListUpdate() {
        videoViewModel.loading.set(View.VISIBLE);
        videoViewModel.fetchList();
        videoViewModel.getVideos().observe(this, new Observer<List<YoutubeVideo>>() {
            @Override
            public void onChanged(@Nullable List<YoutubeVideo> youtubeVideos) {
                videoViewModel.loading.set(View.GONE);
                if (youtubeVideos.size() == 0) {
                    videoViewModel.showEmpty.set(View.VISIBLE);
                } else {
                    videoViewModel.showEmpty.set(View.GONE);
                    videoViewModel.setPlaylistsInAdapter(youtubeVideos);
                }
            }
        });
        setUpListClick();
    }

    private void setUpListClick() {
        videoViewModel.getSelected().observe(this, new Observer<YoutubeVideo>() {
            @Override
            public void onChanged(@Nullable YoutubeVideo youtubeVideo) {
                if (youtubeVideo != null) {
                    Toast.makeText(VideoActivity.this, "you selected a " + youtubeVideo.getVideo_id(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VideoActivity.this, VideoPlayActivity.class);
                    intent.putExtra("video_id", youtubeVideo.getVideo_id());
                    startActivity(intent);
                }
            }
        });
    }

    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (googleAccountCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Log.e("network error", "No network connection available.");
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
            this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                googleAccountCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                    googleAccountCredential.newChooseAccountIntent(),
                    REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                this,
                "This app needs to access your Google account (via Contacts).",
                REQUEST_PERMISSION_GET_ACCOUNTS,
                Manifest.permission.GET_ACCOUNTS);
        }
    }

    @Override
    protected void onActivityResult(
        int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Log.e("google", "google play services not found");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                    data.getExtras() != null) {
                    String accountName =
                        data.getStringExtra(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                            getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        googleAccountCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
            requestCode, permissions, grantResults, this);
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
            GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
            apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
            GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
            apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(
        final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
            VideoActivity.this,
            connectionStatusCode,
            REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}

