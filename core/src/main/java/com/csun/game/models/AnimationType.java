package com.csun.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.google.common.collect.Range;

import java.util.HashMap;
import java.util.Map;


// @Todo redo this whole thing
public enum AnimationType {
    WITCH_WALK_NORTH("north", AnimationData.WITCH_ATLAS, Range.open(1, 7), 6),
    WITCH_WALK_SOUTH("south", AnimationData.WITCH_ATLAS, Range.open(1, 7), 6),
    WITCH_WALK_WEST("west", AnimationData.WITCH_ATLAS, Range.open(1, 7), 6),
    WITCH_WALK_EAST("east", AnimationData.WITCH_ATLAS, Range.open(1, 7), 6),
    WITCH_IDLE_NORTH("north", AnimationData.WITCH_ATLAS, Range.singleton(0), 1),
    WITCH_IDLE_SOUTH("south", AnimationData.WITCH_ATLAS, Range.singleton(0), 1),
    WITCH_IDLE_WEST("west", AnimationData.WITCH_ATLAS, Range.singleton(0), 1),
    WITCH_IDLE_EAST("east", AnimationData.WITCH_ATLAS, Range.singleton(0), 1),

    DOCTOR_WALK_NORTH("north", AnimationData.DOCTOR_ATLAS, Range.open(1, 7), 6),
    DOCTOR_WALK_SOUTH("south", AnimationData.DOCTOR_ATLAS, Range.open(1, 7), 6),
    DOCTOR_WALK_WEST("west", AnimationData.DOCTOR_ATLAS, Range.open(1, 7), 6),
    DOCTOR_WALK_EAST("east", AnimationData.DOCTOR_ATLAS, Range.open(1, 7), 6),
    DOCTOR_IDLE_NORTH("north", AnimationData.DOCTOR_ATLAS, Range.singleton(0), 1),
    DOCTOR_IDLE_SOUTH("south", AnimationData.DOCTOR_ATLAS, Range.singleton(0), 1),
    DOCTOR_IDLE_WEST("west", AnimationData.DOCTOR_ATLAS, Range.singleton(0), 1),
    DOCTOR_IDLE_EAST("east", AnimationData.DOCTOR_ATLAS, Range.singleton(0), 1),

    MOM_WALK_NORTH("north", AnimationData.MOM_ATLAS, Range.open(1, 7), 6),
    MOM_WALK_SOUTH("south", AnimationData.MOM_ATLAS, Range.open(1, 7), 6),
    MOM_WALK_WEST("west", AnimationData.MOM_ATLAS, Range.open(1, 7), 6),
    MOM_WALK_EAST("east", AnimationData.MOM_ATLAS, Range.open(1, 7), 6),
    MOM_IDLE_NORTH("north", AnimationData.MOM_ATLAS, Range.singleton(0), 1),
    MOM_IDLE_SOUTH("south", AnimationData.MOM_ATLAS, Range.singleton(0), 1),
    MOM_IDLE_WEST("west", AnimationData.MOM_ATLAS, Range.singleton(0), 1),
    MOM_IDLE_EAST("east", AnimationData.MOM_ATLAS, Range.singleton(0), 1);


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
        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_NORTH, WITCH_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_SOUTH, WITCH_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_EAST, WITCH_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_WEST, WITCH_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_NORTH, DOCTOR_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_SOUTH, DOCTOR_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_EAST, DOCTOR_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_WEST, DOCTOR_IDLE_WEST);
    }

    static class AnimationData {
        public static final TextureAtlas WITCH_ATLAS = new TextureAtlas(Gdx.files.internal("witch.atlas"));
        public static final TextureAtlas DOCTOR_ATLAS = new TextureAtlas(Gdx.files.internal("doctor.atlas"));
        public static final TextureAtlas MOM_ATLAS = new TextureAtlas(Gdx.files.internal("mom.atlas"));
    }
}
