package com.example.vw.utils;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A utility class for managing interactions with Google Fit API.
 */
public class GoogleFitManager {

    private final Context context;

    /**
     * Constructor to initialize the GoogleFitManager.
     *
     * @param context the application context
     */
    public GoogleFitManager(Context context) {
        this.context = context;
    }

    /**
     * Checks if the user is signed in to Google.
     *
     * @return true if the user is signed in, false otherwise
     */
    public boolean isUserSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(context) != null;
    }

    /**
     * Signs in the user using Google Fit.
     *
     * @param activity the activity that initiates the sign-in
     * @param launcher the activity result launcher to handle the sign-in intent
     */
    public void signIn(AppCompatActivity activity, ActivityResultLauncher<Intent> launcher) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .addExtension(
                        FitnessOptions.builder()
                                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                                .build()
                )
                .build();

        Intent signInIntent = GoogleSignIn.getClient(activity, gso).getSignInIntent();
        launcher.launch(signInIntent);
    }

    /**
     * Accesses Google Fit data including steps and heart rate.
     *
     * @param stepsCallback     a callback for handling step count data
     * @param heartRateCallback a callback for handling heart rate data
     */
    public void accessGoogleFitData(OnStepsDataReceived stepsCallback, OnHeartRateDataReceived heartRateCallback) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null) return;

        // Retrieve steps data
        Fitness.getHistoryClient(context, account)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataSet -> {
                    int steps = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    stepsCallback.onDataReceived(steps);
                });

        // Retrieve heart rate data
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

    /**
     * Signs out the user from Google Fit.
     *
     * @return a Task representing the sign-out operation
     */
    public Task<Void> signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(context, gso).signOut();
    }

    /**
     * Callback interface for handling steps data.
     */
    public interface OnStepsDataReceived {
        void onDataReceived(int steps);
    }

    /**
     * Callback interface for handling heart rate data.
     */
    public interface OnHeartRateDataReceived {
        void onDataReceived(List<Entry> heartRateEntries);
    }
}
