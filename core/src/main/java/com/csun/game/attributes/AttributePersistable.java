package com.csun.game.attributes;

import com.csun.game.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public abstract class AttributePersistable {
    private final String key;

    protected AttributePersistable(String key) {
        this.key = key;
    }

    public abstract Object write(Player player, Gson gson);
    public abstract void read(Player player, JsonElement jsonElement, Gson gson);

    public String getKey() {
        return key;
    }
}
