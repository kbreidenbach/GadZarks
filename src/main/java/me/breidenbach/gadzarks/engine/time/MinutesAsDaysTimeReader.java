package me.breidenbach.gadzarks.engine.time;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 6:05 PM
 */
class MinutesAsDaysTimeReader implements TimeReader {
    private static final long DATE_DIVISOR = (1000 * 60);

    private final Date epoch;

    MinutesAsDaysTimeReader(Date epoch) {
        this.epoch = epoch;
    }

    @Override
    public int getDaysSinceEpoch() {
        Date today = GregorianCalendar.getInstance().getTime();
        return minutesBetween(epoch, today);
    }

    private int minutesBetween(Date epoch, Date today){
        return (int)( (today.getTime() - epoch.getTime()) / DATE_DIVISOR);
    }
}
