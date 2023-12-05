package com.csun.game.attributes;

import com.badlogic.gdx.math.Vector2;
import com.csun.game.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public final class Attributes {

    private Attributes() {

    }

    public static final AttributeKey<String> PLAYER_NAME = new AttributeKey<>(new AttributePersistable("player_name") {
        @Override
        public Object write(Player player, Gson gson) {
            return player.getAttributes().get(Attributes.PLAYER_NAME);
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            player.getAttributes().set(Attributes.PLAYER_NAME, jsonElement.getAsString());
        }
    });

    public static final AttributeKey<Integer> PLAYER_KILLS = new AttributeKey<>(new AttributePersistable("player_kills") {
        @Override
        public Object write(Player player, Gson gson) {
            return player.getAttributes().get(Attributes.PLAYER_KILLS);
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            player.getAttributes().set(Attributes.PLAYER_NAME, jsonElement.getAsString());
        }
    });

    /**
     * This attribute will be used to save the players current position
     * not to be mistaken with players current tile position which should
     * be called with player.getTilePosX() and player.getTilePosY();
     */
    public static final AttributeKey<Vector2> PLAYER_POSITION = new AttributeKey<>(new AttributePersistable("player_position") {
        @Override
        public Object write(Player player, Gson gson) {
            return gson.toJsonTree(player.getAttributes().get(Attributes.PLAYER_POSITION), Vector2.class);
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            Vector2 vector2 = gson.fromJson(jsonElement, Vector2.class);
            player.getAttributes().set(Attributes.PLAYER_POSITION, vector2);
            player.getMovementComponent().pos = vector2;
        }
    });
}
