package me.breidenbach.gadzarks.views;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 7:39 PM
 */
public class HeaderView {

    public static ImageView theHeaderView;

    public static void setHeaderView(ImageView headerView) {
        if (theHeaderView == null) {
            theHeaderView = headerView;
        }
    }

    public static void setImage(Drawable image) {
        theHeaderView.setImageDrawable(image);
    }
}
