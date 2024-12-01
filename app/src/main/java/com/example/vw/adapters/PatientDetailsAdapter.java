package com.example.vw.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.R;

import java.util.List;

/**
 * Adapter class for displaying a list of patient details and options in a RecyclerView.
 */
public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.ViewHolder> {

    private List<String> options;
    private OnItemClickListener onItemClickListener;

    // Variables to hold patient details
    private String patientName;
    private String patientAge;

    /**
     * Constructor to initialize the adapter with an item click listener.
     *
     * @param listener The listener to handle item click events.
     */
    public PatientDetailsAdapter(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Updates the list of options and refreshes the RecyclerView.
     *
     * @param options The new list of options to display.
     */
    public void updateOptions(List<String> options) {
        this.options = options;
        notifyDataSetChanged();
    }

    /**
     * Sets the patient details to be displayed.
     *
     * @param name The name of the patient.
     * @param age  The age of the patient.
     */
    public void setPatientDetails(String name, String age) {
        this.patientName = name;
        this.patientAge = age;
    }

    /**
     * Retrieves the patient's name.
     *
     * @return The patient's name.
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Retrieves the patient's age.
     *
     * @return The patient's age.
     */
    public String getPatientAge() {
        return patientAge;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String option = options.get(position);
        holder.optionText.setText(option);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(option));
    }

    @Override
    public int getItemCount() {
        return options != null ? options.size() : 0;
    }

    /**
     * ViewHolder class for holding the views of each item in the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView optionText;

        /**
         * Constructor to initialize the ViewHolder with the item view.
         *
         * @param itemView The view for the individual item.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            optionText = itemView.findViewById(R.id.optionTextView);
        }
    }

    /**
     * Interface for handling item click events.
     */
    public interface OnItemClickListener {
        /**
         * Callback method for item click events.
         *
         * @param option The clicked option text.
         */
        void onItemClick(String option);
    }
}