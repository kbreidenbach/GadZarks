package me.breidenbach.gadzarks.utils;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: Kevin Breidenbach
 * Date: 10/29/13
 * Time: 4:06 PM
 */
public class ImageUtils {

    private static final String LOG_TAG = "GADZARKS_IMAGEUTIL";

    public static Drawable getImage(View view, String file) {
        Drawable image = null;
        try {
            InputStream ims = view.getContext().getAssets().open(file);
            image = Drawable.createFromStream(ims, null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error loading disc image: " + e.getMessage());
        }

        return image;
    }
}
