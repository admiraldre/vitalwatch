// HomeActivity.java
package com.example.vw;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Display a welcome message or setup the home UI here
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome to VitalWatch Home!");
    }
}
