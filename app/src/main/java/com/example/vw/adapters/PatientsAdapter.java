package com.example.vw.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.R;
import com.example.vw.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientViewHolder> {

    private final List<Patient> patients = new ArrayList<>();
    private OnItemClickListener listener;

    // Listener interface for handling item clicks
    public interface OnItemClickListener {
        void onItemClick(Patient patient);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updatePatients(List<Patient> newPatients) {
        patients.clear();
        patients.addAll(newPatients);
        notifyDataSetChanged(); // Notify the RecyclerView of data changes
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.bind(patient);
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView emailTextView;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.patientName);
            emailTextView = itemView.findViewById(R.id.patientEmail);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(patients.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Patient patient) {
            nameTextView.setText(patient.getName());
            emailTextView.setText(patient.getEmail());
        }
    }
}