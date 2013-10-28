package me.breidenbach.gadzarks.views;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 7:48 PM
 */
public class ZarkView {

    public static ImageView theZarkView;

    public static void setZarkView(ImageView zarkView) {
        if (theZarkView == null) {
            theZarkView = zarkView;
        }
    }

    public static void setImage(Drawable image) {
        theZarkView.setImageDrawable(image);
    }
}
