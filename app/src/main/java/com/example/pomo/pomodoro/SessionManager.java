package com.example.pomo.pomodoro;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahim on 01/08/17.
 */

public class SessionManager {
    /*
     *  Static Region.
     */
    private static SessionManager instance = null;

    private SessionManager() {

    }

    public static SessionManager getInstace() {
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    /*
     *  End of static region.
     */


    //private List<Epoch> epochs = null;

    //private Epoch head = null;

    private Long timeRemaining = 0L;

    private Long actualSlots = 0L;

    private Long slotsWorked = 0L;

    private Long slotsRested = 0L;

    private SessionState state = null;

    public OperationResponse start() {

        if (this.state != null

                ) {
            return OperationResponse.failure("Session is already active.");
        }

//        if (this.epochs != null) {
//            return OperationResponse.failure("Session is already active!");
//        }
//
//        this.epochs = new ArrayList<Epoch>();
//        Epoch current = new Epoch(EpochType.WORK, 30L);
//        this.epochs.add(current);
//        this.head = current;
        this.state = SessionState.WORKING;
        this.timeRemaining = 5L;
        this.actualSlots = 5L;

        return OperationResponse.success("Success");
    }

    public AppAction tick() {
        switch (this.state) {
            case WORKING:
                this.timeRemaining = this.timeRemaining - 1;

                if (this.timeRemaining < 1) {
                    this.state = SessionState.WORK_SNOOZE;
                    this.slotsWorked = this.slotsWorked + 1;
                    this.timeRemaining = 5L;
                    this.actualSlots = 5L;
                    return AppAction.DING;
                }

                return AppAction.DO_NOTHING;


            case RESTING:
                this.timeRemaining = this.timeRemaining - 1;

                if (this.timeRemaining < 1) {
                    this.state = SessionState.REST_SNOOZE;
                    this.slotsRested = this.slotsRested + 1;
                    this.timeRemaining = 5L;
                    this.actualSlots = 5L;
                    return AppAction.DING;
                }

                return AppAction.DO_NOTHING;

            case WORK_SNOOZE:
                this.timeRemaining = this.timeRemaining - 1;

                if (this.timeRemaining < 1) {
                    this.state = SessionState.WORK_SNOOZE;
                    this.slotsWorked = this.slotsWorked + 1;
                    this.timeRemaining = 5L;
                    this.actualSlots = 5L;
                    return AppAction.DING;
                }

                return AppAction.DO_NOTHING;

            case REST_SNOOZE:
                this.timeRemaining = this.timeRemaining - 1;

                if (this.timeRemaining < 1) {
                    this.state = SessionState.REST_SNOOZE;
                    this.slotsRested = this.slotsRested + 1;
                    this.timeRemaining = 5L;
                    this.actualSlots = 5L;
                    return AppAction.DING;
                }

                return AppAction.DO_NOTHING;

            default:
            case PENDING:
            case STOPPED:
                return AppAction.DO_NOTHING;
        }
    }
}
