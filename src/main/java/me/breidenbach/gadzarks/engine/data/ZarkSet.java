package me.breidenbach.gadzarks.engine.data;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 5:04 PM
 */
public class ZarkSet {
    private final int count;
    private final String color;
    private final Map<Integer, Zark> zarks;

    ZarkSet(final String color, final int count, final Map<Integer, Zark> zarks) {
        this.color = color;
        this.count = count;
        this.zarks = zarks;
    }

    public String color() {
        return color;
    }

    public int count() {
        return count;
    }

    public Zark getZark(int number) {
        return zarks.get(number);
    }
}
