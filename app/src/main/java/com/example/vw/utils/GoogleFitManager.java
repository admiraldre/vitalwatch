package com.example.vw.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleFitManager {

    private final Context context;

    public GoogleFitManager(Context context) {
        this.context = context;
    }

    public boolean isUserSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(context) != null;
    }

    public void signIn(AppCompatActivity activity) {
        // Implement sign-in logic for Google Fit
    }

    public void accessGoogleFitData(OnStepsDataReceived stepsCallback, OnHeartRateDataReceived heartRateCallback) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null) return;

        // Steps Data
        Fitness.getHistoryClient(context, account)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataSet -> {
                    int steps = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    stepsCallback.onDataReceived(steps);
                });

        // Heart Rate Data
        Fitness.getHistoryClient(context, account)
                .readData(new com.google.android.gms.fitness.request.DataReadRequest.Builder()
                        .read(DataType.TYPE_HEART_RATE_BPM)
                        .setTimeRange(1, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .build())
                .addOnSuccessListener(dataReadResponse -> {
                    List<Entry> entries = new ArrayList<>();
                    for (DataPoint dp : dataReadResponse.getDataSet(DataType.TYPE_HEART_RATE_BPM).getDataPoints()) {
                        float heartRate = dp.getValue(Field.FIELD_BPM).asFloat();
                        entries.add(new Entry(entries.size(), heartRate));
                    }
                    heartRateCallback.onDataReceived(entries);
                });
    }
}
