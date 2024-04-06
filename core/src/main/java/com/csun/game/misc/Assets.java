package com.csun.game.misc;

import com.badlogic.gdx.assets.AssetManager;

public enum Assets {
    INSTANCE;
    private final AssetManager manager = new AssetManager();

    public AssetManager getManager() {
        return manager;
    }
}
