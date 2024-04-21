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

        TextureAtlas zombie = assetManager.get("zombie.atlas");

        ZOMBIE_WALK_NORTH = new Anim("north", new Array<>(), zombie, Range.open(1,7), 6);
        ZOMBIE_WALK_SOUTH = new Anim("south", new Array<>(), zombie, Range.open(1,7), 6);
        ZOMBIE_WALK_WEST = new Anim("west", new Array<>(), zombie, Range.open(1,7), 6);
        ZOMBIE_WALK_EAST = new Anim("east", new Array<>(), zombie, Range.open(1,7), 6);

        ZOMBIE_IDLE_NORTH = new Anim("north", new Array<>(), zombie,Range.singleton(0), 1);
        ZOMBIE_IDLE_SOUTH = new Anim("south", new Array<>(), zombie, Range.singleton(0), 1);
        ZOMBIE_IDLE_WEST = new Anim("west", new Array<>(), zombie, Range.singleton(0), 1);
        ZOMBIE_IDLE_EAST = new Anim("east", new Array<>(), zombie, Range.singleton(0), 1);

        TextureAtlas mom = assetManager.get("mom.atlas");

        MOM_WALK_NORTH = new Anim("north", new Array<>(), mom, Range.open(1,7), 6);
        MOM_WALK_SOUTH = new Anim("south", new Array<>(), mom, Range.open(1,7), 6);
        MOM_WALK_WEST = new Anim("west", new Array<>(), mom, Range.open(1,7), 6);
        MOM_WALK_EAST = new Anim("east", new Array<>(), mom, Range.open(1,7), 6);

        MOM_IDLE_NORTH = new Anim("north", new Array<>(), mom,Range.singleton(0), 1);
        MOM_IDLE_SOUTH = new Anim("south", new Array<>(), mom, Range.singleton(0), 1);
        MOM_IDLE_WEST = new Anim("west", new Array<>(), mom, Range.singleton(0), 1);
        MOM_IDLE_EAST = new Anim("east", new Array<>(), mom, Range.singleton(0), 1);

        TextureAtlas cali = assetManager.get("cali.atlas");

        CALI_WALK_NORTH = new Anim("north", new Array<>(), cali, Range.open(1,7), 6);
        CALI_WALK_SOUTH = new Anim("south", new Array<>(), cali, Range.open(1,7), 6);
        CALI_WALK_WEST = new Anim("west", new Array<>(), cali, Range.open(1,7), 6);
        CALI_WALK_EAST = new Anim("east", new Array<>(), cali, Range.open(1,7), 6);

        CALI_IDLE_NORTH = new Anim("north", new Array<>(), cali,Range.singleton(0), 1);
        CALI_IDLE_SOUTH = new Anim("south", new Array<>(), cali, Range.singleton(0), 1);
        CALI_IDLE_WEST = new Anim("west", new Array<>(), cali, Range.singleton(0), 1);
        CALI_IDLE_EAST = new Anim("east", new Array<>(), cali, Range.singleton(0), 1);

        TextureAtlas ghostwoman = assetManager.get("ghostgirl.atlas");

        GHOSTGIRL_WALK_NORTH = new Anim("north", new Array<>(), ghostwoman, Range.open(1,7), 6);
        GHOSTGIRL_WALK_SOUTH = new Anim("south", new Array<>(), ghostwoman, Range.open(1,7), 6);
        GHOSTGIRL_WALK_WEST = new Anim("west", new Array<>(), ghostwoman, Range.open(1,7), 6);
        GHOSTGIRL_WALK_EAST = new Anim("east", new Array<>(), ghostwoman, Range.open(1,7), 6);

        GHOSTGIRL_IDLE_NORTH = new Anim("north", new Array<>(), ghostwoman,Range.singleton(0), 1);
        GHOSTGIRL_IDLE_SOUTH = new Anim("south", new Array<>(), ghostwoman, Range.singleton(0), 1);
        GHOSTGIRL_IDLE_WEST = new Anim("west", new Array<>(), ghostwoman, Range.singleton(0), 1);
        GHOSTGIRL_IDLE_EAST = new Anim("east", new Array<>(), ghostwoman, Range.singleton(0), 1);

        TextureAtlas ghostman = assetManager.get("ghostman.atlas");

        GHOSTMAN_WALK_NORTH = new Anim("north", new Array<>(), ghostman, Range.open(1,7), 6);
        GHOSTMAN_WALK_SOUTH = new Anim("south", new Array<>(), ghostman, Range.open(1,7), 6);
        GHOSTMAN_WALK_WEST = new Anim("west", new Array<>(), ghostman, Range.open(1,7), 6);
        GHOSTMAN_WALK_EAST = new Anim("east", new Array<>(), ghostman, Range.open(1,7), 6);

        GHOSTMAN_IDLE_NORTH = new Anim("north", new Array<>(), ghostman,Range.singleton(0), 1);
        GHOSTMAN_IDLE_SOUTH = new Anim("south", new Array<>(), ghostman, Range.singleton(0), 1);
        GHOSTMAN_IDLE_WEST = new Anim("west", new Array<>(), ghostman, Range.singleton(0), 1);
        GHOSTMAN_IDLE_EAST = new Anim("east", new Array<>(), ghostman, Range.singleton(0), 1);

        TextureAtlas devon = assetManager.get("devon.atlas");

        DEVON_WALK_NORTH = new Anim("north", new Array<>(), devon, Range.open(1,7), 6);
        DEVON_WALK_SOUTH = new Anim("south", new Array<>(),devon, Range.open(1,7), 6);
        DEVON_WALK_WEST = new Anim("west", new Array<>(), devon, Range.open(1,7), 6);
        DEVON_WALK_EAST = new Anim("east", new Array<>(), devon, Range.open(1,7), 6);

        DEVON_IDLE_NORTH = new Anim("north", new Array<>(), devon,Range.singleton(0), 1);
        DEVON_IDLE_SOUTH = new Anim("south", new Array<>(), devon, Range.singleton(0), 1);
        DEVON_IDLE_WEST = new Anim("west", new Array<>(), devon, Range.singleton(0), 1);
        DEVON_IDLE_EAST = new Anim("east", new Array<>(), devon, Range.singleton(0), 1);

        TextureAtlas skeleton = assetManager.get("skeleton.atlas");

        SKELETON_WALK_NORTH = new Anim("north", new Array<>(), skeleton, Range.open(1,7), 6);
        SKELETON_WALK_SOUTH = new Anim("south", new Array<>(), skeleton, Range.open(1,7), 6);
        SKELETON_WALK_WEST = new Anim("west", new Array<>(), skeleton, Range.open(1,7), 6);
        SKELETON_WALK_EAST = new Anim("east", new Array<>(), skeleton, Range.open(1,7), 6);

        SKELETON_IDLE_NORTH = new Anim("north", new Array<>(), skeleton,Range.singleton(0), 1);
        SKELETON_IDLE_SOUTH = new Anim("south", new Array<>(), skeleton, Range.singleton(0), 1);
        SKELETON_IDLE_WEST = new Anim("west", new Array<>(), skeleton, Range.singleton(0), 1);
        SKELETON_IDLE_EAST = new Anim("east", new Array<>(), skeleton, Range.singleton(0), 1);

        TextureAtlas ringmaster = assetManager.get("ringmaster.atlas");

        RINGMASTER_WALK_NORTH = new Anim("north", new Array<>(), ringmaster, Range.open(1,7), 6);
        RINGMASTER_WALK_SOUTH = new Anim("south", new Array<>(), ringmaster, Range.open(1,7), 6);
        RINGMASTER_WALK_WEST = new Anim("west", new Array<>(), ringmaster, Range.open(1,7), 6);
        RINGMASTER_WALK_EAST = new Anim("east", new Array<>(), ringmaster, Range.open(1,7), 6);

        RINGMASTER_IDLE_NORTH = new Anim("north", new Array<>(), ringmaster,Range.singleton(0), 1);
        RINGMASTER_IDLE_SOUTH = new Anim("south", new Array<>(), ringmaster, Range.singleton(0), 1);
        RINGMASTER_IDLE_WEST = new Anim("west", new Array<>(), ringmaster, Range.singleton(0), 1);
        RINGMASTER_IDLE_EAST = new Anim("east", new Array<>(), ringmaster, Range.singleton(0), 1);

        TextureAtlas headlessghost = assetManager.get("headlessghost.atlas");

        HEADLESSGHOST_WALK_NORTH = new Anim("north", new Array<>(), headlessghost, Range.open(1,7), 6);
        HEADLESSGHOST_WALK_SOUTH = new Anim("south", new Array<>(), headlessghost, Range.open(1,7), 6);
        HEADLESSGHOST_WALK_WEST = new Anim("west", new Array<>(), headlessghost, Range.open(1,7), 6);
        HEADLESSGHOST_WALK_EAST = new Anim("east", new Array<>(), headlessghost, Range.open(1,7), 6);

        HEADLESSGHOST_IDLE_NORTH = new Anim("north", new Array<>(), headlessghost,Range.singleton(0), 1);
        HEADLESSGHOST_IDLE_SOUTH = new Anim("south", new Array<>(), headlessghost, Range.singleton(0), 1);
        HEADLESSGHOST_IDLE_WEST = new Anim("west", new Array<>(), headlessghost, Range.singleton(0), 1);
        HEADLESSGHOST_IDLE_EAST = new Anim("east", new Array<>(), headlessghost, Range.singleton(0), 1);

        TextureAtlas horseman = assetManager.get("horseman.atlas");

        HORSEMAN_WALK_NORTH = new Anim("north", new Array<>(), horseman, Range.open(1,7), 6);
        HORSEMAN_WALK_SOUTH = new Anim("south", new Array<>(), horseman, Range.open(1,7), 6);
        HORSEMAN_WALK_WEST = new Anim("west", new Array<>(), horseman, Range.open(1,7), 6);
        HORSEMAN_WALK_EAST = new Anim("east", new Array<>(), horseman, Range.open(1,7), 6);

        HORSEMAN_IDLE_NORTH = new Anim("north", new Array<>(), horseman,Range.singleton(0), 1);
        HORSEMAN_IDLE_SOUTH = new Anim("south", new Array<>(), horseman, Range.singleton(0), 1);
        HORSEMAN_IDLE_WEST = new Anim("west", new Array<>(), horseman, Range.singleton(0), 1);
        HORSEMAN_IDLE_EAST = new Anim("east", new Array<>(), horseman, Range.singleton(0), 1);

        TextureAtlas redchest = assetManager.get("redchest.atlas");

        REDCHEST_WALK_NORTH = new Anim("north", new Array<>(), redchest, Range.open(1,7), 6);
        REDCHEST_WALK_SOUTH = new Anim("south", new Array<>(),  redchest, Range.open(1,7), 6);
        REDCHEST_WALK_WEST = new Anim("west", new Array<>(), redchest, Range.open(1,7), 6);
        REDCHEST_WALK_EAST = new Anim("east", new Array<>(),  redchest, Range.open(1,7), 6);

        REDCHEST_IDLE_NORTH = new Anim("north", new Array<>(),  redchest,Range.singleton(0), 1);
        REDCHEST_IDLE_SOUTH = new Anim("south", new Array<>(),  redchest, Range.singleton(0), 1);
        REDCHEST_IDLE_WEST = new Anim("west", new Array<>(),  redchest, Range.singleton(0), 1);
        REDCHEST_IDLE_EAST = new Anim("east", new Array<>(),  redchest, Range.singleton(0), 1);

        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_NORTH, REDCHEST_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_SOUTH, REDCHEST_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_EAST, REDCHEST_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_WEST, REDCHEST_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_NORTH, REDCHEST_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_SOUTH, REDCHEST_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_EAST, REDCHEST_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(REDCHEST_WALK_WEST, REDCHEST_IDLE_WEST);


        IDLE_TRANSITION_ANIMATIONS.put(HORSEMAN_WALK_NORTH, HORSEMAN_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(HORSEMAN_WALK_SOUTH, HORSEMAN_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(HORSEMAN_WALK_EAST, HORSEMAN_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(HORSEMAN_WALK_WEST, HORSEMAN_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(CALI_WALK_NORTH, CALI_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(CALI_WALK_SOUTH, CALI_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(CALI_WALK_EAST, CALI_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(CALI_WALK_WEST, CALI_IDLE_WEST);

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


        IDLE_TRANSITION_ANIMATIONS.put(ZOMBIE_WALK_NORTH, SKELETON_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(ZOMBIE_WALK_SOUTH, SKELETON_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(ZOMBIE_WALK_EAST, SKELETON_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(ZOMBIE_WALK_WEST, SKELETON_IDLE_WEST);


        IDLE_TRANSITION_ANIMATIONS.put(GHOSTGIRL_WALK_NORTH, GHOSTGIRL_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(GHOSTGIRL_WALK_SOUTH, GHOSTGIRL_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(GHOSTGIRL_WALK_EAST, GHOSTGIRL_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(GHOSTGIRL_WALK_WEST, GHOSTGIRL_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(GHOSTMAN_WALK_NORTH, GHOSTMAN_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(GHOSTMAN_WALK_SOUTH, GHOSTMAN_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(GHOSTMAN_WALK_EAST, GHOSTMAN_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(GHOSTMAN_WALK_WEST, GHOSTMAN_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(DEVON_WALK_NORTH, DEVON_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(DEVON_WALK_SOUTH, DEVON_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(DEVON_WALK_EAST, DEVON_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(DEVON_WALK_WEST, DEVON_IDLE_WEST);

        IDLE_TRANSITION_ANIMATIONS.put(HEADLESSGHOST_WALK_NORTH, HEADLESSGHOST_IDLE_NORTH);
        IDLE_TRANSITION_ANIMATIONS.put(HEADLESSGHOST_WALK_SOUTH, HEADLESSGHOST_IDLE_SOUTH);
        IDLE_TRANSITION_ANIMATIONS.put(HEADLESSGHOST_WALK_EAST, HEADLESSGHOST_IDLE_EAST);
        IDLE_TRANSITION_ANIMATIONS.put(HEADLESSGHOST_WALK_WEST, HEADLESSGHOST_IDLE_WEST);
    }
    public static Anim REDCHEST_WALK_NORTH;
    public static Anim REDCHEST_WALK_SOUTH;
    public static Anim REDCHEST_WALK_WEST;
    public static Anim REDCHEST_WALK_EAST;
    public static Anim REDCHEST_IDLE_NORTH;
    public static Anim REDCHEST_IDLE_SOUTH;
    public static Anim REDCHEST_IDLE_WEST;
    public static Anim REDCHEST_IDLE_EAST;

    public static Anim GHOSTGIRL_WALK_NORTH;
    public static Anim GHOSTGIRL_WALK_SOUTH;
    public static Anim GHOSTGIRL_WALK_WEST;
    public static Anim GHOSTGIRL_WALK_EAST;
    public static Anim GHOSTGIRL_IDLE_NORTH;
    public static Anim GHOSTGIRL_IDLE_SOUTH;
    public static Anim GHOSTGIRL_IDLE_WEST;
    public static Anim GHOSTGIRL_IDLE_EAST;

    public static Anim HORSEMAN_WALK_NORTH;
    public static Anim HORSEMAN_WALK_SOUTH;
    public static Anim HORSEMAN_WALK_WEST;
    public static Anim HORSEMAN_WALK_EAST;
    public static Anim HORSEMAN_IDLE_NORTH;
    public static Anim HORSEMAN_IDLE_SOUTH;
    public static Anim HORSEMAN_IDLE_WEST;
    public static Anim HORSEMAN_IDLE_EAST;

    public static Anim GHOSTMAN_WALK_NORTH;
    public static Anim GHOSTMAN_WALK_SOUTH;
    public static Anim GHOSTMAN_WALK_WEST;
    public static Anim GHOSTMAN_WALK_EAST;
    public static Anim GHOSTMAN_IDLE_NORTH;
    public static Anim GHOSTMAN_IDLE_SOUTH;
    public static Anim GHOSTMAN_IDLE_WEST;
    public static Anim GHOSTMAN_IDLE_EAST;

    public static Anim DEVON_WALK_NORTH;
    public static Anim DEVON_WALK_SOUTH;
    public static Anim DEVON_WALK_WEST;
    public static Anim DEVON_WALK_EAST;
    public static Anim DEVON_IDLE_NORTH;
    public static Anim DEVON_IDLE_SOUTH;
    public static Anim DEVON_IDLE_WEST;
    public static Anim DEVON_IDLE_EAST;

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

    public static Anim CALI_WALK_NORTH;
    public static Anim CALI_WALK_SOUTH;
    public static Anim CALI_WALK_WEST;
    public static Anim CALI_WALK_EAST;
    public static Anim CALI_IDLE_NORTH;
    public static Anim CALI_IDLE_SOUTH;
    public static Anim CALI_IDLE_WEST;
    public static Anim CALI_IDLE_EAST;

    public static Anim ZOMBIE_WALK_NORTH;
    public static Anim ZOMBIE_WALK_SOUTH;
    public static Anim ZOMBIE_WALK_WEST;
    public static Anim ZOMBIE_WALK_EAST;
    public static Anim ZOMBIE_IDLE_NORTH;
    public static Anim ZOMBIE_IDLE_SOUTH;
    public static Anim ZOMBIE_IDLE_WEST;
    public static Anim ZOMBIE_IDLE_EAST;

    public static Anim RINGMASTER_WALK_NORTH;
    public static Anim RINGMASTER_WALK_SOUTH;
    public static Anim RINGMASTER_WALK_WEST;
    public static Anim RINGMASTER_WALK_EAST;
    public static Anim RINGMASTER_IDLE_NORTH;
    public static Anim RINGMASTER_IDLE_SOUTH;
    public static Anim RINGMASTER_IDLE_WEST;
    public static Anim RINGMASTER_IDLE_EAST;

    public static Anim HEADLESSGHOST_WALK_NORTH;
    public static Anim HEADLESSGHOST_WALK_SOUTH;
    public static Anim HEADLESSGHOST_WALK_WEST;
    public static Anim HEADLESSGHOST_WALK_EAST;
    public static Anim HEADLESSGHOST_IDLE_NORTH;
    public static Anim HEADLESSGHOST_IDLE_SOUTH;
    public static Anim HEADLESSGHOST_IDLE_WEST;
    public static Anim HEADLESSGHOST_IDLE_EAST;

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
