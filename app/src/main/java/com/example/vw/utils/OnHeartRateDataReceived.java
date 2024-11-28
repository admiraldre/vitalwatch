package com.example.vw.utils;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

public interface OnHeartRateDataReceived {
    void onDataReceived(List<Entry> heartRateEntries);
}
