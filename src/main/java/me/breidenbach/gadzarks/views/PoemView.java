package me.breidenbach.gadzarks.views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.breidenbach.R;

/**
 * User: Kevin Breidenbach
 * Date: 10/27/13
 * Time: 12:00 PM
 */
public class PoemView  {
    private final RelativeLayout layout;
    private static TextView titleTextView;
    private static TextView poemLineTextView;

    public PoemView(RelativeLayout layout) {
        this.layout = layout;
        titleTextView = (TextView)layout.findViewById(R.id.titleTextView);
        poemLineTextView = (TextView)layout.findViewById(R.id.poemLineTextView);
        setupView();
    }

    // Andoird recommends using SP instead of PT for text size
    private void setupView() {
        Typeface titleFont = Typeface.createFromAsset(layout.getContext().getAssets(), "fonts/Abadi_MT_Condensed.ttf");
        Typeface poemFont = Typeface.createFromAsset(layout.getContext().getAssets(), "fonts/Abadi_MT_Condensed_Light.ttf");
        titleTextView.setTypeface(titleFont);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
        titleTextView.setTextColor(Color.rgb(255,255,255));
        poemLineTextView.setTypeface(poemFont);
        poemLineTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f);
        poemLineTextView.setTextColor(Color.rgb(255,255,255));
    }

    public static void setTitleText(String text) {
        titleTextView.setText(text);
    }

    public static void setPoemLineText(String text) {
        poemLineTextView.setText(text);
    }

}
