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

public class SignUpActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private FirebaseAuth auth;
    private Button signUpButton;

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

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(email, password);
                }
            }
        });
    }

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
                        finish();
                    } else {
                        // If sign-up fails, display a message to the user.
                        Toast.makeText(SignUpActivity.this, "Sign-up failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}