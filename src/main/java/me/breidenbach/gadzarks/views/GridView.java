package me.breidenbach.gadzarks.views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.breidenbach.gadzarks.controllers.GridCellController;
import me.breidenbach.gadzarks.engine.CellDataStructure;
import me.breidenbach.gadzarks.engine.Engine;
import me.breidenbach.gadzarks.engine.EngineDataChangeListener;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 3:46 PM
 */
public class GridView implements EngineDataChangeListener {
    private final RelativeLayout.LayoutParams labelLayoutParams;
    private final RelativeLayout.LayoutParams colorLabelLayoutParams;
    private final GridLayout gridLayout;
    private final Engine engine;

    public GridView(GridLayout gridLayout, Engine engine) {
        this.gridLayout = gridLayout;
        this.engine = engine;
        this.labelLayoutParams = setUpLabelLayoutParams();
        this.colorLabelLayoutParams = setUpColorLabelLayoutParams();
        engine.addDataChangeListener(this);
        setUpGrid();
    }

    @Override
    public void dataChanged() {
        setUpGrid();
    }

    private void setUpGrid() {
        CellDataStructure[] cellDataArray = engine.getGridData();
        RelativeLayout cellLayout;

        for (int i = 0; i < Engine.NUMBER_OF_CELLS; i++) {
            CellDataStructure cellData = cellDataArray[i];
            cellLayout = (RelativeLayout) gridLayout.getChildAt(i);

            if (cellData == null) {
                cellLayout.removeAllViews();
                cellLayout.setOnClickListener(null);
            } else {
                setCellView(cellLayout, cellData);
            }
            cellLayout.setOnClickListener(createClickListener(cellLayout, cellData));
        }
    }

    private View.OnClickListener createClickListener(RelativeLayout cellLayout, CellDataStructure cellData) {
        return new GridCellController(cellData, cellLayout);
    }

    private void setCellView(RelativeLayout cellLayout, CellDataStructure cellData) {
        setLabel(cellLayout, cellData);
        if (!cellData.setColorLabel().isEmpty()) {
            setColorLabel(cellLayout, cellData);
        }
    }

    private void setColorLabel(RelativeLayout cellLayout, CellDataStructure cellData) {
        VerticalTextView colorLabel = new VerticalTextView(cellLayout.getContext(), (Gravity.BOTTOM|Gravity.RIGHT));
        Typeface labelFont = Typeface.createFromAsset(cellLayout.getContext().getAssets(), "fonts/Abadi_MT_Condensed.ttf");
        colorLabel.setTypeface(labelFont, Typeface.BOLD);
        colorLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
        colorLabel.setTextColor(Color.rgb(10, 10, 10));
        colorLabel.setText(cellData.setColorLabel());
        cellLayout.addView(colorLabel, colorLabelLayoutParams);

    }

    private void setLabel(RelativeLayout cellLayout, CellDataStructure cellData) {
        TextView label = new TextView(cellLayout.getContext());
        Typeface labelFont = Typeface.createFromAsset(cellLayout.getContext().getAssets(), "fonts/Abadi_MT_Condensed.ttf");
        label.setTypeface(labelFont, Typeface.BOLD);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        label.setTextColor(Color.rgb(10, 10, 10));
        label.setText(Integer.toString(cellData.label()));
        cellLayout.addView(label, labelLayoutParams);
    }

    private RelativeLayout.LayoutParams setUpLabelLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.
                LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
        return layoutParams;
    }

    private RelativeLayout.LayoutParams setUpColorLabelLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.
                LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        layoutParams.leftMargin = 10;
        return layoutParams;
    }

}
