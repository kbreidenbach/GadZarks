package me.breidenbach;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import me.breidenbach.gadzarks.engine.data.*;
import me.breidenbach.gadzarks.engine.time.TimeReader;
import me.breidenbach.gadzarks.engine.time.TimeReaderFactory;

public class HelloAndroidActivity extends Activity {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeReaderFactory trf = new TimeReaderFactory(this);
        TimeReader tr = trf.getFastTimeReader();



        setContentView(R.layout.activity_main);

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

}

