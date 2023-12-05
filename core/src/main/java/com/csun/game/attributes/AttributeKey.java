package com.csun.game.attributes;

public class AttributeKey<T> {

    private final boolean isPersistent;

    public boolean isPersistent() {
        return isPersistent;
    }

    public AttributeKey(boolean isPersistent) {
        this.isPersistent = isPersistent;
    }

    public AttributeKey() {
        this.isPersistent = false;
    }
}
