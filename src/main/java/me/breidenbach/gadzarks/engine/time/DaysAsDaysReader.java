package me.breidenbach.gadzarks.engine.time;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 6:04 PM
 */
class DaysAsDaysReader implements TimeReader {
    private static final long DATE_DIVISOR = (1000 * 60 * 60 * 24);
    private final Date epoch;

    DaysAsDaysReader(Date epoch) {
        this.epoch = epoch;
    }

    @Override
    public int getDaysSinceEpoch() {
        Date today = GregorianCalendar.getInstance().getTime();
        return daysBetween(epoch, today);
    }

    private int daysBetween(Date epoch, Date today){
        return (int)( (today.getTime() - epoch.getTime()) / DATE_DIVISOR);
    }
}
