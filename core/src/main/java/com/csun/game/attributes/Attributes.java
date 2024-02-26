package com.csun.game.attributes;

import box2dLight.Light;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.UI.BaseUI;
import com.csun.game.UI.impl.settings.Settings;
import com.csun.game.action.ActionListener;
import com.csun.game.dialogue.Dialogue;
import com.csun.game.misc.MutableInt;
import com.csun.game.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Attributes {

    private Attributes() {

    }

    public static final AttributeKey<String> PLAYER_NAME = new AttributeKey<>(new AttributePersistable("player_name") {
        @Override
        public Object write(Player player, Gson gson) {
            return player.getAttributes().get(PLAYER_NAME);
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            player.getAttributes().set(PLAYER_NAME, jsonElement.getAsString());
        }
    });


    public static final AttributeKey<Vector2> PLAYER_POSITION = new AttributeKey<>(new AttributePersistable("player_position") {
        @Override
        public Object write(Player player, Gson gson) {
            return gson.toJsonTree(player.getAttributes().get(PLAYER_POSITION), Vector2.class);
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            Vector2 vector2 = gson.fromJson(jsonElement, Vector2.class);
            player.getAttributes().set(PLAYER_POSITION, vector2);
            player.getMovementComponent().pos = vector2;
        }
    });

    public static final AttributeKey<String> PLAYER_CURRENT_MAP = new AttributeKey<>(new AttributePersistable("current_map_name") {
        @Override
        public Object write(Player player, Gson gson) {
            return player.getAttributes().get(PLAYER_CURRENT_MAP);
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            player.getAttributes().set(PLAYER_CURRENT_MAP, jsonElement.getAsString());
        }
    });

    public static final AttributeKey<Settings> PLAYER_SETTINGS = new AttributeKey<>(new AttributePersistable("player_settings") {
        @Override
        public Object write(Player player, Gson gson) {
            return player.getAttributes().get(PLAYER_SETTINGS).getPackedSettings();
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            Settings settings = new Settings();
            settings.setPackedSettings(jsonElement.getAsInt());
            player.getAttributes().set(PLAYER_SETTINGS, settings);
        }
    });

    public static final AttributeKey<Map<String, MutableInt>> QUEST_STAGES = new AttributeKey<>(new AttributePersistable("quest_stages") {
        @Override
        public Object write(Player player, Gson gson) {
            return gson.toJson(player.getAttributes().get(QUEST_STAGES), new TypeToken<HashMap<String, MutableInt>>() { }.getType());
        }

        @Override
        public void read(Player player, JsonElement jsonElement, Gson gson) {
            if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
                String jsonString = jsonElement.getAsString();
                jsonElement = gson.fromJson(jsonString, JsonElement.class);
            }

            Type type = new TypeToken<HashMap<String, MutableInt>>() {}.getType();
            Map<String, MutableInt> questStagesMap = gson.fromJson(jsonElement, type);
            player.getAttributes().set(QUEST_STAGES, questStagesMap);
        }
    });

    public static final AttributeKey<Light> PLAYER_LIGHT = new AttributeKey<>();

    public static final AttributeKey<Boolean> PLAYER_IS_LOGGED_IN = new AttributeKey<>();

    public static final AttributeKey<String> NPC_HOVER = new AttributeKey<>();

    public static final AttributeKey<Vector2> WORLD_MOUSE_POSITION = new AttributeKey<>();

    public static final AttributeKey<String> LAST_NPC_CLICKED = new AttributeKey<>();

    public static final AttributeKey<Dialogue> CURRENT_DIALOGUE = new AttributeKey<>();

    public static final AttributeKey<List<BaseUI>> ALL_OPENED_UI = new AttributeKey<>();

    public static final AttributeKey<List<ActionListener>> ACTION_LISTENERS = new AttributeKey<>();
}
