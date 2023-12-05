package com.csun.game.attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public HashMap<AttributeKey<?>, Object> getPersistentAttributeMap() {
        return attributes
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().isPersistent())
            .collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (existing, replacement) -> replacement,
                HashMap::new)
            );
    }

    public HashMap<AttributeKey<?>, Object> getAttributes() {
        return attributes;
    }
}

