package com.example.vw;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vw.adapters.EmergencyLogAdapter;
import com.example.vw.models.EmergencyLog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class EmergencyReportsActivity extends AppCompatActivity {

    private RecyclerView emergencyLogRecyclerView;
    private EmergencyLogAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_reports);

        db = FirebaseFirestore.getInstance();
        emergencyLogRecyclerView = findViewById(R.id.emergencyLogRecyclerView);
        emergencyLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EmergencyLogAdapter(new ArrayList<>());
        emergencyLogRecyclerView.setAdapter(adapter);

        fetchEmergencyLogs();
    }

    private void fetchEmergencyLogs() {
        List<EmergencyLog> emergencyLogs = new ArrayList<>();

        db.collectionGroup("emergencyLogs")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        EmergencyLog log = document.toObject(EmergencyLog.class);
                        emergencyLogs.add(log);
                    }
                    adapter.updateData(emergencyLogs);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to fetch emergency logs.", Toast.LENGTH_SHORT).show();
                });
    }
}