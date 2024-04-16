package com.csun.game.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.google.common.collect.Range;

import java.util.HashMap;
import java.util.Map;


// @Todo redo this whole thing
public class Animations {

    public static void init(AssetManager assetManager) {
        TextureAtlas witch = assetManager.get("witch.atlas");

        WITCH_WALK_NORTH = new Anim("north", new Array<>(), witch, Range.open(1,7), 6);
        WITCH_WALK_SOUTH = new Anim("south", new Array<>(), witch, Range.open(1,7), 6);
        WITCH_WALK_WEST = new Anim("west", new Array<>(), witch, Range.open(1,7), 6);
        WITCH_WALK_EAST = new Anim("east", new Array<>(), witch, Range.open(1,7), 6);

        WITCH_IDLE_NORTH = new Anim("north", new Array<>(), witch,Range.singleton(0), 1);
        WITCH_IDLE_SOUTH = new Anim("south", new Array<>(), witch, Range.singleton(0), 1);
        WITCH_IDLE_WEST = new Anim("west", new Array<>(), witch, Range.singleton(0), 1);
        WITCH_IDLE_EAST = new Anim("east", new Array<>(), witch, Range.singleton(0), 1);

        TextureAtlas doctor = assetManager.get("doctor.atlas");

        DOCTOR_WALK_NORTH = new Anim("north", new Array<>(), doctor, Range.open(1,7), 6);
        DOCTOR_WALK_SOUTH = new Anim("south", new Array<>(), doctor, Range.open(1,7), 6);
        DOCTOR_WALK_WEST = new Anim("west", new Array<>(), doctor, Range.open(1,7), 6);
        DOCTOR_WALK_EAST = new Anim("east", new Array<>(), doctor, Range.open(1,7), 6);

        DOCTOR_IDLE_NORTH = new Anim("north", new Array<>(), doctor,Range.singleton(0), 1);
        DOCTOR_IDLE_SOUTH = new Anim("south", new Array<>(), doctor, Range.singleton(0), 1);
        DOCTOR_IDLE_WEST = new Anim("west", new Array<>(), doctor, Range.singleton(0), 1);
        DOCTOR_IDLE_EAST = new Anim("east", new Array<>(), doctor, Range.singleton(0), 1);

        TextureAtlas mom = assetManager.get("mom.atlas");

        MOM_WALK_NORTH = new Anim("north", new Array<>(), mom, Range.open(1,7), 6);
        MOM_WALK_SOUTH = new Anim("south", new Array<>(), mom, Range.open(1,7), 6);
        MOM_WALK_WEST = new Anim("west", new Array<>(), mom, Range.open(1,7), 6);
        MOM_WALK_EAST = new Anim("east", new Array<>(), mom, Range.open(1,7), 6);

        MOM_IDLE_NORTH = new Anim("north", new Array<>(), mom,Range.singleton(0), 1);
        MOM_IDLE_SOUTH = new Anim("south", new Array<>(), mom, Range.singleton(0), 1);
        MOM_IDLE_WEST = new Anim("west", new Array<>(), mom, Range.singleton(0), 1);
        MOM_IDLE_EAST = new Anim("east", new Array<>(), mom, Range.singleton(0), 1);

        TextureAtlas skeleton = assetManager.get("skeleton.atlas");

        SKELETON_WALK_NORTH = new Anim("north", new Array<>(), skeleton, Range.open(1,7), 6);
        SKELETON_WALK_SOUTH = new Anim("south", new Array<>(), skeleton, Range.open(1,7), 6);
        SKELETON_WALK_WEST = new Anim("west", new Array<>(), skeleton, Range.open(1,7), 6);
        SKELETON_WALK_EAST = new Anim("east", new Array<>(), skeleton, Range.open(1,7), 6);

        SKELETON_IDLE_NORTH = new Anim("north", new Array<>(), skeleton,Range.singleton(0), 1);
        SKELETON_IDLE_SOUTH = new Anim("south", new Array<>(), skeleton, Range.singleton(0), 1);
        SKELETON_IDLE_WEST = new Anim("west", new Array<>(), skeleton, Range.singleton(0), 1);
        SKELETON_IDLE_EAST = new Anim("east", new Array<>(), skeleton, Range.singleton(0), 1);

        TextureAtlas stranger = assetManager.get("stranger.atlas");

        STRANGER_WALK_NORTH = new Anim("north", new Array<>(), stranger, Range.open(1,7), 6);
        STRANGER_WALK_SOUTH = new Anim("south", new Array<>(), stranger, Range.open(1,7), 6);
        STRANGER_WALK_WEST = new Anim("west", new Array<>(), stranger, Range.open(1,7), 6);
        STRANGER_WALK_EAST = new Anim("east", new Array<>(), stranger, Range.open(1,7), 6);

        STRANGER_IDLE_NORTH = new Anim("north", new Array<>(), stranger,Range.singleton(0), 1);
        STRANGER_IDLE_SOUTH = new Anim("south", new Array<>(), stranger, Range.singleton(0), 1);
        STRANGER_IDLE_WEST = new Anim("west", new Array<>(), stranger, Range.singleton(0), 1);
        STRANGER_IDLE_EAST = new Anim("east", new Array<>(), stranger, Range.singleton(0), 1);

        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_NORTH, WITCH_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_SOUTH, WITCH_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_EAST, WITCH_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(WITCH_WALK_WEST, WITCH_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_NORTH, DOCTOR_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_SOUTH, DOCTOR_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_EAST, DOCTOR_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(DOCTOR_WALK_WEST, DOCTOR_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(MOM_WALK_NORTH, MOM_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(MOM_WALK_SOUTH, MOM_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(MOM_WALK_EAST, MOM_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(MOM_WALK_WEST, MOM_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(SKELETON_WALK_EAST, SKELETON_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(SKELETON_WALK_WEST, SKELETON_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(STRANGER_WALK_NORTH, STRANGER_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(STRANGER_WALK_SOUTH, STRANGER_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(STRANGER_WALK_EAST, STRANGER_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(STRANGER_WALK_WEST, STRANGER_IDLE_WEST);
    }

    public static Anim WITCH_WALK_NORTH;
    public static Anim WITCH_WALK_SOUTH;
    public static Anim WITCH_WALK_WEST;
    public static Anim WITCH_WALK_EAST;
    public static Anim WITCH_IDLE_NORTH;
    public static Anim WITCH_IDLE_SOUTH;
    public static Anim WITCH_IDLE_WEST;
    public static Anim WITCH_IDLE_EAST;

    public static Anim DOCTOR_WALK_NORTH;
    public static Anim DOCTOR_WALK_SOUTH;
    public static Anim DOCTOR_WALK_WEST;
    public static Anim DOCTOR_WALK_EAST;
    public static Anim DOCTOR_IDLE_NORTH;
    public static Anim DOCTOR_IDLE_SOUTH;
    public static Anim DOCTOR_IDLE_WEST;
    public static Anim DOCTOR_IDLE_EAST;

    public static Anim MOM_WALK_NORTH;
    public static Anim MOM_WALK_SOUTH;
    public static Anim MOM_WALK_WEST;
    public static Anim MOM_WALK_EAST;
    public static Anim MOM_IDLE_NORTH;
    public static Anim MOM_IDLE_SOUTH;
    public static Anim MOM_IDLE_WEST;
    public static Anim MOM_IDLE_EAST;

    public static Anim SKELETON_WALK_NORTH;
    public static Anim SKELETON_WALK_SOUTH;
    public static Anim SKELETON_WALK_WEST;
    public static Anim SKELETON_WALK_EAST;
    public static Anim SKELETON_IDLE_NORTH;
    public static Anim SKELETON_IDLE_SOUTH;
    public static Anim SKELETON_IDLE_WEST;
    public static Anim SKELETON_IDLE_EAST;

    public static Anim STRANGER_WALK_NORTH;
    public static Anim STRANGER_WALK_SOUTH;
    public static Anim STRANGER_WALK_WEST;
    public static Anim STRANGER_WALK_EAST;
    public static Anim STRANGER_IDLE_NORTH;
    public static Anim STRANGER_IDLE_SOUTH;
    public static Anim STRANGER_IDLE_WEST;
    public static Anim STRANGER_IDLE_EAST;

    public record Anim(String key, Array<TextureAtlas.AtlasRegion> atlasRegions, TextureAtlas atlas, Range<Integer> frameIndices, float length) {
        public Anim {
            atlasRegions.addAll(getFrames(key, atlas, frameIndices));
        }
    }

    public static Array<TextureAtlas.AtlasRegion> getFrames(String key, TextureAtlas atlas, Range<Integer> framesIndices) {
        Array<TextureAtlas.AtlasRegion> atlasRegions = atlas.findRegions(key);
        Array<TextureAtlas.AtlasRegion> selectedRegions = new Array<>();
        for (int i = framesIndices.lowerEndpoint(); i <= framesIndices.upperEndpoint() && i < atlasRegions.size; i++) {
            selectedRegions.add(atlasRegions.get(i));
        }
        return selectedRegions;
    }

    public static final Map<Anim, Anim> IDLE_TRANSITION_ANIMATIONS = new HashMap<>();
}
