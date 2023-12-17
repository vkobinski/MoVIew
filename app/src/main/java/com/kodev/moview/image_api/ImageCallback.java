package com.kodev.moview.image_api;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ImageCallback {

    void downloadImage(String tag, Bitmap bitmap);
}
