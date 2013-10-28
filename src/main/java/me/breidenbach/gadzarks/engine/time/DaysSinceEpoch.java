package me.breidenbach.gadzarks.engine.time;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 6:04 PM
 */
class DaysSinceEpoch implements TimeReader {
    private static final long DATE_DIVISOR = (1000 * 60 * 60 * 24);
    private static final long MINUTE_DIVISOR = (1000 * 60);

    private final List<EpochChangeListener> listeners = new ArrayList();
    private Date epoch;
    private boolean useFast = false;


    DaysSinceEpoch(Date epoch) {
        this.epoch = epoch;
    }

    @Override
    public int getDaysSinceEpoch() {
        Date today = GregorianCalendar.getInstance().getTime();
        if (useFast) {
            return minutesBetween(epoch, today);
        }
        return daysBetween(epoch, today);
    }

    @Override
    public void addListener(EpochChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }


    public void epochChaged(Date newDate) {
        epoch = newDate;
        notifyListenersEpochChanged();
    }



    public void useFast(boolean useFast) {
        this.useFast = useFast;
        notifyListenersUseFastChanged();
    }


    private int daysBetween(Date epoch, Date today){
        return (int)( (today.getTime() - epoch.getTime()) / DATE_DIVISOR);
    }

    private int minutesBetween(Date epoch, Date today){
        return (int)( (today.getTime() - epoch.getTime()) / MINUTE_DIVISOR);
    }

    private void notifyListenersEpochChanged() {
        for (EpochChangeListener listener : listeners) {
            listener.epochChanged();
        }
    }

    private void notifyListenersUseFastChanged() {
        for (EpochChangeListener listener : listeners) {
            listener.useFastChanged();
        }
    }

}
