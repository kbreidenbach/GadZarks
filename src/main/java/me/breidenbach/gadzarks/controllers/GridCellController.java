package me.breidenbach.gadzarks.controllers;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import me.breidenbach.gadzarks.engine.CellDataStructure;
import me.breidenbach.gadzarks.utils.ImageUtils;
import me.breidenbach.gadzarks.views.HeaderView;
import me.breidenbach.gadzarks.views.PoemView;
import me.breidenbach.gadzarks.views.ZarkView;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 5:50 PM
 */
public class GridCellController implements View.OnClickListener {
    private static final String DISC_FILE = "selection_disc.png";

    // Last layout to be clicked
    private static ImageView lastClickedCell;

    private final RelativeLayout.LayoutParams layoutParams;
    private final CellDataStructure cellData;
    private final RelativeLayout layout;

    public GridCellController(CellDataStructure cellData, RelativeLayout layout) {
        this.cellData = cellData;
        this.layout = layout;
        this.layoutParams = setUpLayoutParams();
    }

    @Override
    public void onClick(View v) {
        displayDisc();
        if (cellData != null) {
            if (cellData.headerImage() != null) {
                displayColorHeader();
            }
            if (!cellData.zarkImageFile().isEmpty()) {
                displayZarkImage();
            }
            if (!cellData.title().isEmpty()) {
                PoemView.setTitleText(cellData.title());
            }
            if (!cellData.poemLine().isEmpty()) {
                PoemView.setPoemLineText(cellData.poemLine());
            }
        } else {
            HeaderView.setImage(null);
            ZarkView.setImage(null);
            PoemView.setPoemLineText("");
            PoemView.setTitleText("");
        }
    }

    private void displayDisc() {
        Drawable disc = ImageUtils.getImage(layout, DISC_FILE);
        if (lastClickedCell != null) {
            lastClickedCell.setImageDrawable(null);
        }
        if (disc != null) {
            ImageView view = new ImageView(layout.getContext());
            view.setImageDrawable(disc);
            layout.addView(view, layoutParams);
            lastClickedCell = view;
        }
    }

    private void displayColorHeader() {
        HeaderView.setImage(cellData.headerImage());
    }

    private void displayZarkImage() {
        Drawable zarkImage = ImageUtils.getImage(layout, cellData.zarkImageFile());
        ZarkView.setImage(zarkImage);
    }

    private RelativeLayout.LayoutParams setUpLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.
                LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
        return layoutParams;
    }
}
