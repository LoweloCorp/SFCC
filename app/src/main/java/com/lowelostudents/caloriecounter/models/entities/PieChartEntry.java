package com.lowelostudents.caloriecounter.models.entities;

import com.github.mikephil.charting.data.PieEntry;

public class PieChartEntry extends PieEntry {
    // TODO find nonblocking way to create pie entries with relation to user without dirty data
    public PieChartEntry(float value, String label) {
        super(value, label);
    }
}
