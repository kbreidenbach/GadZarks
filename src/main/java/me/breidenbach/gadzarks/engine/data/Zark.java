package me.breidenbach.gadzarks.engine.data;

import android.graphics.drawable.Drawable;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 5:06 PM
 */
public class Zark {
    private final String title;
    private final String poemLine;
    private final Drawable zarkImage;

    Zark(final String title, final String poemLine, final Drawable zarkImage) {
        this.title = title;
        this.poemLine = poemLine;
        this.zarkImage = zarkImage;
    }

    public String title() {
        return title;
    }

    public String poemLine() {
        return poemLine;
    }

    public Drawable zarkImage() {
        return zarkImage;
    }
}
