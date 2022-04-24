package com.vik.snl.service;

import com.vik.snl.utils.RandomUtils;

public class Dice {
    private int heldBy;
    private Value value;
    private boolean allocated;

    public Dice() {
        allocated = false;
        value = Value.ONE;
    }

    public int getHeldBy() {
        return heldBy;
    }

    public void setHeldBy(int playerId) {
        this.heldBy = playerId;
        allocated = true;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    public int roll() {
        int val = RandomUtils.generateRandomNumber(1, 6);
        value.setVal(val);
        allocated = false;
        return val;
    }

    private enum Value {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);
        int val;

        Value(int i) {
            this.val = val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }
}
