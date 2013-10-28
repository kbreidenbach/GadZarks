package me.breidenbach.gadzarks.engine.settings;

import android.content.Context;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 8:53 PM
 */
public class SettingsManager {
    private static final String LOG_TAG = "GADZARKS_SETTINGS";
    private static final String SETTINGS_FILE = "Gad_Zarks_Settings";

    private final Context context;

    public SettingsManager(Context context) {
        this.context = context;
    }
}
