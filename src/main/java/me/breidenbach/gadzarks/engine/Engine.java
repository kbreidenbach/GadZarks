package me.breidenbach.gadzarks.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import me.breidenbach.gadzarks.engine.data.*;
import me.breidenbach.gadzarks.engine.time.EpochChangeListener;
import me.breidenbach.gadzarks.engine.time.TimeReader;
import me.breidenbach.gadzarks.views.GridViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 11:15 AM
 */
public class Engine implements EpochChangeListener {
    public static final int NUMBER_OF_CELLS = 16;
    public static final int CELLS_IN_ROW = 4;

    private final TimeReader timeReader;
    private final ZarkSets zarkSets;
    private final List<EngineDataChangeListener> listeners = new ArrayList<>();
    private final List<CellDataStructure> cellData = new ArrayList<>();

    private int daysSinceEpoch;

    public Engine(Context context, TimeReader reader) throws DataException {
        reader.addListener(this);
        this.timeReader = reader;
        this.zarkSets = new SetUpData(context).zarkSets();
        this.daysSinceEpoch = timeReader.getDaysSinceEpoch();
        setUpList();
        setUpGridData();
    }

    @Override
    public void epochChanged() {
        daysSinceEpoch = timeReader.getDaysSinceEpoch();
        setUpGridData();
    }

    @Override
    public void useFastChanged() {
        daysSinceEpoch = timeReader.getDaysSinceEpoch();
        setUpGridData();
    }

    public List<CellDataStructure> cellData() {
        return cellData;
    }

    public void addDataChangeListener(EngineDataChangeListener listener) {
        listeners.add(listener);
    }

    public synchronized CellDataStructure[] getGridData() {
        return null;
    }

    private synchronized void setUpGridData() {
        notifyDataChange();
    }

    private void setUpList() {
        for (int i = 1; i <= zarkSets.numberOfZarkSets(); i++) {
            ZarkSet zarkSet = zarkSets.getZarkSet(i);
            Drawable header = zarkSet.header();
            String color = zarkSet.color();
            boolean first = true;
            for (int n = 1; n <= zarkSet.count(); n++) {
                Zark zark = zarkSet.getZark(n);
                String title = zark.title();
                String poemLine = zark.poemLine();
                String zarkImageFile = zark.zarkImageFile();
                CellDataStructure cell = new CellDataStructure(n, title, poemLine, header, zarkImageFile);
                if (first) {
                    cell.setSetColorLabel(color);
                    first = false;
                }
                cellData.add(cell);
            }
        }
    }

    private void notifyDataChange() {
        for (EngineDataChangeListener listener : listeners) {
            listener.dataChanged(daysSinceEpoch);
        }
    }
}
