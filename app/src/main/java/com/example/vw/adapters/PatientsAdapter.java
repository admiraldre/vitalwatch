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

/**
 * Adapter class for displaying a list of patients in a RecyclerView.
 */
public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientViewHolder> {

    private final List<Patient> patients = new ArrayList<>();
    private OnItemClickListener listener;

    /**
     * Interface for handling click events on patient items.
     */
    public interface OnItemClickListener {
        /**
         * Callback method for handling patient item click events.
         *
         * @param patient The clicked patient object.
         */
        void onItemClick(Patient patient);
    }

    /**
     * Sets the click listener for patient items.
     *
     * @param listener The listener to handle item clicks.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Updates the list of patients displayed in the RecyclerView.
     *
     * @param newPatients The new list of patients to display.
     */
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

    /**
     * ViewHolder class for displaying individual patient items.
     */
    class PatientViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView emailTextView;

        /**
         * Constructor to initialize the ViewHolder with the item view.
         *
         * @param itemView The view for the individual patient item.
         */
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

        /**
         * Binds the patient data to the views in the ViewHolder.
         *
         * @param patient The patient object to display.
         */
        public void bind(Patient patient) {
            nameTextView.setText(patient.getName());
            emailTextView.setText(patient.getEmail());
        }
    }
}