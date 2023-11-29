package com.csun.game.interfaces;

import com.csun.game.interfaces.impl.TestInterface;

public enum InterfaceType {
    TEST(0, TestInterface.class);


    private final int key;
    private final Class<? extends Interface> classz;

    InterfaceType(int key, Class<? extends Interface> classz) {
        this.key = key;
        this.classz = classz;
    }

    public int getKey() {
        return key;
    }

    public Class<? extends Interface> getClassz() {
        return classz;
    }
}

