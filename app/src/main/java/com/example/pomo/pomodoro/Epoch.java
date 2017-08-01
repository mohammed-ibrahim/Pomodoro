package com.example.pomo.pomodoro;

/**
 * Created by ibrahim on 01/08/17.
 */

public class Epoch {

    public Epoch() {
        this.startedAt = System.currentTimeMillis();
    }

    private EpochType type;

    private Long startedAt;

    public EpochType getType() {
        return type;
    }

    public void setType(EpochType type) {
        this.type = type;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }
}
