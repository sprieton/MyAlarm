// AlarmSettingsActivity.java

package com.myalarm;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmSettingsActivity extends AppCompatActivity{

    private NumberPicker picker_hours;
    private NumberPicker picker_mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Necessary to load the activity XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_setting);

        // add the buttons functions
        findViewById(R.id.likeAlarmButton).setOnClickListener(this::likeAlarmCallback);
        findViewById(R.id.disLikeAlarmButton).setOnClickListener(this::dislikeAlarmCallback);

        // configure the Pickers
        picker_hours = findViewById(R.id.hourPicker);
        picker_mins = findViewById(R.id.minutePicker);
        picker_hours.setMinValue(0);
        picker_hours.setMaxValue(23);
        picker_mins.setMinValue(0);
        picker_mins.setMaxValue(59);

        // To change format of numbers and get always two digits
        NumberPicker.Formatter two_digit_formatter = value -> String.format("%02d", value);
        picker_hours.setFormatter(two_digit_formatter);
        picker_mins.setFormatter(two_digit_formatter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    // If user likes the alarm we save the alarm info and send it to main, then finish
    private void likeAlarmCallback(View view) {
        Intent result_intent = new Intent(this, MainActivity.class);

        // save the alarm preferences to send them to main
        int selected_hours = picker_hours.getValue();
        int selected_mins = picker_mins.getValue();

        result_intent.putExtra("HOURS", selected_hours);
        result_intent.putExtra("MINS", selected_mins);

        setResult(RESULT_OK, result_intent);
        finish();
    }

    // If user don't like the alarm we return without save the alarm
    private void dislikeAlarmCallback(View view) {
        finish();
    }
}
