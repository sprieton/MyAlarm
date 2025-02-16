// AlarmSettingsActivity.java

package com.myalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmRingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Necessary to load the activity XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_ring);

        // add the buttons functions
        findViewById(R.id.likeAlarmButton).setOnClickListener(this::postponeAlarmCallback);
        findViewById(R.id.disLikeAlarmButton).setOnClickListener(this::endAlarmCallback);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    // If user likes the alarm we save the alarm info and send it to main, then finish
    private void postponeAlarmCallback(View view) {
        // Intent result_intent = new Intent(this, MainActivity.class);

        // save the alarm preferences to send them to main
        // int selected_hours = picker_hours.getValue();
        // int selected_mins = picker_mins.getValue();

        // result_intent.putExtra("HOURS", selected_hours);
        // result_intent.putExtra("MINS", selected_mins);

        // setResult(RESULT_OK, result_intent);
        finish();
    }

    // If user don't like the alarm we return without save the alarm
    private void endAlarmCallback(View view) {
        finish();
    }
}
