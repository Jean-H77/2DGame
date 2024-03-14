package com.csun.game.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.csun.game.player.Player;

import java.util.concurrent.CompletableFuture;

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
        this.loadFuture = playerParameter.player.load();
        Gdx.app.log("PlayerLoad", "Loading player");
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
