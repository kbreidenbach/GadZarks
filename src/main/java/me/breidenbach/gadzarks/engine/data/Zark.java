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
    private final String zarkImageFile;

    Zark(final String title, final String poemLine, final String zarkImageFile) {
        this.title = title;
        this.poemLine = poemLine;
        this.zarkImageFile = zarkImageFile;
    }

    public String title() {
        return title;
    }

    public String poemLine() {
        return poemLine;
    }

    public String zarkImageFile() {
        return zarkImageFile;
    }
}
