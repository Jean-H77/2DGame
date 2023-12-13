package com.csun.game.player;

import com.csun.game.attributes.AttributeKey;
import com.csun.game.attributes.AttributePersistable;
import com.google.gson.*;
import com.google.inject.Inject;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.csun.game.GameConstants.*;

public class PlayerLoad {

    private static final Logger logger = Logger.getLogger(PlayerLoad.class.getName());


    private final Player player;

    @Inject
    public PlayerLoad(Player player) {
        this.player = player;
    }

    public synchronized CompletableFuture<Void> loadPlayerAttributes() {
        return CompletableFuture.runAsync(() -> {
            try {
                final Path path = PLAYER_SAVE_DIR.resolve(PLAYER_SAVE_FILE_NAME);

                if (!Files.exists(path)) return;

                JsonElement jsonElement;
                try (final Reader reader = new FileReader(path.toFile())) {
                    jsonElement = JsonParser.parseReader(reader);
                }

                JsonObject jsonObject = jsonElement.getAsJsonObject();
                List<AttributeKey<?>> attributes = AttributeKey.persistableAttributes;

                for (AttributeKey<?> property : attributes) {
                    AttributePersistable persistable = property.getAttributePersist();
                    String label = persistable.getKey();
                    JsonElement propertyElement = jsonObject.get(label);
                    if (propertyElement == null || propertyElement.isJsonNull()) continue;
                    persistable.read(player, propertyElement, GSON_PRETTY_PRINT);
                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while loading player data", e);
            }
        });
    }
}
