package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vw.utils.ChartManager;
import com.example.vw.utils.GoogleFitManager;
import com.example.vw.utils.UserFeatureManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;

/**
 * HomeActivity serves as the main dashboard for the app, displaying fitness data
 * such as steps and heart rate, as well as user-configured features like alarms and reminders.
 */
public class HomeActivity extends AppCompatActivity {

    private TextView stepsTextView, heartRateTextView;
    private PieChart stepsPieChart;
    private LineChart heartRateLineChart;
    private LinearLayout additionalFeaturesContainer;

    private GoogleFitManager googleFitManager;
    private UserFeatureManager userFeatureManager;
    private ChartManager chartManager;

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;

    // Declare ActivityResultLauncher for handling Google Sign-In results
    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class);
                        if (account != null) {
                            fetchGoogleFitData();
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                        // Handle sign-in error
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components and managers
        initializeUI();
        initializeManagers();

        // Check if user is signed in to Google Fit
        if (!googleFitManager.isUserSignedIn()) {
            googleFitManager.signIn(this, googleSignInLauncher);
        } else {
            fetchGoogleFitData();
        }

        // Load user-configured features (e.g., alarms, reminders)
        userFeatureManager.loadUserSettings(getIntent());
        userFeatureManager.displayFeatures(additionalFeaturesContainer);
    }

    /**
     * Initializes UI components.
     */
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

        findViewById(R.id.signOutButton).setOnClickListener(view -> signOut());
    }

    /**
     * Initializes the managers for handling Google Fit, user features, and charts.
     */
    private void initializeManagers() {
        googleFitManager = new GoogleFitManager(this);
        userFeatureManager = new UserFeatureManager(this);
        chartManager = new ChartManager(this);
    }

    /**
     * Fetches Google Fit data, including steps and heart rate.
     */
    private void fetchGoogleFitData() {
        googleFitManager.accessGoogleFitData(
                steps -> {
                    stepsTextView.setText("Steps: " + steps);
                    chartManager.updateStepsPieChart(stepsPieChart, steps, 6000);
                },
                heartRateEntries -> {
                    if (!heartRateEntries.isEmpty()) {
                        float latestHeartRate = heartRateEntries.get(0).getY();
                        heartRateTextView.setText("Heart Rate: " + latestHeartRate + " bpm");
                        chartManager.updateHeartRateLineChart(heartRateLineChart, heartRateEntries);
                    } else {
                        heartRateTextView.setText("Heart Rate: N/A");
                    }
                }
        );
    }

    /**
     * Signs out the user and redirects to the LoginActivity.
     */
    private void signOut() {
        googleFitManager.signOut().addOnCompleteListener(task -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Prevent returning to HomeActivity
        });
    }
}
