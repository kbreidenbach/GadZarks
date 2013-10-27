package me.breidenbach.gadzarks.engine.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
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
    private static final String HEADER_IMAGE_FILE = "header.png";
    private static final String ZARK_IMAGE_FILE = "zark.png";

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
            String header = MAIN_FOLDER + '/' + folderNumber + '/' + HEADER_IMAGE_FILE;
            ZarkSet zarkSet = getZarkSet(file, folderNumber, header);
            zarkSets.put(number, zarkSet);
        }
        return new ZarkSets(numberOfZarkSets, zarkSets);
    }

    private ZarkSet getZarkSet(final String file, final String folderNumber, final String header) throws DataException {
        final PlistReader reader = new PlistReader(context, file);
        final String color = reader.readKey("Color");
        final Map<Integer, Zark> zarks = new HashMap<>();
        final int count = Integer.parseInt(reader.readKey("Count"));

        for (int number = 1; number <= count; number ++) {
            String zarkFolderNumber = folderNumberString(number);
            String zarkFile = MAIN_FOLDER + '/' + folderNumber + '/' + zarkFolderNumber + '/' + ZARK_FILE;
            String zarkImageFile = MAIN_FOLDER + '/' + folderNumber + '/' + zarkFolderNumber + '/' + ZARK_IMAGE_FILE;
            Zark zark = getZark(zarkFile, zarkImageFile);
            zarks.put(number, zark);
        }
        return new ZarkSet(color, count, getImage(header), zarks);
    }

    private Zark getZark(final String file, final String imageFile) throws DataException {
        final PlistReader reader = new PlistReader(context, file);
        final String title = reader.readKey("Title");
        final String poemLine = reader.readKey("PoemLine");
        return new Zark(title, poemLine, getImage(imageFile));
    }

    private String folderNumberString(final int folderNumber) {
        return String.format("%02d", folderNumber);
    }

    private Drawable getImage(String file) throws DataException {
        try {
            InputStream ims = context.getAssets().open(file);
            return Drawable.createFromStream(ims, null);
        } catch (IOException e) {
            throw new DataException("Unable to read image file " + file, e);
        }
    }
}
