package me.breidenbach.gadzarks.engine.time;

import java.util.Date;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 6:00 PM
 */
public interface TimeReader {
    int getDaysSinceEpoch();

    void addListener(EpochChangeListener listener);

    Date epoch();
}
