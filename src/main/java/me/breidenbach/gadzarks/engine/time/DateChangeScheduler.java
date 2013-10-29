package me.breidenbach.gadzarks.engine.time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


/**
 * User: Kevin Breidenbach
 * Date: 10/28/13
 * Time: 9:34 PM
 */
class DateChangeScheduler extends BroadcastReceiver {
    private final PendingIntent scheduledService;
    private final Context context;
    private final TimeManager manager;

    DateChangeScheduler(Context context, TimeManager manager) {
        this.manager = manager;
        this.context = context;
        final Intent intent = new Intent(context, DateChangeScheduler.class);
        this.scheduledService = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    void startScheduler() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, scheduledService);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        manager.dayChanged();
    }
}
