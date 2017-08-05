package com.example.pomo.pomodoro;

import android.util.Log;

/**
 * Created by ibrahim on 05/08/17.
 */

public class Utility {

    private static final String TAG = "Utility";

    public static int stringToInteger(String input) {
        try {
            Double dval = Double.parseDouble(input);
            return dval.intValue();
        } catch (Exception e) {

            Log.e(TAG, e.getMessage());

            return 0;
        }
    }
}
