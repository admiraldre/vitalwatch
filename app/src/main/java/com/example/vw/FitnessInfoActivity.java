package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FitnessInfoActivity extends AppCompatActivity {

    private EditText nameField, ageField, weightField, heightField;
    private Spinner genderSpinner;
    private FirebaseFirestore db;
    private String userId; // The UID of the signed-up user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_info);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get the user ID from the Intent
        userId = getIntent().getStringExtra("USER_ID");

        // Initialize UI components
        nameField = findViewById(R.id.nameField);
        ageField = findViewById(R.id.ageField);
        weightField = findViewById(R.id.weightField);
        heightField = findViewById(R.id.heightField);
        genderSpinner = findViewById(R.id.genderSpinner);
        Button saveButton = findViewById(R.id.saveButton);

        // Set up the gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Save button action
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFitnessInfo();
            }
        });
    }

    // Save the user's fitness information to Firestore
    private void saveFitnessInfo() {
        String name = nameField.getText().toString().trim();
        String age = ageField.getText().toString().trim();
        String weight = weightField.getText().toString().trim();
        String height = heightField.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        // Validate input
        if (name.isEmpty() || age.isEmpty() || weight.isEmpty() || height.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare data to save in Firestore
        Map<String, Object> fitnessData = new HashMap<>();
        fitnessData.put("name", name);
        fitnessData.put("age", age);
        fitnessData.put("weight", weight);
        fitnessData.put("height", height);
        fitnessData.put("gender", gender);

        // Store the data in the user's document in Firestore
        DocumentReference userDocRef = db.collection("users").document(userId);
        userDocRef.set(fitnessData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FitnessInfoActivity.this, "Fitness info saved successfully!", Toast.LENGTH_SHORT).show();
                    // Navigate to HomeActivity
                    Intent intent = new Intent(FitnessInfoActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();  // Close FitnessInfoActivity
                } else {
                    Toast.makeText(FitnessInfoActivity.this, "Error saving fitness info", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}