package me.breidenbach.gadzarks.engine.data;

import android.graphics.drawable.Drawable;

import java.util.Map;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 5:04 PM
 */
public class ZarkSet {
    private final int count;
    private final String color;
    private final Drawable header;
    private final Map<Integer, Zark> zarks;

    ZarkSet(final String color, final int count, final Drawable header, final Map<Integer, Zark> zarks) {
        this.color = color;
        this.count = count;
        this.header = header;
        this.zarks = zarks;
    }

    public String color() {
        return color;
    }

    public int count() {
        return count;
    }

    public Drawable header() {
        return header;
    }

    public Zark getZark(int number) {
        return zarks.get(number);
    }
}
