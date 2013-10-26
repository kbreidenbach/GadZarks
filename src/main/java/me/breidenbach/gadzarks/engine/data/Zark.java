package me.breidenbach.gadzarks.engine.data;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 5:06 PM
 */
public class Zark {
    private final String title;
    private final String poemLine;

    Zark(String title, String poemLine) {
        this.title = title;
        this.poemLine = poemLine;
    }

    public String title() {
        return title;
    }

    public String poemLine() {
        return poemLine;
    }
}
