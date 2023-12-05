package com.csun.game.attributes;

import java.util.HashMap;
import java.util.Map;

public class AttributesMap {

    private final HashMap<AttributeKey<?>, Object> attributes = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(AttributeKey<T> key) {
        return (T) attributes.get(key);
    }

    public <T> void set(AttributeKey<T> key, T value) {
        attributes.put(key, value);
    }

    public <T> boolean hasKey(AttributeKey<T> key) {
        return attributes.containsKey(key);
    }

    @SuppressWarnings("unused")
    public HashMap<AttributeKey<?>, Object> getPersistentAttributeMap() {
        HashMap<AttributeKey<?>, Object> map = new HashMap<>();
        for(Map.Entry<AttributeKey<?>, Object> entry : attributes.entrySet()) {
            if (entry.getKey().isPersistent()) map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public HashMap<AttributeKey<?>, Object> getAttributes() {
        return attributes;
    }
}

