package com.example.vw.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vw.R;
import com.example.vw.models.EmergencyLog;
import java.util.List;

public class EmergencyLogAdapter extends RecyclerView.Adapter<EmergencyLogAdapter.EmergencyLogViewHolder> {

    private List<EmergencyLog> emergencyLogs;

    public EmergencyLogAdapter(List<EmergencyLog> emergencyLogs) {
        this.emergencyLogs = emergencyLogs;
    }

    @NonNull
    @Override
    public EmergencyLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emergency_log, parent, false);
        return new EmergencyLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyLogViewHolder holder, int position) {
        EmergencyLog log = emergencyLogs.get(position);
        holder.patientNameTextView.setText(log.getPatientName());
//        holder.urgencyTextView.setText(log.getUrgency());
        holder.detailsTextView.setText(log.getDetails());
    }

    @Override
    public int getItemCount() {
        return emergencyLogs.size();
    }

    public void updateData(List<EmergencyLog> newLogs) {
        this.emergencyLogs = newLogs;
        notifyDataSetChanged(); // Notify the RecyclerView to refresh
    }

    static class EmergencyLogViewHolder extends RecyclerView.ViewHolder {
        TextView patientNameTextView;
//        TextView urgencyTextView;
        TextView detailsTextView;

        public EmergencyLogViewHolder(@NonNull View itemView) {
            super(itemView);
            patientNameTextView = itemView.findViewById(R.id.patientNameTextView);
//            urgencyTextView = itemView.findViewById(R.id.urgencyTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
        }
    }
}