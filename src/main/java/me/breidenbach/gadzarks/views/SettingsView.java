package me.breidenbach.gadzarks.views;

import android.widget.RelativeLayout;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 12:01 PM
 */
public class SettingsView {
    private final RelativeLayout layout;

    public SettingsView(RelativeLayout layout) {
        this.layout = layout;
    }

    public void hideLayout() {
        layout.setVisibility(RelativeLayout.GONE);
    }

    public void showLayout() {
        layout.setVisibility(RelativeLayout.VISIBLE);
    }
}
