package com.example.pomo.pomodoro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahim on 01/08/17.
 */

public class SessionManager {

    List<Epoch> epochs = null;

    public OperationResponse start() {
        if (this.epochs != null) {
            return OperationResponse.failure("Session is already active!");
        }

        this.epochs = new ArrayList<Epoch>();
        this.epochs.add(new Epoch());

        return OperationResponse.success("Success");
    }
}
