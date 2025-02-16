package com.myalarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Class for managing a the list of alarms in a RecyclerView.
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> alarmList;
    
    // // A listener interface for handling delete button clicks
    // private OnDeleteClickListener onDeleteClickListener;

    // // This interface allows communication between the adapter and the activity
    // public interface OnDeleteClickListener {
    //     void onDelete(int position); // Method signature to handle the delete action
    // }

    // Constructor to initialize the alarm list
    public AlarmAdapter(List<Alarm> alarmList) {
        this.alarmList = alarmList; // Set the alarm list from the activity/fragment
    }

    /*
     * This method is called when a new alarm is created to add them to 
     * the interface in the main screen using LayoutInflater which is used to
     * get a new alarm_item xml and add it to the list.
     * It`s like a append function to the list of alarms in main
     */
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the parent layout (recycled view from mainAct) with a new element item_alarm and return it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view); // Return the created view holder
    }

    // This method binds data (alarm details) to the view holder
    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        // Get the alarm object at the current position
        Alarm alarm = alarmList.get(position);

        // Set the alarm's time in the TextView
        holder.alarmTimeText.setText(alarm.getTime());
    }

    @Override
    public int getItemCount() {
        return alarmList.size(); // Return the size of the alarm list
    }

    // This static class holds the views for each alarm item
    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView alarmTimeText; // TextView to display alarm time

        // Constructor to bind views to the holder
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);    // get the element item View from father class
            // Initialize the TextView and Button by finding them in the item layout
            alarmTimeText = itemView.findViewById(R.id.alarmTimeText);
        }
    }
}

