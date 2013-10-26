package me.breidenbach;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import me.breidenbach.gadzarks.engine.data.*;

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
        try {
            Log.wtf("GADZARKS", "Reading PList");
            SetUpData data = new SetUpData(this);
            ZarkSets zarkSets = data.zarkSets();
            Log.wtf("GADZARKS", "Number of Zark Sets: " + zarkSets.numberOfZarkSets());
            for (int i = 1; i <= zarkSets.numberOfZarkSets(); i++) {
                Log.wtf("GADZARKS", "\tZark Set " + i);
                ZarkSet zarkSet = zarkSets.getZarkSet(i);
                Log.wtf("GADZARKS", "\tZark Set " + i);
                int count = zarkSet.count();
                String color = zarkSet.color();
                Log.wtf("GADZARKS", "\tZark Set Color " + color);
                Log.wtf("GADZARKS", "\tZark Set Count " + count);
                for (int n = 1; n <= count; n++) {
                    Log.wtf("GADZARKS", "\t\tZark " + n);
                    Zark zark = zarkSet.getZark(n);
                    String title = zark.title();
                    String poemLine = zark.poemLine();
                    Log.wtf("GADZARKS", "\t\tTitle " + title);
                    Log.wtf("GADZARKS", "\t\tPoemLine " + poemLine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(me.breidenbach.R.menu.main, menu);
	return true;
    }

}

