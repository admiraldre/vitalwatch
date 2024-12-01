package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity that handles user sign-up functionality.
 * Allows users to create an account using their email and password.
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private FirebaseAuth auth;
    private Button signUpButton;

    /**
     * Called when the activity is created. Initializes the UI components, sets up the FirebaseAuth instance,
     * and defines the behavior when the sign-up button is clicked.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize UI components
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        signUpButton = findViewById(R.id.signUpButton);

        // Set up the Sign Up button click listener
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                // Check if email or password fields are empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with sign-up
                    signUp(email, password);
                }
            }
        });
    }

    /**
     * Signs up the user with the provided email and password using Firebase Authentication.
     * On success, navigates to the FitnessInfoActivity to complete the user's profile.
     * On failure, displays a toast message indicating the sign-up failure.
     *
     * @param email    The email address provided by the user.
     * @param password The password provided by the user.
     */
    private void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-up success
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(SignUpActivity.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to FitnessInfoActivity to complete profile
                        Intent intent = new Intent(SignUpActivity.this, FitnessInfoActivity.class);
                        intent.putExtra("USER_ID", user.getUid());
                        startActivity(intent);
                        finish();  // Close the current activity
                    } else {
                        // If sign-up fails, display a message to the user.
                        Toast.makeText(SignUpActivity.this, "Sign-up failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}