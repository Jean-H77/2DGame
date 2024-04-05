package com.csun.game.player;

import com.badlogic.gdx.Gdx;
import com.csun.game.attributes.AttributeKey;
import com.csun.game.attributes.AttributePersistable;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.csun.game.GameConstants.*;

public class PlayerSave {

    private static final Logger logger = Logger.getLogger(PlayerSave.class.getName());

    private final Player player;

    @Inject
    public PlayerSave(Player player) {
        this.player = player;
    }

    public synchronized <T> CompletableFuture<Void> save() {
        return CompletableFuture.runAsync(() -> {

            HashMap<AttributeKey<T>, Object> persistentAttributes = player.getAttributes().getPersistentAttributeMap();

            Gson gson = GSON_PRETTY_PRINT;
            JsonObject properties = new JsonObject();

            for (Map.Entry<AttributeKey<T>, Object> entry : persistentAttributes.entrySet()) {

                AttributePersistable persistable = entry.getKey().getAttributePersist();
                String key = persistable.getKey();
                Gdx.app.log(key, persistable.getKey());
                properties.add(key, gson.toJsonTree(persistable.write(player, gson)));
            }

            try {
                Path path = Path.of("assets").resolve("player.json");
                Path parent = path.getParent();
                if (parent == null) {
                    throw new UnsupportedOperationException("Path must have a parent " + path);
                }
                if (!Files.exists(parent)) Files.createDirectories(parent);

                Files.write(path, gson.toJson(properties).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while saving player data", e);
            }
        });
    }
}
