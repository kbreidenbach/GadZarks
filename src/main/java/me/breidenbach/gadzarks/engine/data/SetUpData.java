package me.breidenbach.gadzarks.engine.data;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 4:41 PM
 */
public class SetUpData {
    private static final String LOG_TAG = "GADZARKS_DATA";
    private static final String MAIN_FOLDER = "zark_sets";
    private static final String MAIN_FILE = "zark_sets.plist";
    private static final String SET_FILE = "zark_set.plist";
    private static final String ZARK_FILE = "zark.plist";

    private final ZarkSets zarkSets;

    private int numberOfZarkSets = 0;


    private Context context;

    public SetUpData(final Context context) throws DataException {
        this.context = context;
        this.zarkSets = setUpZarkSets();
    }

    public ZarkSets zarkSets() {
        return zarkSets;
    }

    private void getNumberOfZarkSets() throws DataException {
        String zarksFile = MAIN_FOLDER + '/' + MAIN_FILE;
        PlistReader reader = new PlistReader(context, zarksFile);
        numberOfZarkSets = Integer.parseInt(reader.readKey("Sets"));
        Log.d(LOG_TAG, "Number of Zarks: " + numberOfZarkSets);
    }

    private ZarkSets setUpZarkSets() throws DataException {
        getNumberOfZarkSets();
        final Map<Integer, ZarkSet> zarkSets = new HashMap<>();
        for (int number = 1; number <= numberOfZarkSets; number ++) {
            String folderNumber = folderNumberString(number);
            String file = MAIN_FOLDER + '/' + folderNumber + '/' + SET_FILE;
            ZarkSet zarkSet = getZarkSet(file, folderNumber);
            zarkSets.put(number, zarkSet);
        }
        return new ZarkSets(numberOfZarkSets, zarkSets);
    }

    private ZarkSet getZarkSet(final String file, final String folderNumber) throws DataException {
        final PlistReader reader = new PlistReader(context, file);
        final String color = reader.readKey("Color");
        final Map<Integer, Zark> zarks = new HashMap<>();
        final int count = Integer.parseInt(reader.readKey("Count"));

        for (int number = 1; number <= count; number ++) {
            String zarkFolderNumber = folderNumberString(number);
            String zarkFile = MAIN_FOLDER + '/' + folderNumber + '/' + zarkFolderNumber + '/' + ZARK_FILE;
            Zark zark = getZark(zarkFile);
            zarks.put(number, zark);
        }
        return new ZarkSet(color, count, zarks);
    }

    private Zark getZark(final String file) throws DataException {
        final PlistReader reader = new PlistReader(context, file);
        final String title = reader.readKey("Title");
        final String poemLine = reader.readKey("PoemLine");
        return new Zark(title, poemLine);
    }

    private String folderNumberString(final int folderNumber) {
        return String.format("%02d", folderNumber);
    }
}
