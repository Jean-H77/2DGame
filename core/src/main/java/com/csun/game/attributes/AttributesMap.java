package com.csun.game.attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//@Todo add functionality to add duration to an attribute
public class AttributesMap {

    private final ConcurrentHashMap<AttributeKey<?>, Object> attributes = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(AttributeKey<T> key) {
        return (T) attributes.get(key);
    }

    public <T> void set(AttributeKey<T> key, T value) {
        attributes.put(key, value);
    }

    public <T> void remove(AttributeKey<T> key) {
        attributes.remove(key);
    }

    public <T> boolean hasKey(AttributeKey<T> key) {
        return attributes.containsKey(key);
    }

    public <T> T getOrDefault(AttributeKey<T> key, T defaultValue) {
        return hasKey(key) ? get(key) : defaultValue;
    }

    @SuppressWarnings("unchecked")
    public <T> HashMap<AttributeKey<T>, Object> getPersistentAttributeMap() {
        return attributes
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().getAttributePersist() != null)
            .collect(
                Collectors.toMap(
                    entry -> (AttributeKey<T>) entry.getKey(),
                    Map.Entry::getValue,
                    (existing, replacement) -> replacement,
                    HashMap::new
                )
            );
    }

    public ConcurrentHashMap<AttributeKey<?>, Object> getAttributes() {
        return attributes;
    }
}

