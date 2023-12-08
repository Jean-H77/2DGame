package com.csun.game.attributes;

import java.util.ArrayList;
import java.util.List;

public class AttributeKey<T> {

    public static final List<AttributeKey<?>> persistableAttributes = new ArrayList<>();

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
