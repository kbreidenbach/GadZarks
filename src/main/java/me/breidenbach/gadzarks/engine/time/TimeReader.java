package me.breidenbach.gadzarks.engine.time;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 6:00 PM
 */
public interface TimeReader {
    int getDaysSinceEpoch();

    void addListener(EpochChangeListener listener);
}
