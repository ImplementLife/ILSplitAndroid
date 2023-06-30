package com.impllife.split.service;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.impllife.split.data.constant.DefaultAccountImg;
import com.impllife.split.ui.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataImageImpl implements DataImage {
    private final MainActivity mainActivity = MainActivity.getInstance();
    private final Map<String, Drawable> appIconMemoCash = new HashMap<>();
    private final String basePathDir = "/image";

    public Optional<Drawable> loadAppIcon(String pack) {
        if (appIconMemoCash.containsKey(pack)) {
            return Optional.ofNullable(appIconMemoCash.get(pack));
        }
        Drawable drawable = null;
        try {
            PackageManager pm = MainActivity.getInstance().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(pack, 0);
            drawable = ai.loadIcon(pm);
            appIconMemoCash.put(pack, drawable);
        } catch (PackageManager.NameNotFoundException ignored) {
            appIconMemoCash.put(pack, null);
            Log.w("DataService.loadAppIcon", "can't load another icon of app: " + pack);
        }
        return Optional.ofNullable(drawable);
    }

    public Optional<Bitmap> loadImg(String name) {
        Bitmap result = null;

        DefaultAccountImg mapping = DefaultAccountImg.parse(name);
        if (mapping != null) {
            result = BitmapFactory.decodeResource(mainActivity.getResources(), mapping.id);
        }

        File dir = new File(mainActivity.getFilesDir().getAbsolutePath() + basePathDir);
        File file = new File(dir, name + ".png");
        if (dir.exists() && file.exists()) {
            result = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return Optional.ofNullable(result);
    }

    public String saveImg(Bitmap bitmap) {
        File dir = new File(mainActivity.getFilesDir().getAbsolutePath() + basePathDir);
        dir.mkdirs();
        String filename = "custom_img_" + new Date().getTime();
        File file = new File(dir, filename + ".png");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            Log.e("file", "err", e);
        }
        return filename;
    }

    public void readTxtFile(String path) {
        File file = new File(path);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            Log.i("file", new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e("file", "err", e);
        }
    }
}
