package com.dtech.smartpulsa.data;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by aris on 10/03/17.
 */

public class MyPercentFormatter implements IValueFormatter, IAxisValueFormatter {

    protected DecimalFormat mFormat;

    public MyPercentFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    public MyPercentFormatter(DecimalFormat format) {
        this.mFormat = format;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if(value > 0) {
            return mFormat.format(value) + " %";
        } else {
            return "";
        }
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if(value > 0) {
            return mFormat.format(value) + " %";
        } else {
            return "";
        }
    }



}
