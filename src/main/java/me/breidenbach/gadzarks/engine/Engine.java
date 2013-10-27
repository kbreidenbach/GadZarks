package me.breidenbach.gadzarks.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    public Engine(Context context) throws DataException {
        this.context = context;
        this.timeReader = new TimeManager(context).getTimeReader();
        this.zarkSets = new SetUpData(context).zarkSets();
        this.daysSinceEpoch = timeReader.getDaysSinceEpoch();
        setUpList();
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
        }

        int gridIndex = 0;
        for (int index = firstItemToDisplay; index < lastLabelToDisplay; index++) {
            grid[gridIndex++] = cellData.get(index);
        }
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
                Drawable zarkImage = zark.zarkImage();
                CellDataStructure cell = new CellDataStructure(n, title, poemLine, header, zarkImage);
                if (first) {
                    cell.setSetColorLabel(color);
                    first = false;
                }
            }
        }
    }


    public class CellDataStructure {
        private final int label;
        private final String title;
        private final String poemLine;
        private final Drawable headerImage;
        private final Drawable zarkImage;
        private String setColorLabel = "";

        private CellDataStructure(int label, String title, String poemLine,
                                  Drawable headerImage, Drawable zarkImage) {
            this.label = label;
            this.title = title;
            this.poemLine = poemLine;
            this.headerImage = headerImage;
            this.zarkImage = zarkImage;
        }

        public void setSetColorLabel(String setColorLabel) {
            this.setColorLabel = setColorLabel;
        }

        public String setColorLabel() { return setColorLabel; }
        public int label() { return label; }
        public String title() { return title; }
        public String poemLine() { return poemLine; }
        public Drawable headerImage() { return headerImage; }
        public Drawable zarkImage() { return zarkImage; }

    }

    private void notifyDataChange() {
        for (EngineDataChangeListener listener : listeners) {
            listener.dataChanged();
        }
    }
}
