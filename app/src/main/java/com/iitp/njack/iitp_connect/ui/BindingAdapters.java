package com.iitp.njack.iitp_connect.ui;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

public class BindingAdapters {
    private BindingAdapters() {
        // never called
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
