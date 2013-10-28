package me.breidenbach.gadzarks.controllers;

import android.view.View;
import me.breidenbach.gadzarks.views.SettingsView;

/**
 * User: Kevin Breidenbach
 * Date: 10/28/13
 * Time: 1:41 PM
 */
public class MenuImageController implements View.OnClickListener {

    private final SettingsView view;

    public MenuImageController(SettingsView view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        view.showLayout();
    }
}
