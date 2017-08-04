package com.example.pomo.pomodoro;

/**
 * Created by ibrahim on 02/08/17.
 */

public enum SessionState {
    PENDING,
    WORKING,
    RESTING,
    WORK_SNOOZE,
    REST_SNOOZE,
    PAUSED_WHILE_WORKING,
    PAUSED_WHILE_RESTING,
    STOPPED;
}
