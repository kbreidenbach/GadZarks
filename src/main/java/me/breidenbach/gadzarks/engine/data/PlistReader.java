package me.breidenbach.gadzarks.engine.data;

import android.content.Context;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 3:53 PM
 */
class PlistReader {
    private final PlistParser parser;

    PlistReader(Context context, String plistFileName) throws DataException {
        this.parser = new PlistParser(context, plistFileName);

    }

    public String readKey(String key) {
        return parser.getTagValue(key);
    }
}
