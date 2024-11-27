package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.emailField);
        passwordEditText = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);

        // Click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Hardcoded username and password check
                if (username.equals("admin") && password.equals("admin123")) {
                    // If credentials are correct, navigate to Admin Dashboard
                    Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish(); // Close Admin Login Activity after login
                } else {
                    // If credentials are incorrect, show a toast message
                    Toast.makeText(AdminLoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

                // Firebase login logic (commented out for now)
                /*
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(AdminLoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Navigate to Admin Dashboard if login is successful
                                Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Show error message if login fails
                                Toast.makeText(AdminLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                */
            }
        });
    }
}