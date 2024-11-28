package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vw.utils.ChartManager;
import com.example.vw.utils.GoogleFitManager;
import com.example.vw.utils.UserFeatureManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

public class HomeActivity extends AppCompatActivity {

    private TextView stepsTextView, heartRateTextView;
    private PieChart stepsPieChart;
    private LineChart heartRateLineChart;
    private LinearLayout additionalFeaturesContainer;

    private GoogleFitManager googleFitManager;
    private UserFeatureManager userFeatureManager;
    private ChartManager chartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components and managers
        initializeUI();
        initializeManagers();

        if (!googleFitManager.isUserSignedIn()) {
            googleFitManager.signIn(this);
        } else {
            googleFitManager.accessGoogleFitData(steps -> {
                stepsTextView.setText("Steps: " + steps);
                chartManager.updateStepsPieChart(stepsPieChart, steps, 6000);
            }, heartRateEntries -> {
                chartManager.updateHeartRateLineChart(heartRateLineChart, heartRateEntries);
            });
        }

        userFeatureManager.loadUserSettings(getIntent());
        userFeatureManager.displayFeatures(additionalFeaturesContainer);
    }

    private void initializeUI() {
        stepsTextView = findViewById(R.id.stepsTextView);
        heartRateTextView = findViewById(R.id.heartRateTextView);
        stepsPieChart = findViewById(R.id.stepsPieChart);
        heartRateLineChart = findViewById(R.id.heartRateLineChart);
        additionalFeaturesContainer = findViewById(R.id.additionalFeaturesContainer);

        findViewById(R.id.settingsButton).setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void initializeManagers() {
        googleFitManager = new GoogleFitManager(this);
        userFeatureManager = new UserFeatureManager(this);
        chartManager = new ChartManager(this);
    }
}
