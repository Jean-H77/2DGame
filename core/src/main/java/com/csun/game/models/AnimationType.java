package com.csun.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.google.common.collect.Range;


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
        Array<TextureAtlas.AtlasRegion> atlasRegions = atlas.findRegions(key);
        Array<TextureAtlas.AtlasRegion> selectedRegions = new Array<>();
        for (int i = framesIndices.lowerEndpoint(); i <= framesIndices.upperEndpoint() && i < atlasRegions.size; i++) {
            selectedRegions.add(atlasRegions.get(i));
        }
        this.atlasRegions = selectedRegions;
        this.length = length;
        System.out.println(this.length);
    }

    private final float length;
    private final Array<TextureAtlas.AtlasRegion> atlasRegions;

    public float getLength() {
        return length;
    }

    public Array<TextureAtlas.AtlasRegion> getAtlasRegions() {
        return atlasRegions;
    }

    static class AnimationData {
        public static final TextureAtlas PLAYER_ATLAS = new TextureAtlas(Gdx.files.internal("witch.atlas"));
    }
}
