package com.csun.game.attributes;

import java.util.ArrayList;
import java.util.List;

public class AttributeKey<T> {

    public static final List<AttributeKey<?>> PERSISTABLE_ATTRIBUTES = new ArrayList<>();

    private final AttributePersistable attributePersistable;

    public AttributePersistable getAttributePersist() {
        return attributePersistable;
    }

    public AttributeKey(AttributePersistable attributePersistable) {
        this.attributePersistable = attributePersistable;
        if(attributePersistable != null) PERSISTABLE_ATTRIBUTES.add(this);
    }

    public AttributeKey() {
        this.attributePersistable = null;
    }
}
