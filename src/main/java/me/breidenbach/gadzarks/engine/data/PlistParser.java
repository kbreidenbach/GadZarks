package me.breidenbach.gadzarks.engine.data;

import android.content.Context;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 11:36 AM
 * TODO THIS CLASS IS CRAP AND NEEDS TO BE TIDIED UP AND RESTRUCTURED
 */
class PlistParser {
    private static final String LOG_TAG = "GADZARKS_DATA";

    private final Map<String, String> tagMap = new HashMap<>();

    PlistParser(Context context, String file) throws DataException {
        try {
            InputStream stream = context.getAssets().open(file);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parseFile(factory, stream);
        } catch (IOException ioe) {
            String msg = "Unable to load data file " + file;
            Log.wtf(LOG_TAG, msg);
            throw new DataException(msg, ioe);

        } catch (XmlPullParserException xmle) {
            String msg = "Unable to instatiate XML parser";
            Log.wtf(LOG_TAG, msg);
            throw new DataException(msg, xmle);
        }
    }

    public String getTagValue(final String tag) {
        return tagMap.get(tag);
    }

    private void parseFile(final XmlPullParserFactory factory, final InputStream stream) throws DataException {
        try {
            factory.setNamespaceAware(true);
            final XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(stream, null);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        final String tagName = xpp.getName();
                        if (tagName.toLowerCase().equals("key")) {
                            getKeyValue(xpp);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException xppe) {
            String msg = "Error parsing data ";
            Log.wtf(LOG_TAG, msg);
            throw new DataException(msg, xppe);

        } catch (IOException ioe) {
            String msg = "Error reading data ";
            Log.wtf(LOG_TAG, msg);
            throw new DataException(msg, ioe);
        }
    }

    // TODO THIS IS UGLY, FIX IT
    private void getKeyValue(final XmlPullParser xpp) throws IOException, XmlPullParserException, DataException {
        int event = xpp.next();
        if (event != XmlPullParser.TEXT) {
            throw new DataException("Incorrect DataFile Format");
        }
        String key = xpp.getText();

        event = xpp.next();
        while (event != XmlPullParser.START_TAG) {
            event = xpp.next();
        }

        event = xpp.next();
        if (event != XmlPullParser.TEXT) {
            throw new DataException("Incorrect DataFile Format");
        }
        String value = xpp.getText();
        tagMap.put(key, value);
    }
}
