package com.example.vw.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.R;
import com.example.vw.models.ActivityLog;

import java.util.List;

/**
 * Adapter for displaying activity logs in the RecyclerView.
 */
public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ActivityLogViewHolder> {

    private List<ActivityLog> activityLogs;

    // Constructor to initialize the adapter with a list of ActivityLog objects
    public ActivityLogAdapter(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    @Override
    public ActivityLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item_activity_log layout for each activity log item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_log, parent, false);
        return new ActivityLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityLogViewHolder holder, int position) {
        // Get the activity log at the given position
        ActivityLog activityLog = activityLogs.get(position);

        // Set the activity type, duration, and date in the TextViews
        holder.activityTypeTextView.setText(activityLog.getActivityType());
        holder.activityDurationTextView.setText("Duration: " + activityLog.getDuration() + " min");
        holder.activityDateTextView.setText("Date: " + activityLog.getDate());
    }

    @Override
    public int getItemCount() {
        // Return the size of the activityLogs list
        return activityLogs.size();
    }

    // ViewHolder class to hold references to the views for each item
    public static class ActivityLogViewHolder extends RecyclerView.ViewHolder {

        public TextView activityTypeTextView;
        public TextView activityDurationTextView;
        public TextView activityDateTextView;

        public ActivityLogViewHolder(View itemView) {
            super(itemView);

            // Find the TextViews in the item_activity_log layout
            activityTypeTextView = itemView.findViewById(R.id.activityTypeTextView);
            activityDurationTextView = itemView.findViewById(R.id.activityDurationTextView);
            activityDateTextView = itemView.findViewById(R.id.activityDateTextView);
        }
    }

    // Method to update the activity logs in the adapter
    public void updateActivityLogs(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
        notifyDataSetChanged();
    }
}