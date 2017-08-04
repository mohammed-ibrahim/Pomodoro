package com.example.pomo.pomodoro;

/**
 * Created by ibrahim on 01/08/17.
 */

public class Epoch {

    public Epoch(EpochType epochType, Long slots) {
        this.numSlots = slots;
        this.remainingSlots = slots;
        this.type = epochType;
    }

    public boolean tick() {
        this.remainingSlots = this.remainingSlots - 1;

        return (this.remainingSlots < 1) ? true : false;
    }

    private EpochType type;

    private Long numSlots;

    private Long remainingSlots;

    public EpochType getType() {
        return type;
    }

    public void setType(EpochType type) {
        this.type = type;
    }


}
