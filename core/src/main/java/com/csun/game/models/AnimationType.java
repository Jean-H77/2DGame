package com.csun.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.google.common.collect.Range;

import java.util.HashMap;
import java.util.Map;


public enum AnimationType {
    PLAYER_WALK_NORTH("north", AnimationData.PLAYER_ATLAS, Range.open(1, 7), 6),
    PLAYER_WALK_SOUTH("south", AnimationData.PLAYER_ATLAS, Range.open(1, 7), 6),
    PLAYER_WALK_WEST("west", AnimationData.PLAYER_ATLAS, Range.open(1, 7), 6),
    PLAYER_WALK_EAST("east", AnimationData.PLAYER_ATLAS, Range.open(1, 7), 6),
    PLAYER_IDLE_NORTH("north", AnimationData.PLAYER_ATLAS, Range.singleton(0), 1),
    PLAYER_IDLE_SOUTH("south", AnimationData.PLAYER_ATLAS, Range.singleton(0), 1),
    PLAYER_IDLE_WEST("west", AnimationData.PLAYER_ATLAS, Range.singleton(0), 1),
    PLAYER_IDLE_EAST("east", AnimationData.PLAYER_ATLAS, Range.singleton(0), 1);


    AnimationType(String key, TextureAtlas atlas, Range<Integer> framesIndices, float length) {
        this.atlasRegions = getFrames(key, atlas, framesIndices);
        this.length = length;
    }

    private final float length;
    private final Array<TextureAtlas.AtlasRegion> atlasRegions;

    public float getLength() {
        return length;
    }

    public Array<TextureAtlas.AtlasRegion> getAtlasRegions() {
        return atlasRegions;
    }

    public Array<TextureAtlas.AtlasRegion> getFrames(String key, TextureAtlas atlas, Range<Integer> framesIndices) {
        Array<TextureAtlas.AtlasRegion> atlasRegions = atlas.findRegions(key);
        Array<TextureAtlas.AtlasRegion> selectedRegions = new Array<>();
        for (int i = framesIndices.lowerEndpoint(); i <= framesIndices.upperEndpoint() && i < atlasRegions.size; i++) {
            selectedRegions.add(atlasRegions.get(i));
        }
        return selectedRegions;
    }

    public static final Map<AnimationType, AnimationType> IDLE_TRANSITION_ANIMATIONS = new HashMap<>();

    /**
     * going to redo how we handle animations later but this will work for now
     */
    static {
        IDLE_TRANSITION_ANIMATIONS.put(PLAYER_WALK_NORTH, PLAYER_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(PLAYER_WALK_SOUTH, PLAYER_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(PLAYER_WALK_EAST, PLAYER_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(PLAYER_WALK_WEST, PLAYER_IDLE_WEST);

    }

    static class AnimationData {
        public static final TextureAtlas PLAYER_ATLAS = new TextureAtlas(Gdx.files.internal("witch.atlas"));
    }
}
