package com.impllife.split.service;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.Optional;

public interface DataImage {
    Optional<Drawable> loadAppIcon(String pack);
    Optional<Bitmap> loadImg(String name);
    String saveImg(Bitmap bitmap);
}
