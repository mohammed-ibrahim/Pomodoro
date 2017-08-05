package com.example.pomo.pomodoro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer timer = null;

    private ProgressBar primaryProgressBar = null;

    private TextView primaryStatusView = null;

    private Button primaryButton = null;

    private static ApplicationState applicationStatus = ApplicationState.PENDING;

    private int counter = 0;

    private int actualCount = 0;

    private static final String TAG = "MainActivity";

    private static int workingCount = 10;

    private static int restingCount = 8;

    private static int snoozingCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primaryProgressBar = (ProgressBar)findViewById(R.id.primary_progress_bar);
        primaryProgressBar.setMax(100);

        primaryStatusView = (TextView)findViewById(R.id.primary_status_view);
        primaryStatusView.setText(String.valueOf(applicationStatus));

        primaryButton = (Button)findViewById(R.id.button);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        workingCount = sharedPreferences.getInt(Constants.VAR_WORKING_COUNT, Constants.DEFAULT_WORKING_COUNT);
        restingCount = sharedPreferences.getInt(Constants.VAR_RESTING_COUNT, Constants.DEFAULT_RESTING_COUNT);
        snoozingCount = sharedPreferences.getInt(Constants.VAR_SNOOZE_COUNT, Constants.DEFAULT_SNOOZE_COUNT);

        if (timer == null) {
            Log.i(TAG, "Initializing the timer.");
            timer = new Timer();
            CustomTimerTask timerTask = new CustomTimerTask();
            timer.schedule(timerTask, 200, 1000);
        }
    }

    class CustomTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    irun();
                }
            });
        }

        public void irun() {
            try {
                Log.i(TAG, "*************************************TIMER*CALLED************************************");
                unSafeExecute();
            } catch (Exception e) {
                java.io.StringWriter sw = new java.io.StringWriter();
                e.printStackTrace(new java.io.PrintWriter(sw));
                Log.e(TAG, "Exception Caught: " + e.getMessage() + "\n" + sw.toString());
            }
        }

        private void unSafeExecute() {
            String message = String.format("STATE: %s COUNTER: %d", String.valueOf(applicationStatus), counter);
            Log.i(TAG, message);

            primaryStatusView.setText(getFriendlyMessageForApplicationStatus(applicationStatus));

            if (applicationStatus.equals(ApplicationState.PAUSED_WHILE_RESTING) ||
                    applicationStatus.equals(ApplicationState.PAUSED_WHILE_WORKING) ||
                    applicationStatus.equals(ApplicationState.PENDING) ||
                    applicationStatus.equals(ApplicationState.STOPPED)) {

                String imsg = String.format("State %s Not doing anything.", String.valueOf(applicationStatus));
                Log.i(TAG, imsg);
                return;
            }

            //Set the progress.
            primaryProgressBar.setMax(actualCount);
            primaryProgressBar.setProgress(actualCount-counter);

            counter = counter - 1;

            //When timer ends.
            if (counter < 1) {
                switch (applicationStatus) {
                    case WORKING:
                    case WORK_SNOOZE:
                        applicationStatus = ApplicationState.WORK_SNOOZE;
                        counter = snoozingCount;
                        actualCount = snoozingCount;
                        primaryButton.setText("TAKE REST");
                        break;

                    case RESTING:
                    case REST_SNOOZE:
                        applicationStatus = ApplicationState.REST_SNOOZE;
                        counter = snoozingCount;
                        actualCount = snoozingCount;
                        primaryButton.setText("START WORKING");
                        break;

                    default:
                    case PENDING:
                    case STOPPED:
                        break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {

        String message = String.format("Button Pressed in applicationStatus: %s", String.valueOf(applicationStatus));
        Log.i(TAG, message);

        switch (applicationStatus) {
            case PENDING:
            case REST_SNOOZE:
                applicationStatus = ApplicationState.WORKING;
                counter = workingCount;
                actualCount = workingCount;
                primaryButton.setText("PAUSE");
                break;

            case WORKING:
                //Toast.makeText(getApplicationContext(),"Already working!!", Toast.LENGTH_LONG).show();
                primaryButton.setText("RESUME WORKING");
                applicationStatus = ApplicationState.PAUSED_WHILE_WORKING;
                break;

            case RESTING:
                primaryButton.setText("RESUME RESTING");
                applicationStatus = ApplicationState.PAUSED_WHILE_RESTING;
                break;

            case PAUSED_WHILE_RESTING:
                applicationStatus = ApplicationState.RESTING;
                primaryButton.setText("PAUSE");
                break;

            case PAUSED_WHILE_WORKING:
                applicationStatus = ApplicationState.WORKING;
                primaryButton.setText("PAUSE");
                break;

            case WORK_SNOOZE:
                applicationStatus = ApplicationState.RESTING;
                counter = restingCount;
                actualCount = restingCount;
                primaryButton.setText("PAUSE");
                break;

            default:
                //Toast.makeText(getApplicationContext(),"Dont know what to do", Toast.LENGTH_LONG).show();
                break;
        }

        primaryStatusView.setText(getFriendlyMessageForApplicationStatus(applicationStatus));
    }

    private String getFriendlyMessageForApplicationStatus(ApplicationState state) {
        switch (state) {
            case WORKING:
                return "Working.. Stay Focused!";

            case RESTING:
                return "Resting...";

            case PAUSED_WHILE_RESTING:
                return "Devp Pending..";

            case PAUSED_WHILE_WORKING:
                return "Gone for a mini break..";

            case PENDING:
                return "Start Focusing? Hit Start..";

            case REST_SNOOZE:
                return "Resting time is over, Please get back to work.";

            case WORK_SNOOZE:
                return "Working time is over, Please take rest.";

            default:
                return "Stopped/Unknown State";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mm_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }


            case R.id.mm_info:
            default:
                Toast.makeText(getApplicationContext(),"Study mate, Pomodoro!", Toast.LENGTH_LONG).show();
                return true;
        }
    }
}
