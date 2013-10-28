package me.breidenbach;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import me.breidenbach.gadzarks.engine.Engine;
import me.breidenbach.gadzarks.engine.data.DataException;
import me.breidenbach.gadzarks.engine.time.TimeManager;
import me.breidenbach.gadzarks.engine.time.TimeReader;
import me.breidenbach.gadzarks.views.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;

public class GadZarksActivity extends Activity {

    private static final String MENU_IMAGE_FILE = "955logo.png";
    private static final String LOG_TAG = "GADZARKS_INITIALIZER";

    private TimeReader timeReader;
    private ImageView menuImageView;
    private PoemView poemView;
    private GridView gridView;
    private SettingsView settingsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeManager trf = new TimeManager(this);
        trf.useFastTimeReader(true);
        //trf.setDate(GregorianCalendar.getInstance().getTime());
        timeReader = trf.getTimeReader();

        setContentView(R.layout.activity_main);
        setHeaderView();
        setZarkView();
        setPoemView();
        setGridView();
        setMenuImage();
        setSettingsView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(me.breidenbach.R.menu.main, menu);
	return true;
    }

    private void setMenuImage() {
        try {
            InputStream ims = getAssets().open(MENU_IMAGE_FILE);
            menuImageView = (ImageView) findViewById(R.id.menuImageView);
            menuImageView.setImageDrawable(Drawable.createFromStream(ims, null));
        } catch (IOException e) {
            Log.wtf(LOG_TAG, "Unable to load menu image");
        }
    }

    private void setPoemView() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.poemLayout);
        poemView = new PoemView(layout);
    }

    private void setGridView() {
        GridLayout grid = (GridLayout) findViewById(R.id.mainLayout);
        try {
            Engine engine = new Engine(this, timeReader);
            gridView = new GridView(grid, engine);
        } catch (DataException e) {
            Log.wtf(LOG_TAG, "Error loading data: " + e.getMessage());
        }
    }

    private void setHeaderView() {
        ImageView headerView = (ImageView) findViewById(R.id.headerImageView);
        HeaderView.setHeaderView(headerView);
    }

    private void setZarkView() {
        ImageView zarkView = (ImageView) findViewById(R.id.zarkImageView);
        ZarkView.setZarkView(zarkView);
    }

    private void setSettingsView() {
        RelativeLayout settingsLayout = (RelativeLayout) findViewById(R.id.settingsLayout);
        settingsView = new SettingsView(settingsLayout);
        settingsView.hideLayout();
    }

}

