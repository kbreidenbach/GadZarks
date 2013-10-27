package me.breidenbach.gadzarks.engine;

import android.graphics.drawable.Drawable;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 3:51 PM
 */
public class CellDataStructure {
    private final int label;
    private final String title;
    private final String poemLine;
    private final Drawable headerImage;
    private final String zarkImageFile;
    private String setColorLabel = "";

    CellDataStructure(int label, String title, String poemLine,
                              Drawable headerImage, String zarkImageFile) {
        this.label = label;
        this.title = title;
        this.poemLine = poemLine;
        this.headerImage = headerImage;
        this.zarkImageFile = zarkImageFile;
    }

    public void setSetColorLabel(String setColorLabel) {
        this.setColorLabel = setColorLabel;
    }

    public String setColorLabel() { return setColorLabel; }
    public int label() { return label; }
    public String title() { return title; }
    public String poemLine() { return poemLine; }
    public Drawable headerImage() { return headerImage; }
    public String zarkImageFile() { return zarkImageFile; }

}
