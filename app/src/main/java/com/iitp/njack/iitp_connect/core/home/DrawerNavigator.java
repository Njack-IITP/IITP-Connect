package com.iitp.njack.iitp_connect.core.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.calendar.list.CodingCalendarActivity;
import com.iitp.njack.iitp_connect.core.profile.ProfileActivity;

public class DrawerNavigator {
    private final Context context;
    private final AuthViewModel authViewModel;

    private AlertDialog logoutDialog;
    private static final String GOOGLE_FORM_LINK = "";

    DrawerNavigator(Context context, AuthViewModel authViewModel) {
        this.context = context;
        this.authViewModel = authViewModel;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    void setLogoutDialog(AlertDialog logoutDialog) {
        this.logoutDialog = logoutDialog;
    }

    void selectItem(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_coding_calendar) {
            Intent intent = new Intent(context, CodingCalendarActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_suggestion) {
            Intent googleFormIntent = new Intent(Intent.ACTION_VIEW);
            googleFormIntent.setData(Uri.parse(GOOGLE_FORM_LINK));
            context.startActivity(googleFormIntent);
        } else if (id == R.id.nav_dashboard) {
            // navigate to dashboard.
        } else if (id == R.id.nav_logout) {
            showLogoutDialog();
        } else if (id == R.id.nav_profie) {
            Intent intent = new Intent(context, ProfileActivity.class);
            context.startActivity(intent);
        }
    }

    private void showLogoutDialog() {
        if (logoutDialog == null)
            logoutDialog = new AlertDialog.Builder(context)
                    .setTitle(R.string.logout_confirmation)
                    .setMessage(R.string.logout_confirmation_message)
                    .setPositiveButton(R.string.ok, (dialog, which) -> authViewModel.logout(context))
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .create();

        logoutDialog.show();
    }
}
