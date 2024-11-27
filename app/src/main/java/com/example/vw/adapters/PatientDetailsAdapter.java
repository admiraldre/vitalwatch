package com.example.vw.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.R;

import java.util.List;

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.ViewHolder> {

    private List<String> options;
    private OnItemClickListener onItemClickListener;

    // Variables to hold patient details
    private String patientName;
    private String patientAge;
    private String patientFitnessGoal;

    public PatientDetailsAdapter(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void updateOptions(List<String> options) {
        this.options = options;
        notifyDataSetChanged();
    }

    public void setPatientDetails(String name, String age, String fitnessGoal) {
        this.patientName = name;
        this.patientAge = age;
        this.patientFitnessGoal = fitnessGoal;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public String getPatientFitnessGoal() {
        return patientFitnessGoal;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView optionText;

        public ViewHolder(View itemView) {
            super(itemView);
            optionText = itemView.findViewById(R.id.optionTextView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String option);
    }
}