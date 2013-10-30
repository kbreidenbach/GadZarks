package me.breidenbach.gadzarks.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.breidenbach.gadzarks.controllers.GridCellController;
import me.breidenbach.gadzarks.engine.CellDataStructure;
import me.breidenbach.gadzarks.utils.ImageUtils;

/**
 * User: Kevin Breidenbach
 * Date: 10/29/13
 * Time: 3:22 PM
 */
public class GridCellLayout extends RelativeLayout {
    private static final String BACKGROUND_FILE = "cell_single.png";
    private final RelativeLayout.LayoutParams labelLayoutParams;
    private final RelativeLayout.LayoutParams colorLabelLayoutParams;

    public GridCellLayout(Context context, CellDataStructure data) {
        super(context);
        setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 120));
        this.labelLayoutParams = setUpLabelLayoutParams();
        this.colorLabelLayoutParams = setUpColorLabelLayoutParams();
        setCellView(data);
        setBackgroundDrawable(ImageUtils.getImage(this, BACKGROUND_FILE));
        setOnClickListener(createClickListener(data));
    }

    private View.OnClickListener createClickListener(CellDataStructure cellData) {
        return new GridCellController(cellData, this);
    }

    private void setCellView(CellDataStructure cellData) {
        removeAllViews();
        if (cellData != null) {
            setLabel(cellData);
            if (!cellData.setColorLabel().isEmpty()) {
                setColorLabel(cellData);
            }
        }
    }

    private void setColorLabel(CellDataStructure cellData) {
        VerticalTextView colorLabel = new VerticalTextView(getContext(), (Gravity.BOTTOM|Gravity.RIGHT));
        Typeface labelFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Abadi_MT_Condensed.ttf");
        colorLabel.setTypeface(labelFont, Typeface.BOLD);
        colorLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
        colorLabel.setTextColor(Color.rgb(10, 10, 10));
        colorLabel.setText(cellData.setColorLabel());
        addView(colorLabel, colorLabelLayoutParams);

    }

    private void setLabel(CellDataStructure cellData) {
        TextView label = new TextView(getContext());
        Typeface labelFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Abadi_MT_Condensed.ttf");
        label.setTypeface(labelFont, Typeface.BOLD);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        label.setTextColor(Color.rgb(10, 10, 10));
        label.setInputType(InputType.TYPE_CLASS_NUMBER);
        label.setText(Integer.toString(cellData.label()));
        addView(label, labelLayoutParams);
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
