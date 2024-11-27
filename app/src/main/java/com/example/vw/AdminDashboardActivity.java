package com.example.vw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {

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
                // For now, we will show a Toast, but you can redirect to other activities
                Toast.makeText(AdminDashboardActivity.this, "Manage Users clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up View Reports button click listener
        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // For now, we will show a Toast, but you can redirect to other activities
                Toast.makeText(AdminDashboardActivity.this, "View Reports clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up Log Out button click listener
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log out the admin user and return to the login page
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AdminDashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                // Navigate back to the login activity
                finish();  // Close this activity
            }
        });
    }
}