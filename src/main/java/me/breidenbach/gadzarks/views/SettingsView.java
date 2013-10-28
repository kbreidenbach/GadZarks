package me.breidenbach.gadzarks.views;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import me.breidenbach.R;
import me.breidenbach.gadzarks.controllers.MenuImageController;
import me.breidenbach.gadzarks.engine.time.TimeManager;

import java.util.Date;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 12:01 PM
 */
public class SettingsView {
    private final RelativeLayout layout;
    private final ImageView menuImageView;
    private final TimeManager timeManager;

    public SettingsView(RelativeLayout layout, ImageView menuImageView, TimeManager timeManager) {
        this.layout = layout;
        this.menuImageView = menuImageView;
        this.timeManager = timeManager;
        setUpMenuClick();
        setUpDateButton();
        setUpCancelButton();
    }

    public void hideLayout() {
        layout.setVisibility(RelativeLayout.GONE);
    }

    public void showLayout() {
        layout.setVisibility(RelativeLayout.VISIBLE);
    }

    public void setDate(Date date) {
        timeManager.setDate(date);
    }

    private void setUpMenuClick() {
        menuImageView.setOnClickListener(new MenuImageController(this));
    }

    private void setUpDateButton() {
        Button dateButton = (Button) layout.findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataPicker();
            }
        });
    }

    private void setUpCancelButton() {
        Button dateButton = (Button) layout.findViewById(R.id.cancelButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLayout();
            }
        });
    }
    private void addDataPicker() {
        Date epoch = timeManager.getTimeReader().epoch();
        DatePickerDialog datePicker = new DatePickerDialog(layout.getContext(), dateSetListener,
                epoch.getYear(), epoch.getMonth(), epoch.getDay());
        datePicker.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                setDate(new Date(year,monthOfYear,dayOfMonth,0,0));
            }
        };
}
