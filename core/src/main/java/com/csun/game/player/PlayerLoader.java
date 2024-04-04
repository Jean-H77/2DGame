package com.csun.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.csun.game.attributes.AttributeKey;
import com.csun.game.attributes.AttributePersistable;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.csun.game.GameConstants.*;

public class PlayerLoader extends AsynchronousAssetLoader<Player, PlayerLoader.PlayerParameter> {

    private CompletableFuture<Void> loadFuture;

    public PlayerLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String s, FileHandle fileHandle, PlayerParameter playerParameter) {
        return null;
    }

    @Override
    public void loadAsync(AssetManager assetManager, String s, FileHandle fileHandle, PlayerParameter playerParameter) {
        Player player = playerParameter.player;
        this.loadFuture = CompletableFuture.runAsync(() -> {
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
                Gdx.app.log("Error while loading player data", String.valueOf(e));
            }
        });
    }

    @Override
    public Player loadSync(AssetManager assetManager, String s, FileHandle fileHandle, PlayerParameter playerParameter) {
        if (this.loadFuture != null) {
            this.loadFuture.join();
        }
        return playerParameter.player;
    }


    public static class PlayerParameter extends AssetLoaderParameters<Player> {
        public Player player;

        public PlayerParameter(Player player) {
            this.player = player;
        }
    }
}
