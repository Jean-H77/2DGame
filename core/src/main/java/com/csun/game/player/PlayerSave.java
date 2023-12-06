package com.csun.game.player;

import com.csun.game.attributes.AttributeKey;
import com.csun.game.attributes.AttributePersistable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.csun.game.GameConstants.PLAYER_SAVE_DIR;
import static com.csun.game.GameConstants.PLAYER_SAVE_FILE_NAME;

public class PlayerSave {

    private static final Logger logger = Logger.getLogger(PlayerSave.class.getName());

    private final Player player;

    private final ExecutorService thread = Executors.newSingleThreadExecutor();

    @Inject
    public PlayerSave(Player player) {
        this.player = player;
    }

    public void save() {
        thread.submit(() -> run());
    }

    private <T> void run() {
        HashMap<AttributeKey<T>, Object> persistentAttributes = player.getAttributes().getPersistentAttributeMap();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject properties = new JsonObject();

        for(Map.Entry<AttributeKey<T>, Object> entry : persistentAttributes.entrySet()) {

            AttributePersistable persistable = entry.getKey().getAttributePersist();
            String key = persistable.getKey();

            properties.add(key, gson.toJsonTree(persistable.write(player, gson)));
        }

        try {
            Path path = PLAYER_SAVE_DIR.resolve(PLAYER_SAVE_FILE_NAME);
            Path parent = path.getParent();
            if (parent == null) {
                throw new UnsupportedOperationException("Path must have a parent " + path);
            }
            if (!Files.exists(parent))  Files.createDirectories(parent);

            Files.write(path, gson.toJson(properties).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving player data", e);
        }
    }
}
