package com.csun.game.interfaces;

import com.csun.game.interfaces.impl.TestInterface;

public enum InterfaceType {
    TEST(0, TestInterface.class, true);


    private final int key;
    private final Class<? extends Interface> classz;
    private final boolean cacheable;

    InterfaceType(int key, Class<? extends Interface> classz, boolean cacheable) {
        this.key = key;
        this.classz = classz;
        this.cacheable = cacheable;
    }

    public int getKey() {
        return key;
    }

    public Class<? extends Interface> getClassz() {
        return classz;
    }

    public boolean isCacheable() {
        return cacheable;
    }
}

