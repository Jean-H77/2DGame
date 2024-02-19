package com.csun.game.attributes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AttributeKey<T> {

    public static final List<AttributeKey<?>> persistableAttributes = new CopyOnWriteArrayList<>();

    private final AttributePersistable attributePersistable;

    public AttributePersistable getAttributePersist() {
        return attributePersistable;
    }

    public AttributeKey(AttributePersistable attributePersistable) {
        this.attributePersistable = attributePersistable;
        if(attributePersistable != null) persistableAttributes.add(this);
    }

    public AttributeKey() {
        this.attributePersistable = null;
    }
}
