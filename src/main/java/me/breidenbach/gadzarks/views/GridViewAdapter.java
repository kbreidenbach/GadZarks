package me.breidenbach.gadzarks.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import me.breidenbach.gadzarks.engine.CellDataStructure;
import me.breidenbach.gadzarks.engine.EngineDataChangeListener;
import me.breidenbach.gadzarks.engine.data.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Kevin Breidenbach
 * Date: 10/29/13
 * Time: 3:09 PM
 */
public class GridViewAdapter extends BaseAdapter implements EngineDataChangeListener {

    private final Context context;
    private final GridView view;
    private final List<CellDataStructure> cellDataList;
    private List<CellDataStructure> displayDataList;

    private int viewable = 1;
    private int daysSinceEpoch = 1;

    public GridViewAdapter(Context context, GridView view, List<CellDataStructure> cellDataList,
                           int daysSinceEpoch) throws DataException {
        this.context = context;
        this.view = view;
        this.cellDataList = cellDataList;
        this.daysSinceEpoch = daysSinceEpoch;
        this.viewable = findLastViewable(daysSinceEpoch);
    }

    @Override
    public int getCount() {
        setData();
        return displayDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return cellDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (position > daysSinceEpoch) {
            return new GridCellLayout(context, null);
        } else{
            return new GridCellLayout(context, cellDataList.get(position));
        }
    }

    @Override
    public void dataChanged(int daysSinceEpoch) {
        this.daysSinceEpoch = daysSinceEpoch;
        this.viewable = findLastViewable(daysSinceEpoch);
        this.notifyDataSetChanged();
        view.invalidateViews();
    }

    private void setData() {
        displayDataList = new ArrayList<>();
        for (int i=0; i <= viewable; i++) {
            displayDataList.add(i, cellDataList.get(i));
        }
    }

    private int findLastViewable(int daysSinceEpoch) {
        int lastViewable = (((daysSinceEpoch / 4) + 1) * 4) - 1;
        if (lastViewable < 15) { lastViewable = 15; }
        return lastViewable;
    }
}
