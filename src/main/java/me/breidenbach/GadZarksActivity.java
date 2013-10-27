package me.breidenbach;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import me.breidenbach.gadzarks.engine.time.TimeManager;
import me.breidenbach.gadzarks.engine.time.TimeReader;
import me.breidenbach.gadzarks.views.PoemView;

import java.io.IOException;
import java.io.InputStream;

public class GadZarksActivity extends Activity {

    private static final String MENU_IMAGE_FILE = "955logo.png";
    private static final String LOG_TAG = "GADZARKS_INITIALIZER";

    private ImageView menuImageView;
    private PoemView poemView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeManager trf = new TimeManager(this);
        trf.useFastTimeReader(true);
        TimeReader tr = trf.getTimeReader();

        setContentView(R.layout.activity_main);
        setPoemView();
        setMenuImage();


        /*while (true) {
            Log.wtf("GadZarks", "Minutes since Epoch: " + tr.getDaysSinceEpoch());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } */
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
        poemView.setTitleText("Speak");
        poemView.setPoemLineText("Eye to eye when speaking");
    }

}

