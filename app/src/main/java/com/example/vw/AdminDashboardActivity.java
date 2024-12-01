package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity class representing the Admin Dashboard, where admins can manage users,
 * view reports, and log out.
 */
public class AdminDashboardActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. Sets up the admin dashboard UI and click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize UI components
        Button manageUsersButton = findViewById(R.id.manageUsersButton);
        Button viewReportsButton = findViewById(R.id.viewReportsButton);
        Button logOutButton = findViewById(R.id.logOutButton);

        // Set up Manage Users button click listener
        manageUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to ManageUsersActivity
                Intent intent = new Intent(AdminDashboardActivity.this, ManageUsersActivity.class);
                startActivity(intent);
            }
        });

        // Set up View Reports button click listener
        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Placeholder for future implementation of viewing reports
                Intent intent = new Intent(AdminDashboardActivity.this, EmergencyReportsActivity.class);
                startActivity(intent);
            }
        });

        // Set up Log Out button click listener
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log out the admin user and return to the login page
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AdminDashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                // Close this activity and navigate back to the login screen
                finish();
            }
        });
    }
}