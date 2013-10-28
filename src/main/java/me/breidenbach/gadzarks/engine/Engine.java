package me.breidenbach.gadzarks.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import me.breidenbach.gadzarks.engine.data.*;
import me.breidenbach.gadzarks.engine.time.EpochChangeListener;
import me.breidenbach.gadzarks.engine.time.TimeManager;
import me.breidenbach.gadzarks.engine.time.TimeReader;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 11:15 AM
 */
public class Engine implements EpochChangeListener {
    private final Context context;
    private final TimeReader timeReader;
    private final ZarkSets zarkSets;
    private final List<EngineDataChangeListener> listeners = new ArrayList<>();
    private final List<CellDataStructure> cellData = new ArrayList<>();
    private CellDataStructure[] grid;

    private int daysSinceEpoch;

    public Engine(Context context, TimeReader reader) throws DataException {
        this.context = context;
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

    public void addDataChangeListener(EngineDataChangeListener listener) {
        listeners.add(listener);
    }

    public synchronized CellDataStructure[] getGridData() {
        return grid;
    }

    private synchronized void setUpGridData() {
        grid = new CellDataStructure[16];
        int lastLabelToDisplay = daysSinceEpoch + 1; // always show at least 1
        int firstItemToDisplay = 0;
        if (daysSinceEpoch >= 16) {
            int startPoint = (daysSinceEpoch - 16)/4;
            firstItemToDisplay = (startPoint + 1) * 4;
            if (firstItemToDisplay > cellData.size()) {
                firstItemToDisplay = cellData.size() - 16;
            }
        }

        if (lastLabelToDisplay >= cellData.size()) {
            lastLabelToDisplay = cellData.size();
        }

        int gridIndex = 0;
        for (int index = firstItemToDisplay; index < lastLabelToDisplay; index++) {
            grid[gridIndex++] = cellData.get(index);
        }
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
            listener.dataChanged();
        }
    }
}
