package de.gemo.stunden.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GUIUtils {

    public static View addSpacer(ViewGroup parent, int color, int height) {
        // add divider
        View divider = new View(GUICreator.APP);
        divider.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
        divider.setBackgroundColor(color);
        parent.addView(divider);
        return divider;
    }

    public static View addSpacer(ViewGroup parent, int height) {
        return addSpacer(parent, GUICreator.BACKGROUND, 20);
    }

    public static View addDivider(ViewGroup parent) {
        return addSpacer(parent, GUICreator.DIVIDER, 1);
    }

    public static List<View> addLabeledText(ViewGroup parent, String topic, String data) {
        // create linear layout
        LinearLayout linearLayout = new LinearLayout(GUICreator.APP.getApplicationContext());
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(GUICreator.BACKGROUND);
        parent.addView(linearLayout);

        // Add first row
        TextView firstRow = new TextView(GUICreator.APP);
        firstRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        firstRow.setText(topic);
        firstRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        firstRow.setPadding(5, 10, 5, 5);
        firstRow.setTextColor(GUICreator.FOREGROUND);
        linearLayout.addView(firstRow);

        // Add second row
        TextView secondRow = new TextView(GUICreator.APP);
        secondRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        secondRow.setText(data);
        secondRow.setPadding(5, 5, 5, 10);
        secondRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        secondRow.setTextColor(GUICreator.FOREGROUND);
        linearLayout.addView(secondRow);

        // create result
        List<View> list = new ArrayList<View>();
        list.add(linearLayout);
        list.add(firstRow);
        list.add(secondRow);
        return list;
    }

}
