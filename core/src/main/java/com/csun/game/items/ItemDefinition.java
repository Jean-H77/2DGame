package com.csun.game.items;

import java.util.HashMap;

public record ItemDefinition(
    int id,
    String name,
    String description,
    ItemType itemType,
    String[] options
) {

    private static final HashMap<Integer, ItemDefinition> definitions = new HashMap<>();

    public static ItemDefinition getDefinition(int itemId) {
        return definitions.get(itemId);
    }

    public static void loadDefinitions() {

    }
}
