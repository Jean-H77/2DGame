package com.csun.game.npc;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.csun.game.GameConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

import java.lang.reflect.Type;
import java.util.List;

public class NpcLoader extends AsynchronousAssetLoader<NpcJson.NpcJsonList, NpcLoader.JsonParameter> {

    private NpcJson.NpcJsonList npcList;

    @Inject
    public NpcLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager assetManager, String s, FileHandle fileHandle, JsonParameter jsonParameter) {
        Gson gson = GameConstants.GSON_PRETTY_PRINT;
        String content = fileHandle.readString();
        Type listType = new TypeToken<List<NpcJson>>(){}.getType();
        List<NpcJson> list = gson.fromJson(content, listType);
        npcList = new NpcJson.NpcJsonList(list);
    }

    @Override
    public NpcJson.NpcJsonList loadSync(AssetManager assetManager, String s, FileHandle fileHandle, JsonParameter jsonParameter) {
        NpcJson.NpcJsonList loadedNpcs = this.npcList;
        this.npcList = null;
        return loadedNpcs;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String s, FileHandle fileHandle, JsonParameter jsonParameter) {
        return null;
    }

    public static class JsonParameter extends AssetLoaderParameters<NpcJson.NpcJsonList> {
    }
}
