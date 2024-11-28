package com.example.vw.utils;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing and updating charts in the application.
 */
public class ChartManager {

    private final Context context;

    /**
     * Constructor to initialize the ChartManager with a context.
     *
     * @param context the application context
     */
    public ChartManager(Context context) {
        this.context = context;
    }

    /**
     * Updates a PieChart with the steps data.
     *
     * @param pieChart the PieChart to update
     * @param steps    the number of steps taken
     * @param goal     the goal for the steps
     */
    public void updateStepsPieChart(PieChart pieChart, int steps, int goal) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(steps, "Steps Taken"));
        entries.add(new PieEntry(goal - steps, "Remaining"));

        PieDataSet dataSet = new PieDataSet(entries, "Steps Goal");
        dataSet.setColors(new int[]{android.R.color.holo_green_light, android.R.color.holo_red_light}, context);

        dataSet.setValueTextColor(Color.BLACK);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh the chart
    }

    /**
     * Updates a LineChart with heart rate data.
     *
     * @param lineChart       the LineChart to update
     * @param heartRateEntries the list of heart rate entries
     */
    public void updateHeartRateLineChart(LineChart lineChart, List<Entry> heartRateEntries) {
        LineDataSet dataSet = new LineDataSet(heartRateEntries, "Heart Rate");
        dataSet.setColor(android.graphics.Color.RED);
        dataSet.setValueTextColor(android.graphics.Color.BLACK);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }
}
