package me.breidenbach.gadzarks.engine.time;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 6:03 PM
 */
public class TimeManager {
    private static final String LOG_TAG = "GADZARKS_TIME";
    private static final String EPOCH_DATE_FILE = "Gad_Zarks_Epoch";

    private final Context context;
    private final DaysSinceEpoch daysSinceEpoch;


    public TimeManager(Context context) {
        this.context = context;
        Date epoch = readEpochDate();
        if (readEpochDate() == null) {
            epoch = resetEpoch();
        }
        daysSinceEpoch = new DaysSinceEpoch(epoch);
    }

    public void useFastTimeReader(boolean useFast) {
        daysSinceEpoch.useFast(useFast);
    }

    public void setDate(Date date) {
        writeEpochDate(date);
        daysSinceEpoch.epochChaged(date);
    }

    public TimeReader getTimeReader() {
        return daysSinceEpoch;
    }

    private Date resetEpoch() {
        Date epoch = GregorianCalendar.getInstance().getTime();
        writeEpochDate(epoch);
        return epoch;
    }

    private void writeEpochDate(Date epochDate) {
        try {

            FileOutputStream fos = context.openFileOutput(EPOCH_DATE_FILE, Context.MODE_PRIVATE);
            fos.write(SimpleDateFormat.getDateTimeInstance().format(epochDate).getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Log.wtf(LOG_TAG, "Unable to find file: " + e.getMessage());
        } catch (IOException e) {
            Log.wtf(LOG_TAG, "Unable to write file: " + e.getMessage());
        }
    }

    private Date readEpochDate() {
        Date epochDate = null;
        FileInputStream fis;
        try {
            fis = context.openFileInput(EPOCH_DATE_FILE);
        } catch (FileNotFoundException e) {
            Log.d(LOG_TAG, "File not found: " + e.getMessage());
            // If not found we'll write it
            return epochDate;
        }

        if (fis != null) {
            StringBuilder sb = new StringBuilder();
            int readByte;
            try {
                while ((readByte = fis.read()) != -1) {
                    char readChar = (char) readByte;
                    sb.append(readChar);
                }
            } catch (IOException e) {
                Log.wtf(LOG_TAG, "Read error, file may be corrupt: " + e.getMessage());
            }
            try {
                epochDate = SimpleDateFormat.getDateTimeInstance().parse(sb.toString());
            } catch (ParseException e) {
                Log.wtf(LOG_TAG, "Parse error, file may be corrupt: " + e.getMessage());
            }
        }
        return epochDate;
    }
}
