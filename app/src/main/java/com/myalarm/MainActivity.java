package com.myalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.Intent;
import android.util.Log;

import org.jetbrains.annotations.Contract;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlarmAdapter adapter;
    private List<Alarm> alarmList;

    // Manager of activities definition
    private ActivityResultLauncher<Intent> settingAlarmLauncher;
    private ActivityResultLauncher<Intent> alarmRingLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Necessary items to get the last state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define the launcher for activities
        settingAlarmLauncher = createSettingAlarmLauncher();
        alarmRingLauncher = createAlarmRingLauncher();

        // Define the view to scroll the alarms
        recyclerView = findViewById(R.id.recyclerView);

        // If add button is pressed add an alarm
        findViewById(R.id.addAlarmButton).setOnClickListener(this::addAlarmButtonCallback);

        alarmList = new ArrayList<>();
        adapter = new AlarmAdapter(alarmList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /*
     *  Manage the receiving data of the setting alarm activity
     *  In specific the data of the new or modified alarm
     */
    @NonNull
    private ActivityResultLauncher<Intent> createSettingAlarmLauncher() {
        return registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.d("DEBUG", "Reached the return of create Setting alarm");
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // Check if data is null
                    if (data != null) {
                        // Now we can get the values inside the data
                        int hours = data.getIntExtra("HOURS", 0);
                        int mins= data.getIntExtra("MINS", 0);

                        Log.d("DEBUG", "alarm set time " + hours + " : " + mins);

                        // Change format of the alarm to show on alarm_item
                        String alarmTime = String.format("%02d:%02d", hours, mins);

                        // Set the new alarm on list
                        Alarm new_alarm = new Alarm(alarmTime);
                        alarmList.add(new_alarm);
                        setAlarm(new_alarm);

                        Log.d("LOG", "Alarm time: " + alarmTime + ", Alarm list size: " + alarmList.size());

                        // Finally add the alarm to the screen
                        adapter.notifyItemInserted(alarmList.size() - 1);
                    } else {
                        Log.e("LOG", "Error: Setting activity data received isn't ok");
                    }
                }
            }
        );
    }

    // Manage the receiving data of the ring alarm activity
    @NonNull
    private ActivityResultLauncher<Intent> createAlarmRingLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                    }
                }
        );
    }

    /*
     *  This function gets the values of the given alarm class element and sets an alarm
     *  that will be waiting for ring using PendingIntent and launch the AlarmRingActivity
     *  with the user specifications
     *  *REQUEST CODE for each alarm is format hours * 100 + mins *
     */
    public void setAlarm(Alarm alarm) {
        // Then we create an intent to call AlarmRingActivity on future
        Intent intent = new Intent(this, AlarmRingActivity.class);

        String alarmTime = alarm.getTime();
        // now we set the alarm
        String[] parts = alarmTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int mins = Integer.parseInt(parts[1]);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, hours * 100 + mins, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Configure the alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, mins);
        calendar.set(Calendar.SECOND, 0);   // No need of more precision

        // check if alarm is for tomorrow
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Now finally we set the alarm on the alarm manager of the system
        // First we need to check if we have the permissions to do that
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12 (API 31) and above
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            } else {
                // Request user to enable exact alarms in settings
                Intent perm_request = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(perm_request);
            }
        } else {
            // For older versions (API 24 to 30), directly schedule the alarm
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );

    }

    private void addAlarmButtonCallback(View view) {
        Intent intent = new Intent(this, AlarmSettingsActivity.class);
        settingAlarmLauncher.launch(intent);
    }

    // private void removeAlarm(int position) {
    //     alarmList.remove(position);
    //     adapter.notifyItemRemoved(position);
    // }
}
