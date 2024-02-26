package com.csun.game.misc;

public class MutableInt {

    private int value;

    public MutableInt(int initialValue) {
        this.value = initialValue;
    }

    public MutableInt() {

    }

    public void increment() {
        value++;
    }

    public void decrement() {
        value--;
    }

    public int getValue() {
        return value;
    }

    public int incrementAndGet() {
        return value++;
    }

    public int decrementAndGet() {
        return value--;
    }
}

