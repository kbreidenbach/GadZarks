package me.breidenbach.gadzarks.engine.data;

import java.util.Map;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 5:13 PM
 */
public class ZarkSets {
    private final int numberOfZarksSets;
    private final Map<Integer, ZarkSet> zarkSets;

    ZarkSets(final int numberOfZarksSets, final Map<Integer, ZarkSet> zarkSets) {
        this.numberOfZarksSets = numberOfZarksSets;
        this.zarkSets = zarkSets;
    }

    public int numberOfZarkSets() {
        return numberOfZarksSets;
    }

    public ZarkSet getZarkSet(int number) {
        return zarkSets.get(number);
    }
}
