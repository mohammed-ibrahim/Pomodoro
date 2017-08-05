package com.example.pomo.pomodoro;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {


    private EditText editWorkingCount = null;

    private EditText editRestingCount = null;

    private EditText editSnoozeCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int workingCount = sharedPreferences.getInt(Constants.VAR_WORKING_COUNT, Constants.DEFAULT_WORKING_COUNT);
        int restingCount = sharedPreferences.getInt(Constants.VAR_RESTING_COUNT, Constants.DEFAULT_RESTING_COUNT);
        int snoozingCount = sharedPreferences.getInt(Constants.VAR_SNOOZE_COUNT, Constants.DEFAULT_SNOOZE_COUNT);

        editWorkingCount = (EditText) findViewById(R.id.settings_input_work_interval);
        editRestingCount = (EditText) findViewById(R.id.settings_input_rest_interval);
        editSnoozeCount = (EditText) findViewById(R.id.settings_input_snooze_interval);

        editWorkingCount.setText(String.valueOf(workingCount));
        editRestingCount.setText(String.valueOf(restingCount));
        editSnoozeCount.setText(String.valueOf(snoozingCount));
    }

    public void onSaveSettings(View view) {

        int updatedWorkingCount = Utility.stringToInteger(editWorkingCount.getText().toString());
        int updatedRestingCount = Utility.stringToInteger(editRestingCount.getText().toString());
        int updatedSnoozeCount = Utility.stringToInteger(editSnoozeCount.getText().toString());

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putInt(Constants.VAR_WORKING_COUNT, updatedWorkingCount);
        editor.putInt(Constants.VAR_RESTING_COUNT, updatedRestingCount);
        editor.putInt(Constants.VAR_SNOOZE_COUNT, updatedSnoozeCount);
        editor.commit();

        finish();
    }
}
